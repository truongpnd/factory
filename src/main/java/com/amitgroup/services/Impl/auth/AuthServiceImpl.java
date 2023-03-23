package com.amitgroup.services.Impl.auth;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.amitgroup.domains.TokenStore;
import com.amitgroup.models.signin.UserSignInResponse;
import com.amitgroup.redis.entities.CachedTokenDTO;
import com.amitgroup.redis.repositories.CachedTokenRepository;
import com.amitgroup.services.auth.AuthService;
import com.amitgroup.services.email.MailService;
import com.amitgroup.sqldatabase.dto.request.auth.LoginReq;
import com.amitgroup.sqldatabase.dto.request.user.UserDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.dto.response.user.UserTokenDTO;
import com.amitgroup.sqldatabase.entities.LoginActivity;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.entities.UserToken;
import com.amitgroup.sqldatabase.repositories.LoginActivityRepository;
import com.amitgroup.sqldatabase.repositories.UserRepository;
import com.amitgroup.sqldatabase.repositories.UserTokenRepository;
import com.amitgroup.utils.HttpUtils;
import com.amitgroup.utils.RestUtils;
import com.amitgroup.utils.TimeUtils;
import com.amitgroup.utils.validate.LoginRequestValidate;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private MailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    private LoginActivityRepository loginActivityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LoginRequestValidate loginRequestValidate;

    @Autowired
    private CachedTokenRepository cachedTokenRepository;

    @Override
    public ResponseHandler login(LoginReq loginRequest, HttpServletRequest request, Errors validate) {
        //validate
        ResponseHandler resp = new ResponseHandler();
        loginRequestValidate.validate(loginRequest, validate);
        if (validate.hasErrors()) {
            resp.setSuccess(false);
            resp.setCode("Fail");
            resp.setMessage("Login fail");
            resp.setData(validate.getAllErrors().get(0).getDefaultMessage());
            return resp;
        }
        
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            user = userRepository.findByUsername(loginRequest.getEmail());
            if (user == null) {
                resp.setMessage("User not found");
                return resp;
            }
        }
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            resp.setMessage("Wrong password");
            return resp;
        }

        Long timeToLive = getTimeToLiveByRemember(loginRequest.getRemember());
        LoginActivity loginActivity = new LoginActivity();
        loginActivity.setUser(user);
        loginActivity.setLoginIp(HttpUtils.getRequestIP(request));
        loginActivity.setUserAgent(HttpUtils.getUserAgent(request));
        loginActivity.setExpiredTime(new Date(timeToLive));
        loginActivityRepository.save(loginActivity);

        CachedTokenDTO cachedTokenDTO = tokenStore.userLogin(user, RestUtils.getRootIPRequest(request));
        resp.setData(cachedTokenDTO);
        return resp;
    }

    @Override
    public ResponseHandler confirmPassword(String token, String password) {
        ResponseHandler response = new ResponseHandler();
        UserToken userToken = userTokenRepository.findByToken(token);

        if (userToken.getExpiredTime().before(new Date())) {
            response.setMessage("Token expired");
            return response;
        }
        if (userToken == null) {
            response.setMessage("Invalid token");
            return response;
        }
        User user = userRepository.findById(userToken.getUser().getId()).get();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        userTokenRepository.delete(userToken);
        response.setMessage("Password reset successfully");
        return response;
    }

    @Override
    public ResponseHandler forgotPassword(UserDTO user) {
        ResponseHandler resp = new ResponseHandler();
        User userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity == null) {
            resp.setMessage("User not found");
            return resp;
        }
        String resetToken = UUID.randomUUID().toString();
        UserToken userToken = new UserToken();
        userToken.setToken(resetToken);
        userToken.setUser(userEntity);
        userToken.setExpiredTime(TimeUtils.safeAddSecond(new Date(), 1800));
        userTokenRepository.save(userToken);
        String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;
        try {
            emailService.sendEmail(userEntity.getEmail(), "Reset password", resetLink);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        resp.setMessage("Reset password link has been sent to your email");
        UserTokenDTO userTokenDTO = modelMapper.map(userToken, UserTokenDTO.class);
        resp.setData(userTokenDTO);
        return resp;
    }

    private Long getTimeToLiveByRemember(Boolean remember) {
        Long timeToLive = new Date().getTime();
        if (remember == null) {
            timeToLive = timeToLive + (24 * 60 * 60 * 1000);
        } else if (remember) {
            timeToLive = timeToLive + (14 * 24 * 60 * 60 * 1000);
        } else {
            timeToLive = timeToLive + (24 * 60 * 60 * 1000);
        }

        return timeToLive;
    }

    @Override
    public ResponseHandler logOut(String token, HttpServletRequest request) {
        ResponseHandler resp = new ResponseHandler();
        tokenStore.deleteToken(RestUtils.getTokenFromHeader(request));
        return resp;
    }
}

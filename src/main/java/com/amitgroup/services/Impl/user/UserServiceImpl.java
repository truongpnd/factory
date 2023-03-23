package com.amitgroup.services.Impl.user;


import com.amitgroup.domains.TokenStore;
import com.amitgroup.redis.entities.CachedTokenDTO;
import com.amitgroup.redis.repositories.CachedTokenRepository;
import com.amitgroup.services.email.MailService;
import com.amitgroup.services.user.UserService;
import com.amitgroup.sqldatabase.dto.request.user.UserDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.dto.response.user.UserReponseDTO;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.repositories.UserRepository;
import com.amitgroup.sqldatabase.repositories.UserTokenRepository;
import com.amitgroup.utils.RestUtils;
import com.amitgroup.utils.validate.UserDtoValidate;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private CachedTokenRepository cachedTokenRepository;

    @Autowired
    private UserDtoValidate userDtoValidate;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public ResponseHandler findByEmail(String email) {
        return null;
    }


    @Override
    public ResponseHandler createAccount(UserDTO user, String token, Errors validate) {
        ResponseHandler resp = new ResponseHandler();
        userDtoValidate.validate(user, validate);
        if (validate.hasErrors()) {
            resp.setSuccess(false);
            resp.setCode("Fail");
            resp.setMessage(validate.getAllErrors().get(0).getDefaultMessage());
            return resp;
        }
        User userEntity = modelMapper.map(user, User.class);
        CachedTokenDTO cachedTokenDTO = cachedTokenRepository.findByToken(token);
        User userAdmin = userRepository.findById(cachedTokenDTO.getCurrentUserId()).orElse(null);
        if (userAdmin == null) {
            resp.setMessage("Invalid token");
            return resp;
        }

        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
        resp.setData(user);
        return resp;

    }

    @Override
    public ResponseHandler updateAccount(UserDTO user) {
        User userEntity = modelMapper.map(user, User.class);
        userRepository.save(userEntity);
        ResponseHandler resp = new ResponseHandler();
        resp.setData(user);
        return resp;
    }

    @Override
    public ResponseHandler deleteAccount(Long id) {
        ResponseHandler resp = new ResponseHandler();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            resp.setMessage("User not found");
            return resp;
        }
        user.setIsActive(false);
        userRepository.save(user);
        resp.setData(user);
        return resp;
    }

    @Override
    public ResponseHandler getAllUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUser'");
    }

    @Override
    public ResponseHandler getOneUser(Long id) {
        ResponseHandler resp = new ResponseHandler();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            resp.setMessage("User not found");
            return resp;
        }
        resp.setData(user);
        return resp;
    }


    @Override
    public ResponseHandler getListWorkByUserId(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListWorkByUserId'");
    }

    @Override
    public ResponseHandler getAll(Pageable pageable) {
        ResponseHandler resp = new ResponseHandler();
        Page<UserReponseDTO> user = userRepository.findAll(pageable).map(u -> modelMapper.map(u, UserReponseDTO.class));
        resp.setData(user);
        return resp;
    }


   

   
}

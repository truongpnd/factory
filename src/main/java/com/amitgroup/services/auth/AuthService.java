package com.amitgroup.services.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.amitgroup.sqldatabase.dto.request.auth.LoginReq;
import com.amitgroup.sqldatabase.dto.request.user.UserDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface AuthService {
    
    ResponseHandler confirmPassword(String token, String password);
    ResponseHandler login(LoginReq loginRequest, HttpServletRequest request,  Errors validate);
    ResponseHandler forgotPassword(UserDTO user);
    ResponseHandler logOut(String token, HttpServletRequest request);
}

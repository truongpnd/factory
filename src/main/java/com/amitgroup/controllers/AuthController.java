package com.amitgroup.controllers;

import com.amitgroup.sqldatabase.dto.request.auth.LoginReq;
import com.amitgroup.sqldatabase.dto.request.user.UserDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.utils.validate.LoginRequestValidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.amitgroup.controllers.api.AuthAPI;
import com.amitgroup.services.auth.AuthService;
import com.amitgroup.services.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private LoginRequestValidate loginRequestValidate;

    @PostMapping(AuthAPI.AUTH_LOGIN)
    public ResponseHandler login(@RequestBody LoginReq req, HttpServletRequest request, Errors validate) {
        
        return authService.login(req, request, validate);

    }

    @PostMapping(AuthAPI.AUTH_REGISTER)
    public ResponseHandler register(@RequestBody @Validated UserDTO req, @RequestHeader("Authorization") String token, Errors validate){
        return userService.createAccount(req, token, validate);
    }

    @PostMapping(AuthAPI.AUTH_FORGOT_PASSWORD)
    public ResponseHandler forgotPassword(@RequestBody UserDTO req){
        return authService.forgotPassword(req);
    }

    @GetMapping(AuthAPI.AUTH_CONFIRM)
    public ResponseHandler confirmResetPassword(@RequestParam String token, @RequestParam String password) {
        return authService.confirmPassword(token, password);
    }

    @PostMapping(AuthAPI.AUTH_LOGOUT)
    public ResponseHandler logOut(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        return authService.logOut(token, request);
    }

 
}

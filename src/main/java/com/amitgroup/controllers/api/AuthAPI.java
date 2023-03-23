package com.amitgroup.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class AuthAPI {
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String AUTH_LOGOUT = "/auth/logout";
    public static final String AUTH_REGISTER = "/management/register";
    public static final String AUTH_FORGOT_PASSWORD = "/auth/reset-password";
    public static final String AUTH_CONFIRM = "/auth/confirm";

}

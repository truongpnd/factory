package com.amitgroup.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class UserAPI {
    public static final String GET_ALL_USER = "/user/get-all";
    public static final String GET_USER_BY_ID = "/user/get-by-id";
    public static final String CREATE_USER = "/user/create";
    public static final String UPDATE_USER = "/user/update";
    public static final String DEACTIVE_USER = "/user/deactive";
    public static final String USER_PROFILE = "/user/profile";
}

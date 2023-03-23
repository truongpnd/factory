package com.amitgroup.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class RoleAPI {
    public static final String GET_ALL_ROLE = "/role/get-all";
    public static final String GET_ROLE_BY_ID = "/role/get-by-id";
    public static final String GET_ROLE_BY_NAME = "/role/get-by-name";
    public static final String UPDATE_ROLE = "/supervisor/role/update";
    public static final String DELETE_ROLE = "/supervisor/role/delete";
    public static final String CREATE_ROLE = "/supervisor/role/create";
}

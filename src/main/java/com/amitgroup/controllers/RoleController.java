package com.amitgroup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitgroup.controllers.api.RoleAPI;
import com.amitgroup.services.role.RoleService;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

@RestController
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @GetMapping(RoleAPI.GET_ALL_ROLE)
    public ResponseHandler getAllRole(){
        return roleService.getAllRole();
    }
}

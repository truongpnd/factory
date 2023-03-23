package com.amitgroup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amitgroup.controllers.api.UserAPI;
import com.amitgroup.services.user.UserService;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(UserAPI.GET_ALL_USER)
    public ResponseHandler getAllUser(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ResponseHandler resp = new ResponseHandler();
        Pageable pageable = PageRequest.of(page, size);
        resp = userService.getAll(pageable);
        return resp;
    }

    
}

package com.amitgroup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitgroup.controllers.api.AreaAPI;
import com.amitgroup.services.area.AreaService;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

@RestController
public class AreaController {
    
    @Autowired
    private AreaService areaService;

    @GetMapping(AreaAPI.GET_ALL_AREA)
    public ResponseHandler getAllAreas() {
        return areaService.getAllAreas();
    }
}

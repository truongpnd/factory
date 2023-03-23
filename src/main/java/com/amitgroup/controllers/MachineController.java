package com.amitgroup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitgroup.controllers.api.MachineAPI;
import com.amitgroup.services.machine.MachineService;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

@RestController
public class MachineController {
    
    @Autowired
    private MachineService machineService;

    @GetMapping(MachineAPI.API_MACHINE_GET_ALL)
    public ResponseHandler getAllMachines() {
        return machineService.getAllMachines();
    }
}

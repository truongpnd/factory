package com.amitgroup.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.amitgroup.controllers.api.MaintenanceAPI;
import com.amitgroup.services.maintenance.MaintenancePersonService;
import com.amitgroup.services.maintenance.MaintenanceService;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

@RestController
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private MaintenancePersonService maintenancePersonService;

    @PostMapping(MaintenanceAPI.CREATE_MAINTENANCE)
    public ResponseHandler createMaintenance(@RequestBody MaintenanceRequestDTO maintenanceRequestDTO,
            HttpServletRequest request, Errors validate) {
        return maintenanceService.createMaintenance(maintenanceRequestDTO, request, validate);
    }

    @PutMapping(MaintenanceAPI.UPDATE_MAINTENANCE)
    public ResponseHandler updateMaintenance(@RequestBody MaintenanceRequestDTO maintenanceRequestDTO,
    HttpServletRequest request, Errors validate) {
        return maintenanceService.updateMaintenance(maintenanceRequestDTO, request, validate);
    }

    @PostMapping(MaintenanceAPI.CREATE_MAINTENANCE_PERSON)
    public ResponseHandler updateMaintenancePerson(@RequestBody MaintenancePersonDTO maintenancePersonDTO, Errors validate) {
        return maintenancePersonService.createMaintenancePerson(maintenancePersonDTO, validate);
    }

    @PutMapping(MaintenanceAPI.UPDATE_MAINTENANCE_PERSON)
    public ResponseHandler createMaintenancePerson(@RequestBody MaintenancePersonDTO maintenancePersonDTO, Errors validate) {
        return maintenancePersonService.updateMaintenancePerson(maintenancePersonDTO, validate);
    }

    @PutMapping(MaintenanceAPI.CHANGE_STATUS_MAINTENANCE)
    public ResponseHandler changeStatusMaintenance(@RequestBody MaintenanceRequestDTO maintenanceRequestDTO) {
        return maintenanceService.changeStatusMaintenance(maintenanceRequestDTO.getId(),
                maintenanceRequestDTO.getStatus());
    }

    @DeleteMapping(MaintenanceAPI.DELETE_MAINTENANCE)
    public ResponseHandler deleteMaintenance(@RequestBody MaintenanceRequestDTO maintenanceRequestDTO) {
        return maintenanceService.deleteMaintenance(maintenanceRequestDTO.getId());
    }

}

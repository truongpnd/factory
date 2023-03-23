package com.amitgroup.services.maintenance;

import javax.servlet.http.HttpServletRequest;

import org.jboss.jandex.Main;
import org.springframework.validation.Errors;

import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface MaintenanceService {
    
    ResponseHandler getAllMaintenance();
    ResponseHandler getOneMaintenance(Long id);
    ResponseHandler createMaintenance(MaintenanceRequestDTO maintenanceRequestDTO, HttpServletRequest request, Errors errors);
    ResponseHandler updateMaintenance(MaintenanceRequestDTO maintenanceRequestDTO, HttpServletRequest request, Errors errors);
    ResponseHandler deleteMaintenance(Long id);
    ResponseHandler changeStatusMaintenance(Long id, String status);
}

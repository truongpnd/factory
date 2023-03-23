package com.amitgroup.services.maintenance;

import java.util.List;

import org.springframework.validation.Errors;

import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface MaintenancePersonService {
    ResponseHandler createMaintenancePerson(MaintenancePersonDTO user, Errors validate);
    ResponseHandler updateMaintenancePerson(MaintenancePersonDTO user, Errors validate);
}

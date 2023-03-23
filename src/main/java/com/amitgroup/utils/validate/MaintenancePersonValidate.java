package com.amitgroup.utils.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;

@Component
public class MaintenancePersonValidate implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MaintenancePersonDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaintenancePersonDTO maintenancePersonDTO = (MaintenancePersonDTO) target;
        if (maintenancePersonDTO.getIdMaintenanceRequest() == null) {
            errors.rejectValue("idMaintenanceRequest", "idMaintenanceRequest.empty","id công việc không được để trống");
        }
        if (maintenancePersonDTO.getMainUserId() == null) {
            errors.rejectValue("mainUserId", "mainUserId.empty","id người phụ trách chính không được để trống");
        }
        if (maintenancePersonDTO.getPersonId().isEmpty()) {
            errors.rejectValue("personId", "personId.empty","id người hỗ trợ không được để trống");
        }
    }

}

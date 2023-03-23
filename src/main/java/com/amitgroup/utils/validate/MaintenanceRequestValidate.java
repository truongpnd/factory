package com.amitgroup.utils.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;

@Component
public class MaintenanceRequestValidate implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MaintenanceRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaintenanceRequestDTO maintenanceRequestDTO = (MaintenanceRequestDTO) target;
        if (maintenanceRequestDTO.getContent().isEmpty()) {
            errors.rejectValue("content", "content.empty","Nội dung công việc không được để trống");
        }
        if (maintenanceRequestDTO.getLevel().isEmpty()) {
            errors.rejectValue("level", "level.empty","Mức độ ưu tiên không được để trống");
        }
       
        if (maintenanceRequestDTO.getAreaId() == null) {
            errors.rejectValue("areaId", "areaId.empty","Khu vực không được để trống");
        }
        if (maintenanceRequestDTO.getMachineId() == null) {
            errors.rejectValue("machineId", "machineId.empty","Máy không được để trống");
        }
    }
    

}

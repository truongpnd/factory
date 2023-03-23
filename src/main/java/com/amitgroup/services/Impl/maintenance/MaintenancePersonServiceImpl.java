package com.amitgroup.services.Impl.maintenance;

import javax.transaction.Transactional;

import com.amitgroup.services.notification.NotificationService;
import com.amitgroup.sqldatabase.dto.response.notification.NotificationDTO;
import com.amitgroup.sqldatabase.entities.Notification;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.repositories.NotificationRepository;
import com.amitgroup.sqldatabase.repositories.UserTokenRepository;
import com.amitgroup.utils.ContentNotification;
import com.amitgroup.utils.validate.MaintenancePersonValidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.amitgroup.services.maintenance.MaintenancePersonService;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.entities.MaintenancePerson;
import com.amitgroup.sqldatabase.entities.MaintenanceRequest;
import com.amitgroup.sqldatabase.repositories.MaintenancePersonRepository;
import com.amitgroup.sqldatabase.repositories.MaintenanceRepository;

@Service
public class MaintenancePersonServiceImpl implements MaintenancePersonService {

    @Autowired
    private MaintenancePersonRepository maintenancePersonRepository;
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserTokenRepository userTokenRepository;
    @Autowired
    private MaintenancePersonValidate maintenancePersonValidate;

    @Override
    public ResponseHandler createMaintenancePerson(MaintenancePersonDTO user, Errors validate) {
        ResponseHandler responseHandler = new ResponseHandler();
        maintenancePersonValidate.validate(user, validate);
        if (validate.hasErrors()) {
            responseHandler.setMessage("Thêm người thực hiện thất bại");
            responseHandler.setData(validate.getAllErrors().get(0).getDefaultMessage());
            return responseHandler;
        }
        MaintenancePerson maintenancePerson = new MaintenancePerson();
        maintenancePerson.setUserId(user.getMainUserId());
        maintenancePerson.setIsMainPerson(true);
        maintenancePerson.setMaintenanceRequestId(user.getIdMaintenanceRequest());
        maintenancePersonRepository.save(maintenancePerson);

        for (int i = 0; i < user.getPersonId().size(); i++) {
            maintenancePerson = new MaintenancePerson();
            maintenancePerson.setMaintenanceRequestId(user.getIdMaintenanceRequest());
            maintenancePerson.setUserId(user.getPersonId().get(i));
            maintenancePersonRepository.save(maintenancePerson);
        }

        responseHandler.setMessage("Tạo người thực hiện thành công");
        return responseHandler;
    }

    @Transactional
    @Override
    public ResponseHandler updateMaintenancePerson(MaintenancePersonDTO user, Errors validate) {
        ResponseHandler responseHandler = new ResponseHandler();
        MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(user.getIdMaintenanceRequest()).get();
        maintenancePersonValidate.validate(user, validate);
        if (validate.hasErrors()) {
            responseHandler.setMessage("Cập nhật người thực hiện thất bại");
            responseHandler.setData(validate.getAllErrors().get(0).getDefaultMessage());
            return responseHandler;
        }

        if (maintenanceRequest != null) {
            maintenancePersonRepository.deleteByMaintenanceRequestId(user.getIdMaintenanceRequest());
            responseHandler.setData(createMaintenancePerson(user, validate));
            responseHandler.setMessage("Cập nhật người thực hiện thành công");
            return responseHandler;
        }
        responseHandler.setMessage("Không tìm thấy yêu cầu bảo trì");
        return responseHandler;

    }

}

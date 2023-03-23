package com.amitgroup.services.Impl.maintenance;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.amitgroup.services.notification.NotificationService;
import com.amitgroup.sqldatabase.dto.response.notification.NotificationDTO;
import com.amitgroup.sqldatabase.repositories.NotificationRepository;
import com.amitgroup.sqldatabase.repositories.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.amitgroup.domains.TokenStore;
import com.amitgroup.services.maintenance.MaintenanceService;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.entities.MaintenanceRequest;
import com.amitgroup.sqldatabase.repositories.MaintenanceRepository;
import com.amitgroup.sqldatabase.repositories.UserTokenRepository;
import com.amitgroup.utils.RestUtils;
import com.amitgroup.utils.validate.MaintenanceRequestValidate;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private MaintenanceRequestValidate maintenanceRequestValidate;

    @Override
    public ResponseHandler getAllMaintenance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMaintenance'");
    }

    @Override
    public ResponseHandler getOneMaintenance(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOneMaintenance'");
    }

    @Override
    public ResponseHandler createMaintenance(MaintenanceRequestDTO maintenanceRequestDTO, HttpServletRequest request, Errors errors) {
        ResponseHandler responseHandler = new ResponseHandler();
        maintenanceRequestValidate.validate(maintenanceRequestDTO, errors);
        if (errors.hasErrors()) {
            responseHandler.setMessage("Tạo yêu cầu bảo trì thất bại");
            responseHandler.setData(errors.getAllErrors().get(0).getDefaultMessage());
            return responseHandler;
        }
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        maintenanceRequest.MaintenanceRequest(maintenanceRequestDTO);
        Long id = userRepository.findByUsername(tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getUserName()).getId();
        maintenanceRequest.setCreatedBy(id);
        maintenanceRepository.save(maintenanceRequest);
        responseHandler.setData(maintenanceRequest);
        responseHandler.setMessage("Tạo yêu cầu bảo trì thành công");
        return responseHandler;
    }

    @Override
    public ResponseHandler updateMaintenance(MaintenanceRequestDTO maintenanceRequestDTO, HttpServletRequest request, Errors errors) {
        ResponseHandler responseHandler = new ResponseHandler();
        maintenanceRequestValidate.validate(maintenanceRequestDTO, errors);
        if (errors.hasErrors()) {
            responseHandler.setMessage("Cập nhật bảo trì thất bại");
            responseHandler.setData(errors.getAllErrors().get(0).getDefaultMessage());
            return responseHandler;
        }
        Long id = userRepository.findByUsername(tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getUserName()).getId();
        MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(maintenanceRequestDTO.getId()).get();
        maintenanceRequest.MaintenanceRequest(maintenanceRequestDTO);
        maintenanceRequest.setModifiedAt(new Date());
        maintenanceRequest.setModifiedBy(id);
        maintenanceRequest.setId(maintenanceRequestDTO.getId());
        maintenanceRepository.save(maintenanceRequest);
        responseHandler.setData(maintenanceRequest);
        responseHandler.setMessage("Cập nhật yêu cầu bảo trì thành công");
        return responseHandler;
    }

    @Override
    public ResponseHandler deleteMaintenance(Long id) {
        ResponseHandler responseHandler = new ResponseHandler();
        MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(id).get();
        maintenanceRequest.setIsDeleted(true);
        maintenanceRepository.save(maintenanceRequest);
        responseHandler.setMessage("Xóa yêu cầu bảo trì thành công");
        return responseHandler;
    }


    @Override
    public ResponseHandler changeStatusMaintenance(Long id, String status) {
        ResponseHandler responseHandler = new ResponseHandler();
        MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(id).get();
        maintenanceRequest.setStatus(status);
        maintenanceRepository.save(maintenanceRequest);
        responseHandler.setMessage("Cập nhật trạng thái yêu cầu bảo trì thành công");
        return responseHandler;
    }

}

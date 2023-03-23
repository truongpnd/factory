package com.amitgroup.services.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.amitgroup.sqldatabase.dto.request.user.UserDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

@Service
public interface UserService {

    ResponseHandler findByEmail(String email);
    ResponseHandler createAccount(UserDTO user, String token, Errors validate);
    ResponseHandler updateAccount(UserDTO user);
    ResponseHandler deleteAccount(Long id);
    ResponseHandler getAllUser();
    ResponseHandler getOneUser(Long id);
    ResponseHandler getListWorkByUserId(String userId);
    ResponseHandler getAll(Pageable pageable);
    
    
}

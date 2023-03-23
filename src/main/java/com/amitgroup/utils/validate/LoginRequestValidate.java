package com.amitgroup.utils.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.amitgroup.sqldatabase.dto.request.auth.LoginReq;

@Component
public class LoginRequestValidate implements Validator{
    
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginReq.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginReq loginReq = (LoginReq) target;
        if (loginReq.getEmail().isEmpty() ) {
            errors.rejectValue("email", "email.empty", "Email or username không được để trống");
        }
        // if (!loginReq.getEmail().matches(EMAIL_PATTERN)) {
        //     errors.rejectValue("email", "email.invalid", "Email không hợp lệ");
        // }
        if (loginReq.getPassword().isEmpty()) {
            errors.rejectValue("password", "password.empty", "Password không được để trống");
        }
        if (loginReq.getPassword().length() < 8 || loginReq.getPassword().length() > 16) {
            errors.rejectValue("password", "password.length", "Password phải từ 8 đến 16 ký tự");
        }
    }
    
}

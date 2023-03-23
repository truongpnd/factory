package com.amitgroup.utils.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.amitgroup.sqldatabase.dto.request.user.UserDTO;

@Component
public class UserDtoValidate implements Validator{

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDto = (UserDTO) target;
        if(userDto.getId() == null) {
            errors.rejectValue("id", "id.empty", "Id không tồn tại");
        }
        if (userDto.getUsername().isEmpty()) {
            errors.rejectValue("username", "username.empty", "Username không được để trống");
        }
        if (userDto.getFullName().isEmpty()) {
            errors.rejectValue("fullName", "fullName.empty", "Họ tên không được để trống");
        }
        if (userDto.getEmail().isEmpty()) {
            errors.rejectValue("email", "email.empty", "Email không được để trống");
        }
        if (!userDto.getEmail().matches(EMAIL_PATTERN)) {
            errors.rejectValue("email", "email.invalid", "Email không hợp lệ");
        }
        if (userDto.getPassword().isEmpty()) {
            errors.rejectValue("password", "password.empty", "Password không được để trống");
        }
        if (userDto.getPassword().length() < 8 || userDto.getPassword().length() > 16) {
            errors.rejectValue("password", "password.length", "Password phải từ 8 đến 16 ký tự");
        }
        if (userDto.getPhone().isEmpty()) {
            errors.rejectValue("phone", "phone.empty", "Số điện thoại không được để trống");
        if (userDto.getPhone().length() < 10 || userDto.getPhone().length() > 11) {
            errors.rejectValue("phone", "phone.length", "Số điện thoại phải từ 10 đến 11 ký tự");
        }
        if (userDto.getRoleId() == 0) {
            errors.rejectValue("roleId", "roleId.empty", "Vai trò không được để trống");
        }
    }

}
}


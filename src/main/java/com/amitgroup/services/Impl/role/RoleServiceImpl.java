package com.amitgroup.services.Impl.role;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amitgroup.services.role.RoleService;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.dto.response.role.RoleDTO;
import com.amitgroup.sqldatabase.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseHandler getAllRole() {
        ResponseHandler resp = new ResponseHandler();
        List<RoleDTO> roleDTO = new ArrayList<>();
        roleRepository.findAll().forEach(role -> {
            roleDTO.add(modelMapper.map(role, RoleDTO.class));
        });
        resp.setData(roleDTO);
        return resp;
    }
    
}

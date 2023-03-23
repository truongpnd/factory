package com.amitgroup.services.Impl.machine;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amitgroup.services.machine.MachineService;
import com.amitgroup.sqldatabase.dto.request.machine.MachineDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.entities.Machine;
import com.amitgroup.sqldatabase.repositories.MachineRepository;

@Service
public class MachineServiceImpl implements MachineService{

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseHandler getAllMachines() {
        ResponseHandler responseHandler = new ResponseHandler();
        List<MachineDTO> machineDTOs = new ArrayList<>();
        machineRepository.findAll().forEach(machine -> {
            machineDTOs.add(modelMapper.map(machine, MachineDTO.class));
        });
        responseHandler.setData(machineDTOs);
        return responseHandler;
    }

    @Override
    public ResponseHandler getMachineById(Long id) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setData(machineRepository.findById(id).get());
        return responseHandler;
    }

    @Override
    public ResponseHandler createMachine(MachineDTO machineDTO) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setData(machineRepository.save(modelMapper.map(machineDTO, Machine.class)));
        return responseHandler;
    }

    @Override
    public ResponseHandler updateMachine(MachineDTO machineDTO) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setData(createMachine(machineDTO));
        return responseHandler;
    }

    

    
}

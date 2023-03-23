package com.amitgroup.services.machine;

import com.amitgroup.sqldatabase.dto.request.machine.MachineDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface MachineService {
    
    ResponseHandler getAllMachines();
    ResponseHandler getMachineById(Long id);
    ResponseHandler createMachine(MachineDTO machineDTO);
    ResponseHandler updateMachine(MachineDTO machineDTO);

}

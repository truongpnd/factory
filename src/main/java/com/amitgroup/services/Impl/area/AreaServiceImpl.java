package com.amitgroup.services.Impl.area;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amitgroup.services.area.AreaService;
import com.amitgroup.sqldatabase.dto.request.area.AreaDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.entities.Area;
import com.amitgroup.sqldatabase.repositories.AreaRepository;

@Service
public class AreaServiceImpl implements AreaService{

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseHandler getAllAreas() {
        ResponseHandler responseHandler = new ResponseHandler();
        List<AreaDTO> areaDTOs = new ArrayList<>();
        areaRepository.findAll().forEach(area -> {
            areaDTOs.add(modelMapper.map(area, AreaDTO.class));
        });
        responseHandler.setData(areaDTOs);
        return responseHandler;
    }

    @Override
    public ResponseHandler getAreaById(Long id) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setData(modelMapper.map(areaRepository.findById(id).get(), AreaDTO.class));
        return responseHandler;
    }

    @Override
    public ResponseHandler createArea(AreaDTO areaDTO) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setData(modelMapper.map(areaRepository.save(modelMapper.map(areaDTO, Area.class)), AreaDTO.class));
        return responseHandler;
    }

    @Override
    public ResponseHandler updateArea(AreaDTO areaDTO) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setData(createArea(areaDTO));
        return responseHandler;
    }

    
}

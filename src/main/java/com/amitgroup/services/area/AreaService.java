package com.amitgroup.services.area;

import com.amitgroup.sqldatabase.dto.request.area.AreaDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface AreaService {
    
    ResponseHandler getAllAreas();
    ResponseHandler getAreaById(Long id);
    ResponseHandler createArea(AreaDTO areaDTO);
    ResponseHandler updateArea(AreaDTO areaDTO);
}

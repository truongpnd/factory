package com.amitgroup.sqldatabase.dto.response.user;

import java.util.Date;

import com.amitgroup.sqldatabase.entities.MaintenanceRequest;

import lombok.Data;

@Data
public class ListWorkDTO {
    private Long id;
    private String content;
    private String description;
    private String status;
    private Date createdDate;
    private Boolean confirm;
    private String level;
    
    public ListWorkDTO(MaintenanceRequest maintenanceRequest){
        this.id = maintenanceRequest.getId();
        this.content = maintenanceRequest.getContent();
        this.description = maintenanceRequest.getDescription();
        this.status = maintenanceRequest.getStatus();
        this.createdDate = maintenanceRequest.getCreatedAt();
        this.level = maintenanceRequest.getLevel();
    }
}

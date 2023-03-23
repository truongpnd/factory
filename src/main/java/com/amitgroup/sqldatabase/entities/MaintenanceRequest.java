package com.amitgroup.sqldatabase.entities;

import java.util.List;

import javax.persistence.*;

import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "maintenance_request")

public class MaintenanceRequest extends BaseEntity {
    private String content;
    private String status;
    private String level;
    private String description;
    @Column(name = "area_id")
    private Long areaId;
    @Column(name = "machine_id")
    private Long machineId;
    
    @ManyToOne
    @JoinColumn(name = "machine_id" , insertable = false, updatable = false)
    private Machine machine;
    
    @ManyToOne
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area;

    public void MaintenanceRequest(MaintenanceRequestDTO maintenanceRequestDTO) {
        this.content = maintenanceRequestDTO.getContent();
        this.status = maintenanceRequestDTO.getStatus();
        this.level = maintenanceRequestDTO.getLevel();
        this.description = maintenanceRequestDTO.getDescription();
        this.areaId = maintenanceRequestDTO.getAreaId();
        this.machineId = maintenanceRequestDTO.getMachineId();
    }


}

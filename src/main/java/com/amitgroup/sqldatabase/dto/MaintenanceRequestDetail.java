package com.amitgroup.sqldatabase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaintenanceRequestDetail {
    private String localted;
    private String idMachines;
    private String nameMachines;
    private String problem;
    private Date createDate;
    private String status;
    private Long userRequest;
    private Long idPersonInCharge;
    private Long idPersonSupportOne;
    private Long idPersonSupportTwo;
    private Long idPersonSupportThree;

}

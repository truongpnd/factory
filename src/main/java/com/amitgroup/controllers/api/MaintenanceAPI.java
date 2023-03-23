package com.amitgroup.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class MaintenanceAPI {

    public static final String UPDATE_MAINTENANCE = "/supervisor/maintenance/update";
    public static final String CREATE_MAINTENANCE = "/supervisor/maintenance/create";
    public static final String CHANGE_STATUS_MAINTENANCE = "/maintenance/change-status";
    public static final String DELETE_MAINTENANCE = "/supervisor/maintenance/delete";
    //person
    public static final String UPDATE_MAINTENANCE_PERSON = "/supervisor/maintenance/update-person";
    public static final String CREATE_MAINTENANCE_PERSON = "/supervisor/maintenance/create-person";
    public static final String GET_LIST_WORK_BY_USER_ID = "/maintenance/get-list-work";
    public static final String CONFIRM_WORK = "/maintenance/confirm-work";
}

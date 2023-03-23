package com.amitgroup.sqldatabase.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public enum PermissionType {
    MANAGEMENT(1, "USER_MANAGEMENT"),
    SUPERVISOR(2, "SUPERVISOR"),
    USER(3, "USER"),
    ;

    private final int type;
    private final String code;

    PermissionType(int type, String code) {
        this.type = type;
        this.code = code;
    }

    public static PermissionType from(int type) {
        for (PermissionType _type : PermissionType.values()) {
            if (_type.getType() == type) {
                return _type;
            }
        }

        return null;
    }

    public static PermissionType from(String code) {
        for (PermissionType _type : PermissionType.values()) {
            if (_type.getCode().equals(code)) {
                return _type;
            }
        }

        return null;
    }

    @JsonProperty("type")
    public int getType() {
        return type;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    public static List<String> listCode() {
        List<String> codes =  new ArrayList<>();

        for (PermissionType _type : PermissionType.values()) {
            codes.add(_type.getCode());
        }

        return codes;
    }

}

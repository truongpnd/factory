package com.amitgroup.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BaseResponse<T>{
    @Schema(defaultValue = "1" , description = "Result Code")
    private int code  = ERROR.SUCCESS.getCode();

    @Schema(defaultValue = "Message" , description = "Result message")
    private String message;

    @Schema(defaultValue = "MESSAGE_CODE" , description = "Message code")
    private String messageCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Trace ID")
    private String traceId;

    private T data;

    public BaseResponse(T data){
        this.data = data;
    }

    public BaseResponse(){
        this.message = ERROR.SUCCESS.getMessage();
    }

    public BaseResponse(ERROR error){
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BaseResponse(ApiException apiException){
        this.code = apiException.getErrorCode();
        this.message = apiException.getErrorMsg();
        this.messageCode = apiException.getMessageCode();
    }

    public BaseResponse(int code, String message, String messageCode){
        this.code = code;
        this.message = message;
        this.messageCode = messageCode;
    }
}

package com.myblog_rest_api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;




    private long fieldId;
public ResourceNotFoundException(String resourceName,String fieldName,long fieldId ){

    super(String.format("%s with %s:'%s' is not found",resourceName,fieldName,fieldId));
    this.resourceName=resourceName;
    this.fieldName=fieldName;
    this.fieldId=fieldId;

}

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldId() {
        return fieldId;
    }
}

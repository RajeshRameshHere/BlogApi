package com.myblog_rest_api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ArgumentNotMatchedException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handelArgumentNotMatchedException(MethodArgumentNotValidException exception){

        Map<String ,String>errorMap=new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->{errorMap.put(error.getField(),error.getDefaultMessage());});
        return errorMap;
    }

}

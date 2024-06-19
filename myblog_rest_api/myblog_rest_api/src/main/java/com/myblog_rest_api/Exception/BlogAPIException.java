package com.myblog_rest_api.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BlogAPIException extends RuntimeException {

    public BlogAPIException(HttpStatus status, String message) {
        super(message);
    }
}
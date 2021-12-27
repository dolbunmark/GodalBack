package com.dolbunmark.godal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoProductException extends RuntimeException{

    public NoProductException(String message) {
        super(message);
    }
}

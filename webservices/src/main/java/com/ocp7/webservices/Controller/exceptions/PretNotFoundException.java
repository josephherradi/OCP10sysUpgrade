package com.ocp7.webservices.Controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class PretNotFoundException extends RuntimeException {
    public PretNotFoundException(String message){
        super(message);

    }
}

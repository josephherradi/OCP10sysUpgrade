package com.ocp7.webservices.Controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

public class ImpossibleAjouterReservationException extends RuntimeException {
    public ImpossibleAjouterReservationException(String message){super(message);}
}

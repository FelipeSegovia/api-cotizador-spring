package com.neuralcode.cotizador_api.domain.exceptions;

public class UserDomainException extends RuntimeException{
    public UserDomainException(String message) {
        super(message);
    }
}

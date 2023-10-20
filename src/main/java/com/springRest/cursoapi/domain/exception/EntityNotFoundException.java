package com.springRest.cursoapi.domain.exception;

public class EntityNotFoundException extends NegocioException{
    public EntityNotFoundException(String message) {
        super(message);
    }

}

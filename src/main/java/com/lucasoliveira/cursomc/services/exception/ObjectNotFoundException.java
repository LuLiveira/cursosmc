package com.lucasoliveira.cursomc.services.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String s){
        super(s);
    }

    public ObjectNotFoundException(String s, Throwable cause){
        super(s, cause);
    }
}

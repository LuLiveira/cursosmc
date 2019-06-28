package com.lucasoliveira.cursomc.services.exception;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException(String s){
        super(s);
    }

    public AuthorizationException(String s, Throwable cause){
        super(s, cause);
    }
}

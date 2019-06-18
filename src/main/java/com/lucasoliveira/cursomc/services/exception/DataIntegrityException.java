package com.lucasoliveira.cursomc.services.exception;

public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException(String s){
        super(s);
    }

    public DataIntegrityException(String s, Throwable cause){
        super(s, cause);
    }
}

package com.lucasoliveira.cursomc.services.exception;

public class FileException extends RuntimeException {

    public FileException(String s){
        super(s);
    }

    public FileException(String s, Throwable cause){
        super(s, cause);
    }
}

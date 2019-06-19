package com.lucasoliveira.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> error = new ArrayList<>();

    public ValidationError(Integer status, String menssagem, Long timeStamp) {
        super(status, menssagem, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return error;
    }

    public void addError(String fieldName, String message) {
        error.add(new FieldMessage(fieldName, message));
    }
}

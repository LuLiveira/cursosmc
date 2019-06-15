package com.lucasoliveira.cursomc.resources.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {

    private Integer status;
    private String menssagem;
    private Long timeStamp;

    public StandardError(Integer status, String menssagem, Long timeStamp) {
        this.status = status;
        this.menssagem = menssagem;
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

package com.quandora.plugins.REST;

import javax.xml.bind.annotation.*;
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class QuandoraRestModel {

    @XmlElement(name = "value")
    private String message;

    public QuandoraRestModel() {
    }

    public QuandoraRestModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
package com.esrx.services.order.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

/**
 * Created by EI6323 on 2/26/2018.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class FieldErrorMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String property;
    private String message;
    public FieldErrorMessage(final String property, final String message){
        this.property = property;
        this.message = message;
    }

    public String getProperty() {return property;}

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}
}
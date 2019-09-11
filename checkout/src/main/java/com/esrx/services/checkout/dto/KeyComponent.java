package com.esrx.services.order.dto;


import java.io.Serializable;

public class KeyComponent implements Serializable {
    private static final long serialVersionUID = -1L;
    private String name;
    private String value;

    public KeyComponent() {
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


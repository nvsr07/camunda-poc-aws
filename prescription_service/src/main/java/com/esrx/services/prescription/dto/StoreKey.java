package com.esrx.services.order.dto;

import java.io.Serializable;
import java.util.List;

public class StoreKey implements Serializable {
    private static final long serialVersionUID = -1L;
    private String storeName;
    private String keyName;
    private List<KeyComponent> keyComponents;
    private Status status;

    public StoreKey() {
    }

    public String getStoreName() {
        return this.storeName;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public List<KeyComponent> getKeyComponents() {
        return this.keyComponents;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setKeyComponents(List<KeyComponent> keyComponents) {
        this.keyComponents = keyComponents;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

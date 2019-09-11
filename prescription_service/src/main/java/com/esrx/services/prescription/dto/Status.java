package com.esrx.services.order.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(
        value = "Status",
        description = "This is a generic utility struct for capturing a status and effectivity dates in entity models."
)
public class Status implements Serializable {
    private static final long serialVersionUID = 4729797228182891468L;
    @ApiModelProperty("The status.\nThe default allowed values are defined within the context of the entity service and structure being annotated. \nThe default values are Active, Inactive, and Unknown, but those values may be redefined for a particular use context.")
    private String value;
    @ApiModelProperty("The date and time (optional) at which the item became effective. The format must comply with the ISO 8601 standard.\nThe time component is optional.")
    private String effectiveDateTime;
    @ApiModelProperty("The date and time (optional) at which the item will expire. The format must comply with the ISO 8601 standard.\nThe time component is optional.")
    private String expirationDateTime;

    public Status() {
    }

    public String getValue() {
        return this.value;
    }

    public String getEffectiveDateTime() {
        return this.effectiveDateTime;
    }

    public String getExpirationDateTime() {
        return this.expirationDateTime;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEffectiveDateTime(String effectiveDateTime) {
        this.effectiveDateTime = effectiveDateTime;
    }

    public void setExpirationDateTime(String expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public String toString() {
        return "Status(value=" + this.getValue() + ", effectiveDateTime=" + this.getEffectiveDateTime() + ", expirationDateTime=" + this.getExpirationDateTime() + ")";
    }
}

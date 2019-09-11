package com.esrx.services.order.flow;

import java.util.Date;

public class CancelOrderCommandPayload {

    private String orderId;
    private String refId;
    private String reasonForCancellation;
    private Date cancelledDate;

    public String getOrderId() {
        return orderId;
    }

    public CancelOrderCommandPayload setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getRefId() {
        return refId;
    }

    public CancelOrderCommandPayload setRefId(String refId) {
        this.refId = refId;
        return this;
    }

    public String getReasonForCancellation() {
        return reasonForCancellation;
    }

    public CancelOrderCommandPayload setReasonForCancellation(String reasonForCancellation) {
        this.reasonForCancellation = reasonForCancellation;
        return this;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public CancelOrderCommandPayload setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
        return this;
    }
}

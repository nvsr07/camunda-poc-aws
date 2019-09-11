package com.esrx.services.prescription.flow;

public class OrderShippedEventPayload {
  private String refId;

  public String getRefId() {
    return refId;
  }

  public OrderShippedEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
}

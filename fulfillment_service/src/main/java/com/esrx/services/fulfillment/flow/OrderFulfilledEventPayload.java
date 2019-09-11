package com.esrx.services.fulfillment.flow;

public class OrderFulfilledEventPayload {
  private String refId;

  public String getRefId() {
    return refId;
  }

  public OrderFulfilledEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
}

package com.esrx.services.prescription.flow;

public class AddOrderLinesCompleteEventPayload {
  private String refId;

  private String getTraceId;

  public String getRefId() {
    return refId;
  }

  public AddOrderLinesCompleteEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
}

package com.esrx.services.pharmacy.claim.flow;

public class ClaimAdjuducatedPayload {
  private String refId;

  public String getRefId() {
    return refId;
  }

  public ClaimAdjuducatedPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
}

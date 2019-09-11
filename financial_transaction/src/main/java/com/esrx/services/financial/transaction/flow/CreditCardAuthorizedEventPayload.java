package com.esrx.services.financial.transaction.flow;

public class CreditCardAuthorizedEventPayload {
  private String refId;

  public String getRefId() {
    return refId;
  }

  public CreditCardAuthorizedEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
}

package com.esrx.services.membership.flow;

public class MembershipAttachedEventPayload {
  private String refId;

  public String getRefId() {
    return refId;
  }

  public MembershipAttachedEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
}

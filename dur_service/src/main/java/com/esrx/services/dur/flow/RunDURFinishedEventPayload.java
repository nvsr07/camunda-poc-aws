package com.esrx.services.dur.flow;

public class RunDURFinishedEventPayload {
  private String refId;

  public String getRefId() {
    return refId;
  }

  public RunDURFinishedEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
}

package com.esrx.services.inventory.reservation.messages;

public class InventoryReservedEventPayload {
  
  private String refId;
  private String pickId;

  public String getRefId() {
    return refId;
  }

  public InventoryReservedEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  public String getPickId() {
    return pickId;
  }

  public InventoryReservedEventPayload setPickId(String pickId) {
    this.pickId = pickId;
    return this;
  }
}

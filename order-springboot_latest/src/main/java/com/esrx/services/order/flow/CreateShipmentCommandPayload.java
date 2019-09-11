package com.esrx.services.order.flow;

public class CreateShipmentCommandPayload {


  private String refId;
  private String reason;
  private int amount;
  private String pickId;
  private String logisticsProvider;
  private String recipientName;

  public String getPickId() {
    return pickId;
  }

  public CreateShipmentCommandPayload setPickId(String pickId) {
    this.pickId = pickId;
    return this;
  }

  public String getLogisticsProvider() {
    return logisticsProvider;
  }

  public CreateShipmentCommandPayload setLogisticsProvider(String logisticsProvider) {
    this.logisticsProvider = logisticsProvider;
    return this;
  }

  public String getRecipientName() {
    return recipientName;
  }

  public CreateShipmentCommandPayload setRecipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  public String getRecipientAddress() {
    return recipientAddress;
  }

  public CreateShipmentCommandPayload setRecipientAddress(String recipientAddress) {
    this.recipientAddress = recipientAddress;
    return this;
  }

  private String recipientAddress;

  public String getRefId() {
    return refId;
  }
  public CreateShipmentCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
  public String getReason() {
    return reason;
  }
  public CreateShipmentCommandPayload setReason(String reason) {
    this.reason = reason;
    return this;
  }
  public int getAmount() {
    return amount;
  }
  public CreateShipmentCommandPayload setAmount(int amount) {
    this.amount = amount;
    return this;
  }
}

package com.esrx.services.order.flow;

public class FulfillOrderCommandPayload {
  
  private String refId;
  private String reason;
  private int amount;
  private String pickId;
  private String logisticsProvider;
  private String recipientName;

  public String getPickId() {
    return pickId;
  }

  public FulfillOrderCommandPayload setPickId(String pickId) {
    this.pickId = pickId;
    return this;
  }

  public String getLogisticsProvider() {
    return logisticsProvider;
  }

  public FulfillOrderCommandPayload setLogisticsProvider(String logisticsProvider) {
    this.logisticsProvider = logisticsProvider;
    return this;
  }

  public String getRecipientName() {
    return recipientName;
  }

  public FulfillOrderCommandPayload setRecipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  public String getRecipientAddress() {
    return recipientAddress;
  }

  public FulfillOrderCommandPayload setRecipientAddress(String recipientAddress) {
    this.recipientAddress = recipientAddress;
    return this;
  }

  private String recipientAddress;
  
  public String getRefId() {
    return refId;
  }
  public FulfillOrderCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
  public String getReason() {
    return reason;
  }
  public FulfillOrderCommandPayload setReason(String reason) {
    this.reason = reason;
    return this;
  }
  public int getAmount() {
    return amount;
  }
  public FulfillOrderCommandPayload setAmount(int amount) {
    this.amount = amount;
    return this;
  }
}

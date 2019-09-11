package com.esrx.services.financial.transaction.messages;

public class AuthorizeCreditCardCommandPayload {

  private String refId;
  private String reason;
  private int amount;
  private String pickId;

  public String getPickId() {
    return pickId;
  }

  public AuthorizeCreditCardCommandPayload setPickId(String pickId) {
    this.pickId = pickId;
    return this;
  }

  public String getLogisticsProvider() {
    return logisticsProvider;
  }

  public AuthorizeCreditCardCommandPayload setLogisticsProvider(String logisticsProvider) {
    this.logisticsProvider = logisticsProvider;
    return this;
  }

  public String getRecipientName() {
    return recipientName;
  }

  public AuthorizeCreditCardCommandPayload setRecipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  public String getRecipientAddress() {
    return recipientAddress;
  }

  public AuthorizeCreditCardCommandPayload setRecipientAddress(String recipientAddress) {
    this.recipientAddress = recipientAddress;
    return this;
  }

  private String logisticsProvider;
  private String recipientName;
  private String recipientAddress;

  public String getRefId() {
    return refId;
  }
  public AuthorizeCreditCardCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
  public String getReason() {
    return reason;
  }
  public AuthorizeCreditCardCommandPayload setReason(String reason) {
    this.reason = reason;
    return this;
  }
  public int getAmount() {
    return amount;
  }
  public AuthorizeCreditCardCommandPayload setAmount(int amount) {
    this.amount = amount;
    return this;
  }
}

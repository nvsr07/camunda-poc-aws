package com.esrx.services.dur.messages;

public class RunDURCommandPayload {
  
  private String refId;
  private String pickId;

  public String getPickId() {
    return pickId;
  }

  public RunDURCommandPayload setPickId(String pickId) {
    this.pickId = pickId;
    return this;
  }

  public String getLogisticsProvider() {
    return logisticsProvider;
  }

  public RunDURCommandPayload setLogisticsProvider(String logisticsProvider) {
    this.logisticsProvider = logisticsProvider;
    return this;
  }

  public String getRecipientName() {
    return recipientName;
  }

  public RunDURCommandPayload setRecipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  public String getRecipientAddress() {
    return recipientAddress;
  }

  public RunDURCommandPayload setRecipientAddress(String recipientAddress) {
    this.recipientAddress = recipientAddress;
    return this;
  }

  private String logisticsProvider;
  private String recipientName;
  private String recipientAddress;



  
  public String getRefId() {
    return refId;
  }
  public RunDURCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }



}

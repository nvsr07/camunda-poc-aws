package com.esrx.services.order.flow;

import com.esrx.services.order.dto.ItemDto;

import java.util.List;

public class CreateFillCommandPayload {

  private String refId;
  private String rxNumber;
  private String patientName;

  public String getRefId() {
    return refId;
  }

  public CreateFillCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  public List<ItemDto> getLineItems() {
    return lineItems;
  }

  public CreateFillCommandPayload setLineItems(List<ItemDto> lineItems) {
    this.lineItems = lineItems;
    return this;
  }

  private List<ItemDto> lineItems;


  public String getRxNumber() {
    return rxNumber;
  }

  public CreateFillCommandPayload setRxNumber(String rxNumber) {
    this.rxNumber = rxNumber;
    return this;
  }

  public String getPatientName() {
    return patientName;
  }

  public CreateFillCommandPayload setPatientName(String patientName) {
    this.patientName = patientName;
    return this;
  }

}

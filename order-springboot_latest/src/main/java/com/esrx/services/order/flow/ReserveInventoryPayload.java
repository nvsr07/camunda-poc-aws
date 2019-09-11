package com.esrx.services.order.flow;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReserveInventoryPayload {

  private String refId;
  private List<String> itemNos;

  public List<String> getItemNos() {
    return itemNos;
  }

  public ReserveInventoryPayload setItemNos(List<String> itemNos) {
    this.itemNos = itemNos;
    return this;
  }

  public List<Integer> getQuantity() {
    return quantity;
  }

  public ReserveInventoryPayload setQuantity(List<Integer> quantity) {
    this.quantity = quantity;
    return this;
  }

  public List<Date> getOrderPlacedDate() {
    return orderPlacedDate;
  }

  public ReserveInventoryPayload setOrderPlacedDate(List<Date> orderPlacedDate) {
    this.orderPlacedDate = orderPlacedDate;
    return this;
  }

  private List<Integer> quantity;
  private List<Date> orderPlacedDate;



  public String getRefId() {
    return refId;
  }

  public ReserveInventoryPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

}

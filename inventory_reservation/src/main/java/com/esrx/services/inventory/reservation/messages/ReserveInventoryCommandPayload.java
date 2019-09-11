package com.esrx.services.inventory.reservation.messages;

import com.esrx.services.inventory.reservation.domain.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReserveInventoryCommandPayload {

  private String refId;
  private List<String> itemNos;

  public List<String> getItemNos() {
    return itemNos;
  }

  public ReserveInventoryCommandPayload setItemNos(List<String> itemNos) {
    this.itemNos = itemNos;
    return this;
  }

  public List<Integer> getQuantity() {
    return quantity;
  }

  public ReserveInventoryCommandPayload setQuantity(List<Integer> quantity) {
    this.quantity = quantity;
    return this;
  }

  public List<Date> getOrderPlacedDate() {
    return orderPlacedDate;
  }

  public ReserveInventoryCommandPayload setOrderPlacedDate(List<Date> orderPlacedDate) {
    this.orderPlacedDate = orderPlacedDate;
    return this;
  }

  private List<Integer> quantity;
  private List<Date> orderPlacedDate;



  public String getRefId() {
    return refId;
  }

  public ReserveInventoryCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }


}

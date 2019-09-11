package com.esrx.services.order.flow;



import com.esrx.services.order.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class FetchGoodsCommandPayload {
  
  private String refId;
  private String reason = "CustomerOrder";
  private List<OrderItem> items = new ArrayList<>();
  
  public String getRefId() {
    return refId;
  }
  public FetchGoodsCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
  public String getReason() {
    return reason;
  }
  public FetchGoodsCommandPayload setReason(String reason) {
    this.reason = reason;
    return this;
  }
  public List<OrderItem> getItems() {
    return items;
  }
  public FetchGoodsCommandPayload setItems(List<OrderItem> items) {
    this.items = items;
    return this;
  }

}

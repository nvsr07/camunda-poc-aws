package com.esrx.services.order.flow;


import com.esrx.services.order.domain.Order;
import com.esrx.services.order.dto.ItemDto;
import com.esrx.services.order.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class OrderCreatedEventPayload {
  
  private String refId;
  private OrderDto order;
  private List<ItemDto> items = new ArrayList<ItemDto>();


  public OrderDto getOrder() {
    return order;

  }

  public OrderCreatedEventPayload setOrder(OrderDto order) {
    this.order = order;
    return this;
  }

  public String getRefId() {
    return refId;
  }
  public OrderCreatedEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  public List<ItemDto> getItems() {
    return items;
  }

  public OrderCreatedEventPayload setItems(List<ItemDto> items) {
    this.items = items;
    return this;
  }

}

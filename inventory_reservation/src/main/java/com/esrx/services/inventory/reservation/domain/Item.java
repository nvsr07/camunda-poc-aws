package com.esrx.services.inventory.reservation.domain;

import java.util.List;
import java.util.UUID;

public class Item {

  private String articleId;
  private int amount;

  public Item setArticleId(String articleId) {
    this.articleId = articleId; 
    return this;
  }

  public int getAmount() {
    return amount;
  }

  public Item setAmount(int amount) {
    this.amount = amount;
    return this;
  }

  public String getArticleId() {
    return articleId;
  }

  public static class PickOrder {

    private String pickId = UUID.randomUUID().toString();
    private List<Item> items;

    public String getPickId() {
      return pickId;
    }
    public PickOrder setPickId(String pickId) {
      this.pickId = pickId;
      return this;
    }
    public List<Item> getItems() {
      return items;
    }
    public PickOrder setItems(List<Item> items) {
      this.items = items;
      return this;
    }
    @Override
    public String toString() {
      return "PickOrder [pickId=" + pickId + ", items=" + items + "]";
    }
  }
}

package com.deere.theamazingstore.model;

import java.util.Objects;

public class StoreItem {

  private String name;

  private Double price;

  private String type;

  public StoreItem() {
  }

  public StoreItem(String name, Double price, String type) {
    this.name = name;
    this.price = price;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoreItem storeItem = (StoreItem) o;
    return Objects.equals(name, storeItem.name) && Objects.equals(price, storeItem.price) &&
        Objects.equals(type, storeItem.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, price, type);
  }

  @Override
  public String toString() {
    return "StoreItem{" +
        "name='" + name + '\'' +
        ", price=" + price +
        ", type='" + type + '\'' +
        '}';
  }
}

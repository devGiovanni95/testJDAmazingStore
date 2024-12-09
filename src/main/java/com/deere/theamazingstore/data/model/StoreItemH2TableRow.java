package com.deere.theamazingstore.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "STORE_ITEM")
public class StoreItemH2TableRow {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false, unique = true)
  private Integer id;
  @Column(name = "name")
  private String name;
  @Column(name = "price")
  private Double price;
  @Column(name = "type")
  private String type;

  public StoreItemH2TableRow(String name, Double price, String type) {
  }

  public StoreItemH2TableRow(Integer id, String name, Double price, String type) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.type = type;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
    StoreItemH2TableRow that = (StoreItemH2TableRow) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
        Objects.equals(price, that.price) && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, type);
  }

  @Override
  public String toString() {
    return "StoreItemTable{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        ", type='" + type + '\'' +
        '}';
  }
}

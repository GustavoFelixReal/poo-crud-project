package model;

import java.util.Objects;

public class Product {
  private int id;
  private String name;
  private double price;
  private String manufacturer;
  private String description;
  private String keyFeatures;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getManufacturer() {
    return this.manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getKeyFeatures() {
    return this.keyFeatures;
  }

  public void setKeyFeatures(String keyFeatures) {
    this.keyFeatures = keyFeatures;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Product)) {
      return false;
    }
    Product product = (Product) o;
    return id == product.id && Objects.equals(name, product.name) && price == product.price
        && Objects.equals(manufacturer, product.manufacturer) && Objects.equals(description, product.description)
        && Objects.equals(keyFeatures, product.keyFeatures);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, manufacturer, description, keyFeatures);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", name='" + getName() + "'" +
        ", price='" + getPrice() + "'" +
        ", manufacturer='" + getManufacturer() + "'" +
        ", description='" + getDescription() + "'" +
        ", keyFeatures='" + getKeyFeatures() + "'" +
        "}";
  }

}
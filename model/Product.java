package model;

import java.util.Objects;

public class Product {
  private String name;
  private double price;
  private Category category;
  private String description;
  private String manufacturer;
  private double weight;
  private double height;
  private double width;
  private double length;

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

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getManufacturer() {
    return this.manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public double getWeight() {
    return this.weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public double getHeight() {
    return this.height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getWidth() {
    return this.width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getLength() {
    return this.length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  @Override
  public String toString() {
    return "{" +
        " name='" + getName() + "'" +
        ", price='" + getPrice() + "'" +
        ", category='" + getCategory() + "'" +
        ", description='" + getDescription() + "'" +
        ", manufacturer='" + getManufacturer() + "'" +
        ", weight='" + getWeight() + "'" +
        ", height='" + getHeight() + "'" +
        ", width='" + getWidth() + "'" +
        ", length='" + getLength() + "'" +
        "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Product)) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(name, product.name) && price == product.price && Objects.equals(category, product.category)
        && Objects.equals(description, product.description) && Objects.equals(manufacturer, product.manufacturer)
        && weight == product.weight && height == product.height && width == product.width && length == product.length;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, price, category, description, manufacturer, weight, height, width, length);
  }

}
package model;

public class ProductBuilder {
  private Product product;

  public ProductBuilder() {
    this.product = new Product();
  }

  public static ProductBuilder builder() {
    return new ProductBuilder();
  }

  public ProductBuilder addId(int id) {
    this.product.setId(id);
    return this;
  }

  public ProductBuilder addName(String name) {
    this.product.setName(name);
    return this;
  }

  public ProductBuilder addPrice(double price) {
    this.product.setPrice(price);
    return this;
  }

  public ProductBuilder addManufacturer(String manufacturer) {
    this.product.setManufacturer(manufacturer);
    return this;
  }

  public ProductBuilder addDescription(String description) {
    this.product.setDescription(description);
    return this;
  }

  public ProductBuilder addKeyFeatures(String keyFeatures) {
    this.product.setKeyFeatures(keyFeatures);
    return this;
  }

  public Product get() {
    return this.product;
  }
}

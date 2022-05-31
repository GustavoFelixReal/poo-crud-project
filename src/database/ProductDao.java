package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.ProductBuilder;

public class ProductDao implements Idao<Product> {
  private Connection con;

  public ProductDao(Connection con) {
    this.con = con;
  }

  @Override
  public List<Product> all() {
    List<Product> products = new ArrayList<>();

    String query = "SELECT * FROM products";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        Product product = ProductBuilder.builder()
            .addId(rs.getInt("product_id"))
            .addName(rs.getString("product_name"))
            .addPrice(rs.getDouble("product_price"))
            .addManufacturer(rs.getString("product_manufacturer"))
            .addDescription(rs.getString("product_description"))
            .addKeyFeatures(rs.getString("product_key_features"))
            .get();

        products.add(product);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return products;
  }

  @Override
  public List<Product> list(String param) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Product get(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void create(Product product) {
    // TODO Auto-generated method stub

  }

  @Override
  public void update(Product t) {
    // TODO Auto-generated method stub

  }

  @Override
  public void remove(Product t) {
    // TODO Auto-generated method stub

  }
}

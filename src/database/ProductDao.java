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
    List<Product> products = new ArrayList<>();

    String query = "SELECT * FROM products WHERE product_name LIKE '%" + param + "%'";

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
  public Product get(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean create(Product product) {
    String query = "INSERT INTO products VALUES (null, ?, ?, ?, ?, ?)";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1, product.getName());
      stmt.setDouble(2, product.getPrice());
      stmt.setString(3, product.getManufacturer());
      stmt.setString(4, product.getDescription());
      stmt.setString(5, product.getKeyFeatures());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }

    return true;
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

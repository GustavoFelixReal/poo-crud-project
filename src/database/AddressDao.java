package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Address;
import model.AddressBuilder;

public class AddressDao implements Idao<Address> {
  private Connection con;

  public AddressDao(Connection con) {
    this.con = con;
  }

  @Override
  public List<Address> all() {
    List<Address> addresses = new ArrayList<>();

    String query = "SELECT a.*, CONCAT(a.address_owner, ':', c.customer_full_name) as address_owner_concat FROM addresses a ";
    query += "LEFT JOIN customers c ON c.customer_id = a.address_owner";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        Address address = AddressBuilder.builder()
            .addId(rs.getInt("address_id"))
            .addAddress(rs.getString("address_street"), rs.getInt("address_number"), rs.getString("address_city_area"),
                rs.getString("address_city"), rs.getString("address_state"), rs.getString("address_country"),
                rs.getString("address_zip_code"))
            .addLine2(rs.getString("address_line_2"))
            .addOwner(rs.getString("address_owner_concat"))
            .get();

        addresses.add(address);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return addresses;
  }

  public List<String> allCustomers() {
    List<String> customers = new ArrayList<>();

    String query = "SELECT CONCAT(c.customer_id, ':', c.customer_full_name) as address_owner_concat FROM customers c";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        customers.add(rs.getString("address_owner_concat"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customers;
  }

  @Override
  public List<Address> list(String param) {
    List<Address> addresses = new ArrayList<>();

    String query = "SELECT a.*, CONCAT(a.address_owner, ':', c.customer_full_name) as address_owner_concat FROM addresses a ";
    query += "LEFT JOIN customers c ON c.customer_id = a.address_owner ";
    query += "WHERE a.address_owner =" + param;

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        Address address = AddressBuilder.builder()
            .addId(rs.getInt("address_id"))
            .addAddress(rs.getString("address_street"), rs.getInt("address_number"), rs.getString("address_city_area"),
                rs.getString("address_city"), rs.getString("address_state"), rs.getString("address_country"),
                rs.getString("address_zip_code"))
            .addLine2(rs.getString("address_line_2"))
            .addOwner(rs.getString("address_owner_concat"))
            .get();

        addresses.add(address);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return addresses;
  }

  @Override
  public Address get(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void create(Address address) {
    String query = "INSERT INTO addresses (address_owner, address_street, address_number, address_line_2, address_city_area, address_city, address_state, address_country, address_zip_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try {
      String[] mergedOwner = address.getOwner().split(":");
      int owner = Integer.parseInt(mergedOwner[0]);

      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, owner);
      stmt.setString(2, address.getStreet());
      stmt.setInt(3, address.getNumber());
      stmt.setString(4, address.getLine2());
      stmt.setString(5, address.getCityArea());
      stmt.setString(6, address.getCity());
      stmt.setString(7, address.getState());
      stmt.setString(8, address.getCountry());
      stmt.setString(9, address.getZipCode());

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Address address) {
    // TODO Auto-generated method stub

  }

  @Override
  public void remove(Address address) {
    // TODO Auto-generated method stub

  }

}

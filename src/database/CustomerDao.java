package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.CustomerBuilder;

public class CustomerDao implements Idao<Customer> {
  @Override
  public List<Customer> all() {
    List<Customer> customers = new ArrayList<>();

    String query = "SELECT * FROM customers";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        Customer customer = CustomerBuilder.builder()
            .addId(rs.getInt("customer_id"))
            .addName(rs.getString("customer_full_name"))
            .addEmail(rs.getString("customer_email"))
            .addCpf(rs.getString("customer_cpf"))
            .addRg(rs.getString("customer_rg"))
            .addGender(rs.getString("customer_gender"))
            .addBirth(rs.getDate("customer_birth").toLocalDate())
            .addCellPhone(rs.getString("customer_cellphone"))
            .get();

        customers.add(customer);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customers;
  }

  @Override
  public List<Customer> list(String param) {
    List<Customer> customers = new ArrayList<>();

    String query = "SELECT * FROM customers WHERE customer_full_name LIKE '%" + param + "%'";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        Customer customer = CustomerBuilder.builder()
            .addId(rs.getInt("customer_id"))
            .addName(rs.getString("customer_full_name"))
            .addEmail(rs.getString("customer_email"))
            .addCpf(rs.getString("customer_cpf"))
            .addRg(rs.getString("customer_rg"))
            .addGender(rs.getString("customer_gender"))
            .addBirth(rs.getDate("customer_birth").toLocalDate())
            .addCellPhone(rs.getString("customer_cellphone"))
            .get();

        customers.add(customer);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customers;
  }

  @Override
  public Customer get(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void create(Customer customer) {
    String query = "INSERT INTO customers (customer_full_name, customer_email, customer_cpf, customer_rg, customer_gender, customer_birth, customer_cellphone) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1, customer.getName());
      stmt.setString(2, customer.getEmail());
      stmt.setString(3, customer.getCpf());
      stmt.setString(4, customer.getRg());
      stmt.setString(5, customer.getGender());
      stmt.setDate(6, java.sql.Date.valueOf(customer.getBirth()));
      stmt.setString(7, customer.getCellPhone());

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Customer customer) {
    // TODO Auto-generated method stub

  }

  @Override
  public void remove(Customer t) {
    // TODO Auto-generated method stub

  }
}

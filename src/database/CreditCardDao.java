package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CreditCard;
import model.CreditCardBuilder;

public class CreditCardDao implements Idao<CreditCard> {
  private Connection con;

  public CreditCardDao(Connection con) {
    this.con = con;
  }

  @Override
  public List<CreditCard> all() {
    List<CreditCard> creditCards = new ArrayList<>();

    String query = "SELECT cc.*, CONCAT(cc.card_owner, ':', c.customer_full_name) as card_owner_concat FROM credit_cards cc ";
    query += "LEFT JOIN customers c ON c.customer_id = cc.card_owner";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        CreditCard creditCard = CreditCardBuilder.builder()
            .addId(rs.getInt("card_id"))
            .addCreditCard(rs.getString("card_owner_name"), rs.getString("card_number"), rs.getString("card_country"),
                rs.getDate("card_expiry").toLocalDate(), rs.getString("card_cvv"))
            .addOwner(rs.getString("card_owner_concat"))
            .get();

        creditCards.add(creditCard);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return creditCards;
  }

  public List<String> allCustomers() {
    List<String> customers = new ArrayList<>();

    String query = "SELECT CONCAT(cc.card_owner, ':', c.customer_full_name) as card_owner_concat FROM credit_cards cc ";
    query += "LEFT JOIN customers c ON c.customer_id = cc.card_owner";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        customers.add(rs.getString("card_owner_concat"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customers;
  }

  @Override
  public List<CreditCard> list(String param) {
    List<CreditCard> creditCards = new ArrayList<>();

    String query = "SELECT cc.*, CONCAT(cc.card_owner, ':', c.customer_full_name) as card_owner_concat FROM credit_cards cc ";
    query += "LEFT JOIN customers c ON c.customer_id = cc.card_owner ";
    query += "WHERE cc.card_owner =" + param;

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        CreditCard creditCard = CreditCardBuilder.builder()
            .addId(rs.getInt("card_id"))
            .addCreditCard(rs.getString("card_owner_name"), rs.getString("card_number"), rs.getString("card_country"),
                rs.getDate("card_expiry").toLocalDate(), rs.getString("card_cvv"))
            .addOwner(rs.getString("card_owner_concat"))
            .get();

        creditCards.add(creditCard);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return creditCards;
  }

  @Override
  public CreditCard get(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void create(CreditCard card) {
    String query = "INSERT INTO credit_cards VALUES (null, ?, ?, ?, ?, ?, ?),";

    try {
      String[] mergedOwner = card.getOwner().split(":");
      int owner = Integer.parseInt(mergedOwner[0]);

      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, owner);
      stmt.setString(2, card.getName());
      stmt.setString(3, card.getNumber());
      stmt.setString(4, card.getCountry());
      stmt.setDate(5, java.sql.Date.valueOf(card.getExpiry()));
      stmt.setString(6, card.getCvv());
     
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(CreditCard creditCard) {
    // TODO Auto-generated method stub

  }

  @Override
  public void remove(CreditCard creditCard) {
    // TODO Auto-generated method stub

  }

}

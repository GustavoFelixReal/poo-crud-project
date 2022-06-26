package src.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import src.model.*;

public class CreditCardDao implements Idao<CreditCard> {
  private SessionFactory sf;

  public CreditCardDao(SessionFactory sf) {
    this.sf = sf;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<CreditCard> all() {
    List<CreditCard> creditCards = new ArrayList<>();

    String query = "SELECT cc.*, CONCAT(cc.card_owner, ':', c.customer_full_name) as card_owner_concat, c.* FROM credit_cards cc ";
    query += "LEFT JOIN customers c ON c.customer_id = cc.card_owner";

    EntityManager em = sf.createEntityManager();
    Query q = em.createNativeQuery(query);

    List<Object[]> list = q.getResultList();

    for (Object[] obj : list) {
      Customer customer = CustomerBuilder.builder()
          .addId(Integer.parseInt(obj[1].toString()))
          .addName(obj[11].toString())
          .addEmail(obj[12].toString())
          .addCpf(obj[13].toString())
          .addRg(obj[14].toString())
          .addGender(obj[15].toString())
          .addBirth(LocalDate.parse(obj[16].toString()))
          .addCellPhone(obj[17] != null ? obj[17].toString() : null)
          .get();

      CreditCard creditCard = CreditCardBuilder.builder()
          .addId(Integer.parseInt(obj[0].toString()))
          .addCreditCard(obj[2].toString(), obj[3].toString(), obj[4].toString(),
              LocalDate.parse(obj[5].toString()), obj[6].toString())
          .addOwnerString(obj[9].toString())
          .addOwner(customer)
          .get();

      creditCards.add(creditCard);
    }

    return creditCards;
  }

  @SuppressWarnings("unchecked")
  public List<String> allCustomers() {
    List<String> customers = new ArrayList<>();

    String query = "SELECT CONCAT(c.customer_id, ':', c.customer_full_name) as address_owner_concat FROM customers c";

    EntityManager em = sf.createEntityManager();
    Query q = em.createNativeQuery(query);

    List<String> list = q.getResultList();

    for (String obj : list) {
      customers.add(obj);
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<CreditCard> list(String param) {
    List<CreditCard> creditCards = new ArrayList<>();

    String query = "SELECT cc.*, CONCAT(cc.card_owner, ':', c.customer_full_name) as card_owner_concat, c.* FROM credit_cards cc ";
    query += "LEFT JOIN customers c ON c.customer_id = cc.card_owner ";
    query += "WHERE cc.card_owner =" + param;

    EntityManager em = sf.createEntityManager();
    Query q = em.createNativeQuery(query);

    List<Object[]> list = q.getResultList();

    for (Object[] obj : list) {
      Customer customer = CustomerBuilder.builder()
              .addId(Integer.parseInt(obj[1].toString()))
              .addName(obj[11].toString())
              .addEmail(obj[12].toString())
              .addCpf(obj[13].toString())
              .addRg(obj[14].toString())
              .addGender(obj[15].toString())
              .addBirth(LocalDate.parse(obj[16].toString()))
              .addCellPhone(obj[17] != null ? obj[17].toString() : null)
              .get();

      CreditCard creditCard = CreditCardBuilder.builder()
              .addId(Integer.parseInt(obj[0].toString()))
              .addCreditCard(obj[2].toString(), obj[3].toString(), obj[4].toString(),
                      LocalDate.parse(obj[5].toString()), obj[6].toString())
              .addOwnerString(obj[9].toString())
              .addOwner(customer)
              .get();

      creditCards.add(creditCard);
    }

    return creditCards;
  }

  @Override
  public CreditCard get(int id) {
    EntityManager entityManager = sf.createEntityManager();
    CreditCard creditCard = entityManager.find(CreditCard.class, id);
    return creditCard;
  }

  @Override
  public boolean create(CreditCard card) {
    try {
      EntityManager entityManager = sf.createEntityManager();
      EntityTransaction transaction = entityManager.getTransaction();
      transaction.begin();
      entityManager.persist(card);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  @Override
  public void update(CreditCard creditCard) {
    EntityManager entityManager = sf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(creditCard);
    transaction.commit();
  }

  @Override
  public void remove(CreditCard creditCard) {
    EntityManager entityManager = sf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(creditCard);
    transaction.commit();
  }

}

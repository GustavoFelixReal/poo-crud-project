package src.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;
import src.model.CreditCard;
import src.model.Customer;
import src.model.CustomerBuilder;

public class CustomerDao implements Idao<Customer> {
  private SessionFactory sf;

  public CustomerDao(SessionFactory sf) {
    this.sf = sf;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Customer> all() {
    List<Customer> customers = new ArrayList<>();

    String query = "SELECT * FROM customers";

    EntityManager em = sf.createEntityManager();
    Query q = em.createNativeQuery(query);

    List<Object[]> list = q.getResultList();

    for (Object[] obj : list) {
        Customer customer = CustomerBuilder.builder()
            .addId(Integer.parseInt(obj[0].toString()))
            .addName(obj[1].toString())
            .addEmail(obj[2].toString())
            .addCpf(obj[3].toString())
            .addRg(obj[4].toString())
            .addGender(obj[5].toString())
            .addBirth(LocalDate.parse(obj[6].toString()))
            .addCellPhone(obj[7] != null ? obj[7].toString() : null)
            .get();

        customers.add(customer);
    }

    return customers;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Customer> list(String param) {
    List<Customer> customers = new ArrayList<>();

    String query = "SELECT * FROM customers WHERE customer_full_name LIKE '%" + param + "%'";

    EntityManager em = sf.createEntityManager();
    Query q = em.createNativeQuery(query);

    List<Object[]> list = q.getResultList();

    for (Object[] obj : list) {
      Customer customer = CustomerBuilder.builder()
              .addId(Integer.parseInt(obj[0].toString()))
              .addName(obj[1].toString())
              .addEmail(obj[2].toString())
              .addCpf(obj[3].toString())
              .addRg(obj[4].toString())
              .addGender(obj[5].toString())
              .addBirth(LocalDate.parse(obj[6].toString()))
              .addCellPhone(obj[7] != null ? obj[7].toString() : null)
              .get();

      customers.add(customer);
    }

    return customers;
  }

  @Override
  public Customer get(int id) {
    EntityManager entityManager = sf.createEntityManager();
    Customer customer = entityManager.find(Customer.class, id);
    return customer;
  }

  @Override
  public boolean create(Customer customer) {
    try {
      EntityManager entityManager = sf.createEntityManager();
      EntityTransaction transaction = entityManager.getTransaction();
      transaction.begin();
      entityManager.persist(customer);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  @Override
  public void update(Customer customer) {
    EntityManager entityManager = sf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(customer);
    transaction.commit();
  }

  @Override
  public void remove(Customer customer) {
    EntityManager entityManager = sf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(customer);
    transaction.commit();
  }
}

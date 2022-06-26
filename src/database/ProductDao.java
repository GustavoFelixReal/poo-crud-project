package src.database;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;
import src.model.Customer;
import src.model.Product;
import src.model.ProductBuilder;

public class ProductDao implements Idao<Product> {
  private SessionFactory sf;

  public ProductDao(SessionFactory sf) {
    this.sf = sf;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Product> all() {
    List<Product> products = new ArrayList<>();

    String query = "SELECT * FROM products";

    EntityManager em = sf.createEntityManager();
    Query q = em.createNativeQuery(query);

    List<Object[]> list = q.getResultList();

    for (Object[] obj : list) {
      Product product = ProductBuilder.builder()
          .addId(Integer.parseInt(obj[0].toString()))
          .addName(obj[1].toString())
          .addPrice(Double.parseDouble(obj[2].toString()))
          .addManufacturer(obj[3].toString())
          .addDescription(obj[4].toString())
          .addKeyFeatures(obj[5].toString())
          .get();

      products.add(product);
    }

    return products;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Product> list(String param) {
    List<Product> products = new ArrayList<>();

    String query = "SELECT * FROM products WHERE product_name LIKE '%" + param + "%'";

    EntityManager em = sf.createEntityManager();
    Query q = em.createNativeQuery(query);

    List<Object[]> list = q.getResultList();

    for (Object[] obj : list) {
      Product product = ProductBuilder.builder()
              .addId(Integer.parseInt(obj[0].toString()))
              .addName(obj[1].toString())
              .addPrice(Double.parseDouble(obj[2].toString()))
              .addManufacturer(obj[3].toString())
              .addDescription(obj[4].toString())
              .addKeyFeatures(obj[5].toString())
              .get();

      products.add(product);
    }

    return products;
  }

  @Override
  public Product get(int id) {
    EntityManager entityManager = sf.createEntityManager();
    Product product = entityManager.find(Product.class, id);
    return product;
  }

  @Override
  public boolean create(Product product) {
    try {
      EntityManager entityManager = sf.createEntityManager();
      EntityTransaction transaction = entityManager.getTransaction();
      transaction.begin();
      entityManager.persist(product);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  @Override
  public void update(Product product) {
    EntityManager entityManager = sf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(product);
    transaction.commit();
  }

  @Override
  public void remove(Product product) {
    EntityManager entityManager = sf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(product);
    transaction.commit();
  }
}

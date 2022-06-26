package src.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import src.model.Address;
import src.model.AddressBuilder;
import src.model.Customer;
import src.model.CustomerBuilder;

public class AddressDao implements Idao<Address> {
    private SessionFactory sf;

    public AddressDao(SessionFactory sf) {
        this.sf = sf;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Address> all() {
        List<Address> addresses = new ArrayList<>();

        StringBuffer query = new StringBuffer();

        query.append("SELECT a.*, CONCAT(a.address_owner, ':', c.customer_full_name) as address_owner_concat, c.* FROM addresses a ");
        query.append("LEFT JOIN customers c ON c.customer_id = a.address_owner");

        EntityManager em = sf.createEntityManager();
        Query q = em.createNativeQuery(query.toString());

        List<Object[]> list = q.getResultList();

        for (Object[] obj : list) {
            Customer customer = CustomerBuilder.builder()
                    .addId(Integer.parseInt(obj[12].toString()))
                    .addName(obj[13].toString())
                    .addEmail(obj[14].toString())
                    .addCpf(obj[15].toString())
                    .addRg(obj[16].toString())
                    .addGender(obj[17].toString())
                    .addBirth(LocalDate.parse(obj[18].toString()))
                    .addCellPhone(obj[19] != null ? obj[19].toString() : null)
                    .get();

            Address address = AddressBuilder.builder()
                    .addId(Integer.parseInt(obj[0].toString()))
                    .addAddress(obj[2].toString(), Integer.parseInt(obj[3].toString()), obj[5].toString(),
                            obj[6].toString(), obj[7].toString(), obj[8].toString(),
                            obj[9].toString())
                    .addLine2(obj[4] != null ? obj[4].toString() : null)
                    .addOwnerString(obj[11].toString())
                    .addOwner(customer)
                    .get();

            addresses.add(address);
        }

        return addresses;
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
    public List<Address> list(String param) {
        List<Address> addresses = new ArrayList<>();

        StringBuffer query = new StringBuffer();

        query.append("SELECT a.*, CONCAT(a.address_owner, ':', c.customer_full_name) as address_owner_concat, c.* FROM addresses a ");
        query.append("LEFT JOIN customers c ON c.customer_id = a.address_owner ");
        query.append("WHERE a.address_owner =" + param);

        EntityManager em = sf.createEntityManager();
        Query q = em.createNativeQuery(query.toString());

        List<Object[]> list = q.getResultList();

        for (Object[] obj : list) {
            Customer customer = CustomerBuilder.builder()
                    .addId(Integer.parseInt(obj[12].toString()))
                    .addName(obj[13].toString())
                    .addEmail(obj[14].toString())
                    .addCpf(obj[15].toString())
                    .addRg(obj[16].toString())
                    .addGender(obj[17].toString())
                    .addBirth(LocalDate.parse(obj[18].toString()))
                    .addCellPhone(obj[19] != null ? obj[19].toString() : null)
                    .get();

            Address address = AddressBuilder.builder()
                    .addId(Integer.parseInt(obj[0].toString()))
                    .addAddress(obj[2].toString(), Integer.parseInt(obj[3].toString()), obj[5].toString(),
                            obj[6].toString(), obj[7].toString(), obj[8].toString(),
                            obj[9].toString())
                    .addLine2(obj[4] != null ? obj[4].toString() : null)
                    .addOwnerString(obj[11].toString())
                    .addOwner(customer)
                    .get();

            addresses.add(address);
        }

        return addresses;
    }

    @Override
    public Address get(int id) {
        EntityManager entityManager = sf.createEntityManager();
        Address address = entityManager.find(Address.class, id);
        return address;
    }

    @Override
    public boolean create(Address address) {
        try {
            EntityManager entityManager = sf.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(address);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public void update(Address address) {
        EntityManager entityManager = sf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(address);
        transaction.commit();
    }

    @Override
    public void remove(Address address) {
        EntityManager entityManager = sf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(address);
        transaction.commit();
    }

}

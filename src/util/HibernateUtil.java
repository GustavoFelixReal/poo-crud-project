package src.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import src.model.Address;
import src.model.CreditCard;
import src.model.Customer;
import src.model.Product;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				Properties prop = new Properties();
				prop.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				prop.put(Environment.URL, "jdbc:mysql://localhost:3306/ecomm_katchau?createDatabaseIfNotExist=true");
				prop.put(Environment.USER, "root");
				prop.put(Environment.PASS, "root");
				prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
				prop.put(Environment.SHOW_SQL, "true");
				prop.put(Environment.HBM2DDL_AUTO, "update");

				configuration.addProperties(prop);
				configuration.addAnnotatedClass(Address.class);
				configuration.addAnnotatedClass(CreditCard.class);
				configuration.addAnnotatedClass(Customer.class);
				configuration.addAnnotatedClass(Product.class);

				ServiceRegistry registry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties())
						.build();
				sessionFactory = configuration.buildSessionFactory(registry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

}

package modelo.services;

import modelo.Cliente;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    public static SessionFactory newSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory SessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return SessionFactory;
    }
    
        public List<Cliente> getAllClientes () {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM clientes");
        List<Cliente> clientes = (List<Cliente>) query.list();

        session.close();
        sessionFactory.close();
        return clientes;
    }
}

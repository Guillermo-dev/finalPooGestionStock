package modelo.services;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private volatile static SessionFactory INSTANCE = null;

    public static SessionFactory getSessionFactory() {
        if (INSTANCE == null) {
            createSessionFactory();
        }
        return INSTANCE;
    }

    private static void createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory SessionFactory = configuration.buildSessionFactory(serviceRegistry);
        INSTANCE = SessionFactory;
    }
}

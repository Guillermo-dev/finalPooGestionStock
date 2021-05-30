package gestiondestock;

import ui.Index;
import hibernateUtils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GestionDeStock {

    public static void main(String[] args) {
        HibernateUtil.newSessionFactory();
        SessionFactory sessionFactory = HibernateUtil.newSessionFactory();
        Session session = sessionFactory.openSession();

        session.close();
        sessionFactory.close();

        new Index().setVisible(true);
    }

}

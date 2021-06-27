package modelo.services;

import java.util.ArrayList;
import java.util.List;
import modelo.NotaCredito;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class NotaCreditoConsultas extends HibernateUtil {

    public ArrayList<NotaCredito> getAllNotasCredito() {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM NotaCredito");
        List<NotaCredito> notasCreditos = (List<NotaCredito>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<NotaCredito>) notasCreditos;
    }

    public void saveFactura(NotaCredito notaCredito) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(notaCredito);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}

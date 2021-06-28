package modelo.services;

import java.util.ArrayList;
import java.util.List;
import modelo.NotaCredito;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class NotaCreditoConsultas extends HibernateUtil {

    public static ArrayList<NotaCredito> getAllNotasCredito() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM NotaCredito");
        List<NotaCredito> notasCreditos = (List<NotaCredito>) query.list();

        session.getTransaction().commit();
        session.close();

        return (ArrayList<NotaCredito>) notasCreditos;
    }

    public static ArrayList<NotaCredito> getNotasCreditoBuscador(String buscador) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM NotaCredito WHERE "
                + "numero_factura LIKE CONCAT('%',:buscador,'%')");
        query.setParameter("buscador", buscador);
        List<NotaCredito> notasCreditos = (List<NotaCredito>) query.list();

        session.getTransaction().commit();
        session.close();

        return (ArrayList<NotaCredito>) notasCreditos;
    }

    public static void saveNotaCredito(NotaCredito notaCredito) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(notaCredito);

        session.getTransaction().commit();
        session.close();
    }
}

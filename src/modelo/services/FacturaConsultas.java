package modelo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import modelo.Factura;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class FacturaConsultas extends HibernateUtil {

    public ArrayList<Factura> getAllFacturas() {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM Factura");
        List<Factura> facturas = (List<Factura>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Factura>) facturas;
    }

    public ArrayList<Factura> getAllFacturasFiltro(char proposito) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM Factura WHERE proposito = :proposito");
        query.setParameter("proposito", proposito);
        List<Factura> facturas = (List<Factura>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Factura>) facturas;
    }

    public int getLastNuemroFactura() {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        // TODO: ACOMODAR ULTIMO NUMERO SOLO VENTA
        session.beginTransaction();
        Query query = session.createQuery("FROM Factura WHERE proposito = :venta");
        query.setParameter("venta", 'V');
        List<Factura> facturas = (List<Factura>) query.list();

        try {
            Factura ultimaFactura = Collections.max(facturas, Comparator.comparing(factura -> factura.getNumeroFactura()));

            session.close();
            sessionFactory.close();
            return Integer.parseInt(ultimaFactura.getNumeroFactura());
        } catch (Exception e) {
            return 0;
        }
    }

    public Factura getFactura(int idFactura) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Factura factura = (Factura) session.get(Factura.class, idFactura);

        session.close();
        sessionFactory.close();

        return factura;
    }

    public void saveFactura(Factura factura) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(factura);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}

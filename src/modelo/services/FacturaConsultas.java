package modelo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import modelo.Factura;
import java.util.List;
import modelo.Proveedor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class FacturaConsultas extends HibernateUtil {

    public static ArrayList<Factura> getAllFacturas() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Factura");
        List<Factura> facturas = (List<Factura>) query.list();

        session.getTransaction().commit();
        session.close();

        return (ArrayList<Factura>) facturas;
    }

    public static ArrayList<Factura> getAllFacturasFiltro(char proposito) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Factura WHERE proposito = :proposito");
        query.setParameter("proposito", proposito);
        List<Factura> facturas = (List<Factura>) query.list();

        session.getTransaction().commit();
        session.close();

        return (ArrayList<Factura>) facturas;
    }

    public static ArrayList<Factura> getFacturasProveedor(Proveedor proveedor) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Factura WHERE proveedor = :proveedor ORDER  BY fecha");
        query.setParameter("proveedor", proveedor);
        List<Factura> facturas = (List<Factura>) query.list();

        session.getTransaction().commit();
        session.close();

        return (ArrayList<Factura>) facturas;
    }

    public static int getLastNumeroFactura() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Factura WHERE proposito = :venta");
        query.setParameter("venta", 'V');
        List<Factura> facturas = (List<Factura>) query.list();

        session.getTransaction().commit();
        session.close();
      
        if (!facturas.isEmpty()) {
            Factura ultimaFactura = Collections.max(facturas, Comparator.comparing(factura -> factura.getNumeroFactura()));
            return Integer.parseInt(ultimaFactura.getNumeroFactura());
        } else {
            return 0;
        }
    }

    public static Factura getFactura(int idFactura) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();

        Factura factura = (Factura) session.get(Factura.class, idFactura);

        session.close();

        return factura;
    }

    public static void saveFactura(Factura factura) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(factura);
        
        session.getTransaction().commit();
        session.close();
    }
}

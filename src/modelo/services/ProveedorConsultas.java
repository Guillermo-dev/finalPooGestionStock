package modelo.services;

import java.util.ArrayList;
import java.util.List;
import modelo.Proveedor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ProveedorConsultas extends HibernateUtil {

    public ArrayList<Proveedor> getAllProveedores() {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM Proveedor");
        List<Proveedor> proveedores = (List<Proveedor>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Proveedor>) proveedores;
    }

    public Proveedor getProveedor(int idProveedor) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Proveedor proveedor = (Proveedor) session.get(Proveedor.class, idProveedor);

        session.close();
        sessionFactory.close();

        return proveedor;
    }
}

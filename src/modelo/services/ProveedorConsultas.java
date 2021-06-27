package modelo.services;

import java.util.ArrayList;
import java.util.List;
import modelo.Proveedor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ProveedorConsultas extends HibernateUtil {

    public static ArrayList<Proveedor> getAllProveedores() {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM Proveedor");
        List<Proveedor> proveedores = (List<Proveedor>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Proveedor>) proveedores;
    }

    public static Proveedor getProveedor(int idProveedor) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Proveedor proveedor = (Proveedor) session.get(Proveedor.class, idProveedor);

        session.close();
        sessionFactory.close();

        return proveedor;
    }

    public static ArrayList<Proveedor> getProveedoresBuscador(String buscador) {
        buscador = buscador.toUpperCase();

        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Proveedor WHERE "
                + "UPPER (nombre) LIKE CONCAT('%',:buscador,'%') "
                + "OR UPPER (cuil_cuit) LIKE CONCAT('%',:buscador,'%')"
                + "OR UPPER (razon_social) LIKE CONCAT('%',:buscador,'%')"
                + "OR UPPER (direccion) LIKE CONCAT('%',:buscador,'%')"
                + "OR UPPER (telefono) LIKE CONCAT('%',:buscador,'%')"
                + "OR UPPER (email) LIKE CONCAT('%',:buscador,'%')");
        query.setParameter("buscador", buscador);
        List<Proveedor> proveedores = (List<Proveedor>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Proveedor>) proveedores;
    }

    public static void saveProveedor(Proveedor proveedores) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(proveedores);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
    
    public static void updateProveedor(Proveedor newProveedor, int idProveedor) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Proveedor oldProveedor = (Proveedor) session.get(Proveedor.class, idProveedor);
        oldProveedor.setNombre(newProveedor.getNombre());
        oldProveedor.setCuilCuit(newProveedor.getCuilCuit());
        oldProveedor.setRazonSocial(newProveedor.getRazonSocial());
        oldProveedor.setDireccion(newProveedor.getDireccion());
        oldProveedor.setTelefono(newProveedor.getTelefono());
        oldProveedor.setEmail(newProveedor.getEmail());
        
        session.beginTransaction();
        session.update(oldProveedor);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }

    public static void deleteProveedor (int idProveedor){
    
    SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();
        Proveedor proveedor = (Proveedor) session.get(Proveedor.class, idProveedor);

        session.beginTransaction();
        session.delete(proveedor);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
   
    }
    
    
    
    
    
    
    
}

package modelo.services;

import java.util.ArrayList;
import modelo.Articulo;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ArticuloConsultas extends HibernateUtil {

    public ArrayList<Articulo> getAllArticulos() {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM Articulo");
        List<Articulo> articulos = (List<Articulo>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Articulo>) articulos;
    }

    public ArrayList<Articulo> getArticulosBusacador(String buscador) {
        buscador = buscador.toUpperCase();

        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Articulo WHERE "
                + "UPPER (nombre) LIKE CONCAT('%',:buscador,'%') "
                + "OR UPPER (descripcion) LIKE CONCAT('%',:buscador,'%')");
        query.setParameter("buscador", buscador);
        List<Articulo> articulos = (List<Articulo>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Articulo>) articulos;
    }
    
    public ArrayList<Articulo> getArticulosStockMinimo() {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM Articulo WHERE stock_actual < stock_minimo");
        List<Articulo> articulos = (List<Articulo>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Articulo>) articulos;
    }

    public void saveArticulo(Articulo articulo) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(articulo);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }

    public void updateArticulo(Articulo newArticulo, int idArticulo) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Articulo oldArticulo = (Articulo) session.get(Articulo.class, idArticulo);
        oldArticulo.setNombre(newArticulo.getNombre());
        oldArticulo.setRubro(newArticulo.getRubro());
        oldArticulo.setDescripcion(newArticulo.getDescripcion());
        oldArticulo.setProveedor(newArticulo.getProveedor());
        oldArticulo.setStockActual(oldArticulo.getStockActual());
        oldArticulo.setStockMinimo(newArticulo.getStockMinimo());
        oldArticulo.setPrecioUnitario(newArticulo.getPrecioUnitario());

        session.beginTransaction();
        session.update(oldArticulo);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }

    public void deleteArticulo(int idArticulo) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Articulo articulo = (Articulo) session.get(Articulo.class, idArticulo);

        session.beginTransaction();
        session.delete(articulo);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}

package modelo.services;

import java.util.ArrayList;
import java.util.List;
import modelo.Rubro;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RubroConsultas extends HibernateUtil {

    public ArrayList<Rubro> getAllRubros() {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM Rubro");
        List<Rubro> rubros = (List<Rubro>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Rubro>) rubros;
    }

    public ArrayList<Rubro> getRubrosBuscador(String buscador) {
        buscador = buscador.toUpperCase();

        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Rubro WHERE "
                + "UPPER (nombre) LIKE CONCAT('%',:buscador,'%') "
                + "OR UPPER (descripcion) LIKE CONCAT('%',:buscador,'%')");
        query.setParameter("buscador", buscador);
        List<Rubro> rubros = (List<Rubro>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Rubro>) rubros;
    }
    
    public void saveRubro(Rubro rubro) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(rubro);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
    
    public void updateRubro(Rubro newRubro, int idRubro) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Rubro oldRubro = (Rubro) session.get(Rubro.class, idRubro);
        oldRubro.setDescripcion(newRubro.getDescripcion());
        oldRubro.setNombre(newRubro.getNombre());
        

        session.beginTransaction();
        session.update(oldRubro);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
    
    public void deleteRubro(int idRubro) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Rubro rubro = (Rubro) session.get(Rubro.class, idRubro);

        session.beginTransaction();
        session.delete(rubro);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }

    public Rubro getRubro(int idRubro) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Rubro rubro = (Rubro) session.get(Rubro.class, idRubro);

        session.close();
        sessionFactory.close();

        return rubro;
    }
}

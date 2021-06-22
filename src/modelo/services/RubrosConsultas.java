package modelo.services;

import java.util.ArrayList;
import java.util.List;
import modelo.Rubro;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;




public class RubrosConsultas extends HibernateUtil{
    
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
}

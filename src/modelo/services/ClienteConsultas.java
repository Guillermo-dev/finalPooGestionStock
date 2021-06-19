package modelo.services;

import java.util.ArrayList;
import modelo.Cliente;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ClienteConsultas extends HibernateUtil {

    public ArrayList<Cliente> getAllClientes() {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("FROM Cliente");
        List<Cliente> clientes = (List<Cliente>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Cliente>) clientes;
    }

    public ArrayList<Cliente> getClientesBusacador(String buscador) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        buscador = buscador.toUpperCase();
        session.beginTransaction();
        Query query = session.createQuery("FROM Cliente WHERE "
                + "UPPER (nombre) LIKE CONCAT('%',:buscador,'%') "
                + "OR UPPER (apellido) LIKE CONCAT('%',:buscador,'%')"
                + "OR UPPER (dni) LIKE CONCAT('%',:buscador,'%')"
                + "OR UPPER (direccion) LIKE CONCAT('%',:buscador,'%')"
                + "OR UPPER (telefono) LIKE CONCAT('%',:buscador,'%')"
                + "OR UPPER (email) LIKE CONCAT('%',:buscador,'%')");
        query.setParameter("buscador", buscador);
        List<Cliente> clientes = (List<Cliente>) query.list();

        session.close();
        sessionFactory.close();
        return (ArrayList<Cliente>) clientes;     
    }

    public void deleteCliente(int idCliente) {
        SessionFactory sessionFactory = newSessionFactory();
        Session session = sessionFactory.openSession();

        Cliente cliente = (Cliente) session.get(Cliente.class, idCliente);

        session.beginTransaction();
        session.delete(cliente);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}
package modelo.services;

import java.util.ArrayList;
import modelo.Cliente;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ClienteConsultas extends HibernateUtil {

    public static ArrayList<Cliente> getAllClientes() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Cliente");
        List<Cliente> clientes = (List<Cliente>) query.list();

        session.getTransaction().commit();
        session.close();

        return (ArrayList<Cliente>) clientes;
    }

    public static ArrayList<Cliente> getClientesBusacador(String buscador) {
        buscador = buscador.toUpperCase();

        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
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

        session.getTransaction().commit();
        session.close();

        return (ArrayList<Cliente>) clientes;
    }

    public static Cliente getCliente(int idCliente) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();

        Cliente cliente = (Cliente) session.get(Cliente.class, idCliente);

        session.close();
        
        return cliente;
    }

    public static void saveCliente(Cliente cliente) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(cliente);
        
        session.getTransaction().commit();
        session.close();
    }

    public static void updateCliente(Cliente newCliente, int idCliente) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();

        Cliente oldCliente = (Cliente) session.get(Cliente.class, idCliente);
        oldCliente.setApellido(newCliente.getApellido());
        oldCliente.setNombre(newCliente.getNombre());
        oldCliente.setDni(newCliente.getDni());
        oldCliente.setDireccion(newCliente.getDireccion());
        oldCliente.setTelefono(newCliente.getTelefono());
        oldCliente.setEmail(newCliente.getEmail());

        session.beginTransaction();
        
        session.update(oldCliente);
        
        session.getTransaction().commit();
        session.close();
    }

    public static void deleteCliente(int idCliente) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();

        Cliente cliente = (Cliente) session.get(Cliente.class, idCliente);

        session.beginTransaction();
        
        session.delete(cliente);
        
        session.getTransaction().commit();
        session.close();
    }
}

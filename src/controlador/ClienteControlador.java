package controlador;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import modelo.Cliente;
import vista.Index;
import modelo.services.HibernateUtil;

public class ClienteControlador {

    public static void cargarLista(Index view) {
        DefaultListModel clientesModel = new DefaultListModel();
        view.listaClientes.setModel(clientesModel);
        ArrayList<Cliente> clientes = HibernateUtil.getAllClientes();
        clientes.forEach((cliente) -> {
            clientesModel.addElement(cliente.getId() + ", " + cliente.getNombre() + ", " + cliente.getApellido());
        });
    }

    public static void seleccionarCliente(Index view, String id) {
        try {
            view.clienteIdText.setText(id.split(",")[0]);
        } catch (Exception e) {

        }
    }

    public static void eliminarCliente(Index view) {
        try {

            HibernateUtil.deleteCliente(Integer.parseInt(view.clienteIdText.getText()));
            view.clienteIdText.setText("");
            cargarLista(view);
            System.out.println("Eliminado uscces");
        } catch (Exception e) {
            System.out.println("Error");
        }

    }
}

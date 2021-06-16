package controlador;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import vista.Index;
import modelo.services.ClienteConsultas;

public class ClienteControlador {

    public static void cargarTabla(Index view, ClienteConsultas services) {
        DefaultTableModel clientesModel = new DefaultTableModel();
        view.clieTabla.setModel(clientesModel);
        ArrayList<Cliente> clientes = services.getAllClientes();

        clientesModel.addColumn("Id");
        clientesModel.addColumn("Apellido");
        clientesModel.addColumn("Nombre");
        clientesModel.addColumn("DNI");
        clientesModel.addColumn("Direccion");
        clientesModel.addColumn("Telefono");
        clientesModel.addColumn("Email");

        clientes.forEach((cliente) -> {
            String[] data = new String[7];
            data[0] = Integer.toString(cliente.getId());
            data[1] = cliente.getApellido();
            data[2] = cliente.getNombre();
            data[3] = cliente.getDni();
            data[4] = cliente.getDireccion();
            data[5] = cliente.getTelefono();
            data[6] = cliente.getEmail();
            clientesModel.addRow(data);
        });
    }

    public static void seleccionarCliente(Index view, String id, String apellido, String nombre, String dni, String direccion, String telefono, String email) {
        try {
            view.clieInputTextId.setText(id);
            view.clieInputTextApellido.setText(apellido);
            view.clieInputTextNombre.setText(nombre);
            view.clieInputTextDni.setText(dni);
            view.clieInputTextDireccion.setText(direccion);
            view.clieInputTextTelefono.setText(telefono);
            view.clieInputTextEmail.setText(email);
        } catch (Exception e) {

        }
    }

    public static void eliminarCliente(Index view, ClienteConsultas services) {
        try {
            services.deleteCliente(Integer.parseInt(view.clieInputTextId.getText()));
            DefaultTableModel clientesModel = (DefaultTableModel) view.clieTabla.getModel();
            clientesModel.removeRow(view.clieTabla.getSelectedRow());
            view.clieInputTextApellido.setText("");
            view.clieInputTextNombre.setText("");
            view.clieInputTextDni.setText("");
            view.clieInputTextDireccion.setText("");
            view.clieInputTextTelefono.setText("");
            view.clieInputTextEmail.setText("");
            view.clieInputTextId.setText("");
            System.out.println("Eliminado uscces");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}

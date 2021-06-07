package controlador;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import vista.Index;
import modelo.services.HibernateUtil;

public class ClienteControlador {

    public static void cargarTabla(Index view) {
        DefaultTableModel clientesModel = new DefaultTableModel();
        view.cliTabla.setModel(clientesModel);
        ArrayList<Cliente> clientes = HibernateUtil.getAllClientes();

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
            view.cliIdText.setText(id);
            view.cliApellidoText.setText(apellido);
            view.cliNombreText.setText(nombre);
            view.cliDniText.setText(dni);
            view.cliDireccionText.setText(direccion);
            view.cliTelefonoText.setText(telefono);
            view.cliEmailText.setText(email);
        } catch (Exception e) {

        }
    }

    public static void eliminarCliente(Index view) {
        try {
            HibernateUtil.deleteCliente(Integer.parseInt(view.cliIdText.getText()));
            DefaultTableModel clientesModel = (DefaultTableModel) view.cliTabla.getModel();
            clientesModel.removeRow(view.cliTabla.getSelectedRow());
            view.cliApellidoText.setText("");
            view.cliNombreText.setText("");
            view.cliDniText.setText("");
            view.cliDireccionText.setText("");
            view.cliTelefonoText.setText("");
            view.cliEmailText.setText("");
            view.cliIdText.setText("");
            System.out.println("Eliminado uscces");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}

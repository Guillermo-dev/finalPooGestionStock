package controlador;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import vista.Index;
import modelo.services.ClienteConsultas;

public class ClienteControlador {

    public static void cargarTabla(Index view, ClienteConsultas services) {
        DefaultTableModel clientesModel = (DefaultTableModel) view.clieTabla.getModel();
        ArrayList<Cliente> clientes = services.getAllClientes();

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

    public static void buscar(Index view, ClienteConsultas services, String buscador) {
        if (buscador.equals("")) {
            cargarTabla(view, services);
        } else {
            DefaultTableModel clientesModel = (DefaultTableModel) view.clieTabla.getModel();
            clientesModel.setRowCount(0);
            ArrayList<Cliente> clientes = services.getClientesBusacador(buscador);
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

    public static void clearInputTexts(Index view) {
        view.clieInputTextId.setText("");
        view.clieInputTextApellido.setText("");
        view.clieInputTextNombre.setText("");
        view.clieInputTextDni.setText("");
        view.clieInputTextDireccion.setText("");
        view.clieInputTextTelefono.setText("");
        view.clieInputTextEmail.setText("");
    }

    public static void eliminarCliente(Index view, ClienteConsultas services) {
        try {
            services.deleteCliente(Integer.parseInt(view.clieInputTextId.getText()));

            DefaultTableModel clientesModel = (DefaultTableModel) view.clieTabla.getModel();
            clientesModel.removeRow(view.clieTabla.getSelectedRow());

            clearInputTexts(view);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}

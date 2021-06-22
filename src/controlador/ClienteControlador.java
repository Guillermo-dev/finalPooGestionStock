package controlador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import vista.Index;
import modelo.services.ClienteConsultas;

public class ClienteControlador {

    public static void cargarTabla(JTable clieTabla, ArrayList<Cliente> clientes) {
        DefaultTableModel tableModel = (DefaultTableModel) clieTabla.getModel();
        tableModel.setNumRows(0);

        clientes.forEach((cliente) -> {
            String[] data = new String[7];
            data[0] = Integer.toString(cliente.getId());
            data[1] = cliente.getApellido();
            data[2] = cliente.getNombre();
            data[3] = cliente.getDni();
            data[4] = cliente.getDireccion();
            data[5] = cliente.getTelefono();
            data[6] = cliente.getEmail();
            tableModel.addRow(data);
        });
    }

    public static void iniciarTabla(JTable clieTabla, ClienteConsultas services) {
        ArrayList<Cliente> clientes = services.getAllClientes();
        cargarTabla(clieTabla, clientes);
    }

    public static void buscarTabla(JTable clieTabla, ClienteConsultas services, String buscador) {
        ArrayList<Cliente> clientes = services.getClientesBusacador(buscador);
        cargarTabla(clieTabla, clientes);
    }

    public static void cargarInputTexts(Index view, String id, String apellido, String nombre, String dni, String direccion, String telefono, String email) {
        view.clieInputTextId.setText(id);
        view.clieInputTextApellido.setText(apellido);
        view.clieInputTextNombre.setText(nombre);
        view.clieInputTextDni.setText(dni);
        view.clieInputTextDireccion.setText(direccion);
        view.clieInputTextTelefono.setText(telefono);
        view.clieInputTextEmail.setText(email);
    }

    public static void vaciarInputTexts(Index view) {
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

            vaciarInputTexts(view);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(null, "ERROR INESPERADO \n Intentolo mas tarde");
        }

    }
}

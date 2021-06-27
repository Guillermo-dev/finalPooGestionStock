package controlador;

import controlador.excepciones.Excepcion;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import vista.Index;
import modelo.services.ClienteConsultas;
import org.hibernate.exception.ConstraintViolationException;

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

    public static void iniciarTabla(Index view) {
        ArrayList<Cliente> clientes = ClienteConsultas.getAllClientes();
        cargarTabla(view.clieTabla, clientes);
    }

    public static void buscarTabla(Index view) {
        String buscador = view.clieInputTextBuscador.getText();
        ArrayList<Cliente> clientes = ClienteConsultas.getClientesBusacador(buscador);
        cargarTabla(view.clieTabla, clientes);
    }

    public static void cargarInputTexts(Index view) {
        view.clieInputTextId.setText(view.clieTabla.getValueAt(view.clieTabla.getSelectedRow(), 0).toString());
        view.clieInputTextApellido.setText(view.clieTabla.getValueAt(view.clieTabla.getSelectedRow(), 1).toString());
        view.clieInputTextNombre.setText(view.clieTabla.getValueAt(view.clieTabla.getSelectedRow(), 2).toString());
        view.clieInputTextDni.setText(view.clieTabla.getValueAt(view.clieTabla.getSelectedRow(), 3).toString());
        view.clieInputTextDireccion.setText(view.clieTabla.getValueAt(view.clieTabla.getSelectedRow(), 4).toString());
        view.clieInputTextTelefono.setText(view.clieTabla.getValueAt(view.clieTabla.getSelectedRow(), 5).toString());
        view.clieInputTextEmail.setText(view.clieTabla.getValueAt(view.clieTabla.getSelectedRow(), 6).toString());
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

    public static boolean inputsTextInvalidos(Index view) {
        //Reviso que los campos esten llenos
        if (view.clieInputTextApellido.getText().equals("")
                || view.clieInputTextNombre.getText().equals("")
                || view.clieInputTextDni.getText().equals("")
                || view.clieInputTextDireccion.getText().equals("")
                || view.clieInputTextTelefono.getText().equals("")
                || view.clieInputTextEmail.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
            return true;
        }
        //Reviso que el formato de los datos sean validos
        else{
            try {
                Long.parseLong(view.clieInputTextDni.getText());
                Long.parseLong(view.clieInputTextTelefono.getText());
                Excepcion.comprobarTextos(view.clieInputTextApellido.getText(), "apellido");
                Excepcion.comprobarTextos(view.clieInputTextNombre.getText(), "nombre");
                Excepcion.comprobarEmail(view.clieInputTextEmail.getText());
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Ingrese solo valores numericos para DNI y/o telefono.");
                return true;
            }
            catch (Excepcion e){
                JOptionPane.showMessageDialog(null, e.getMessage());
                return true;
            }
            return false;
        }
    }
    
    
    public static boolean clienteSeleccionado(Index view) {
        return !view.clieInputTextId.getText().equals("");
    }

    public static void guardarCliente(Index view) {
        if (inputsTextInvalidos(view)) {
            System.out.println("Error al cargar cliente");
        } else {
            Cliente cliente = new Cliente(
                    view.clieInputTextNombre.getText(),
                    view.clieInputTextApellido.getText(),
                    view.clieInputTextDni.getText(),
                    view.clieInputTextDireccion.getText(),
                    view.clieInputTextTelefono.getText(),
                    view.clieInputTextEmail.getText());
            if (clienteSeleccionado(view)) {
                try {
                    ClienteConsultas.updateCliente(cliente, Integer.parseInt(view.clieInputTextId.getText()));
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            } else {
                try {
                    ClienteConsultas.saveCliente(cliente);
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            }
            iniciarTabla(view);
            vaciarInputTexts(view);
        }
    }

    public static void eliminarCliente(Index view) {
        try {
            ClienteConsultas.deleteCliente(Integer.parseInt(view.clieInputTextId.getText()));

            DefaultTableModel tablaModel = (DefaultTableModel) view.clieTabla.getModel();
            tablaModel.removeRow(view.clieTabla.getSelectedRow());

            vaciarInputTexts(view);
        } catch (ConstraintViolationException e) {
            JOptionPane.showMessageDialog(view, "El cliente esata asociado a una factura, no se puede eliminar");
        }

    }
}

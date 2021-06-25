package controlador;

import controlador.excepciones.excepcion;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
                int dni = Integer.parseInt(view.clieInputTextDni.getText());
                int telefono = Integer.parseInt(view.clieInputTextTelefono.getText());
                comprobarTextos(view.clieInputTextApellido.getText());
                comprobarTextos(view.clieInputTextNombre.getText());
                comprobarEmail(view.clieInputTextEmail.getText());
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Ingrese solo valores numericos para DNI y/o telefono.");
                return true;
            }
            catch (excepcion e){
                JOptionPane.showMessageDialog(null, e.getMessage());
                return true;
            }
            return false;
        }
    }
    
    static void comprobarEmail(String email) throws excepcion{
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        Matcher mather = pattern.matcher(email);
        if (!mather.find()){
            throw new excepcion("El email ingresado no es valido");
        }
    }
    static void comprobarTextos (String texto) throws excepcion{
        //Compruebo si hay un caracter especial o numero
        String REG_EXP = "\\¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|" +
        "\\%+|\\&+|\\+|\\=+|\\’+|\\¡+|\\++|\\*+|\\~+|\\[+|\\]" +
        "+|\\{+|\\}+|\\^+|\\<+|\\>+|\\@|\\_+|\\-+|[0-9]";
        Pattern pattern = Pattern.compile(REG_EXP);
        Matcher matcher = pattern.matcher(texto);
        if(matcher.find()){
            throw new excepcion("El nombre/apellido no puede tener caracteres especiales ni numeros");
        };
    }

    public static boolean clienteSeleccionado(Index view) {
        return !view.clieInputTextId.getText().equals("");
    }

    public static void guardarCliente(Index view, ClienteConsultas services) {
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
                    services.updateCliente(cliente, Integer.parseInt(view.clieInputTextId.getText()));
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            } else {
                try {
                    services.saveCliente(cliente);
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            }
            iniciarTabla(view.clieTabla, services);
            vaciarInputTexts(view);
        }
    }

    public static void eliminarCliente(Index view, ClienteConsultas services) {
        try {
            services.deleteCliente(Integer.parseInt(view.clieInputTextId.getText()));

            DefaultTableModel tablaModel = (DefaultTableModel) view.clieTabla.getModel();
            tablaModel.removeRow(view.clieTabla.getSelectedRow());

            vaciarInputTexts(view);
        } catch (Exception e) {
            // TODO
            System.out.println("asdasd");
            JOptionPane.showMessageDialog(null, "ERROR INESPERADO \n Intentolo mas tarde");
        }

    }
}

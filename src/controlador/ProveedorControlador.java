package controlador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Proveedor;
import vista.Index;
import modelo.services.ProveedorConsultas;
import javax.swing.DefaultComboBoxModel;

public class ProveedorControlador {

    // En bd responsable_inscripto", "monotributista", "consumidor_final"
    static String[] RAZONES_SOCIALES = {"Responsable inscripto", "Monotributista", "Consumidor final"};

    public static void iniciarDropdownRazonSocial(Index view) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.provDropdownRazonSocial.getModel();
             dropModel.addElement("<Seleccionar Razon Social>");
        for (String RAZONES_SOCIALES1 : RAZONES_SOCIALES) {
            dropModel.addElement(RAZONES_SOCIALES1);
        }
    }

    public static void cargarTabla(JTable provTabla, ArrayList<Proveedor> proveedores) {
        DefaultTableModel tableModel = (DefaultTableModel) provTabla.getModel();
        tableModel.setNumRows(0);

        proveedores.forEach((proveedor) -> {
            String[] data = new String[7];
            data[0] = Integer.toString(proveedor.getId());
            data[1] = proveedor.getCuilCuit();
            data[2] = proveedor.getNombre();
            data[3] = proveedor.getRazonSocial();
            data[4] = proveedor.getDireccion();
            data[5] = proveedor.getTelefono();
            data[6] = proveedor.getEmail();
            tableModel.addRow(data);
        });
    }

    public static void iniciarTabla(JTable provTabla, ProveedorConsultas services) {
        ArrayList<Proveedor> proveedores = services.getAllProveedores();
        cargarTabla(provTabla, proveedores);
    }

    public static void buscarTabla(JTable provTabla, ProveedorConsultas services, String buscador) {
        ArrayList<Proveedor> proveedores = services.getProveedoresBuscador(buscador);
        cargarTabla(provTabla, proveedores);
    }

    public static void cargarInputTexts(Index view, String id, String cuilCuit, String nombre, String razonSocial, String direccion, String telefono, String email) {
        view.provInputTextId.setText(id);
        view.provInputTextCuilT.setText(cuilCuit);
        view.provInputTextNombre.setText(nombre);
        view.provInputTextDireccion.setText(direccion);
        view.provDropdownRazonSocial.setSelectedItem(razonSocial);
        view.provInputTextDireccion.setText(direccion);
        view.provInputTextTelefono.setText(telefono);
        view.provInputTextEmail.setText(email);
    }

    public static void vaciarInputTexts(Index view) {
        view.provInputTextId.setText("");
        view.provInputTextCuilT.setText("");
        view.provInputTextNombre.setText("");
        view.provInputTextDireccion.setText("");
        view.provDropdownRazonSocial.setSelectedItem("<Seleccionar Razon Social>");
        view.provInputTextDireccion.setText("");
        view.provInputTextTelefono.setText("");
        view.provInputTextEmail.setText("");
    }

    public static boolean inputsTextInvalidos(Index view) {
        // TODO: Agregar logica de validacion
        return view.provInputTextCuilT.getText().equals("")
                || view.provInputTextNombre.getText().equals("")
                || view.provInputTextDireccion.getText().equals("")
                || view.provDropdownRazonSocial.getSelectedItem().equals("<Seleccionar Razon Social>")
                || view.provInputTextDireccion.getText().equals("")
                || view.provInputTextTelefono.getText().equals("")
                || view.provInputTextEmail.getText().equals("");
    }

    public static boolean proveedorSeleccionado(Index view) {
        return !view.provInputTextId.getText().equals("");
    }

    public static void guardarProveedor(Index view, ProveedorConsultas services) {
        if (inputsTextInvalidos(view)) {
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
        } else {
            Proveedor proveedor = new Proveedor(
                    view.provInputTextNombre.getText(),
                    view.provInputTextCuilT.getText(),
                    view.provDropdownRazonSocial.getSelectedItem().toString(),
                    view.provInputTextDireccion.getText(),                  
                    view.provInputTextTelefono.getText(),
                    view.provInputTextEmail.getText());
            if (proveedorSeleccionado(view)) {
                try {
                    services.updateProveedor(proveedor, Integer.parseInt(view.provInputTextId.getText()));
                } catch (Exception e) {
                    //TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            }else { 
                try{
                    services.saveProveedor(proveedor);
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            }
            iniciarTabla(view.provTabla, services);
            vaciarInputTexts(view);
        }
    }
    
    public static void eliminarProveedor(Index view, ProveedorConsultas services){
        try {
            services.deleteProveedor(Integer.parseInt(view.provInputTextId.getText()));
            
            DefaultTableModel tablaModel = (DefaultTableModel) view.provTabla.getModel();
            tablaModel.removeRow(view.provTabla.getSelectedRow());
            
            vaciarInputTexts(view);
        }catch (Exception e) {
            // TODO
            JOptionPane.showMessageDialog(null, "ERROR INESPERADO \n Intentolo mas tarde");   
        } 
    }
}

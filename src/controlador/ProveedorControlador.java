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

    public static void inicialTabla(JTable provTabla, ProveedorConsultas services){
        ArrayList<Proveedor> proveedores = services.getAllProveedores();
        cargarTabla (provTabla, proveedores);
    }
    
    public static void buscarTabla (JTable provTabla, ProveedorConsultas services, String buscador){
        ArrayList<Proveedor> proveedores = services.getProveedoresBusacador(buscador);
        cargarTabla(provTabla, proveedores);
    }
    
    
    //ver la razon social
    public static void cargarInputTexts(Index view, String id, String cuilCuit, String nombre, String razonSocial, String direccion, String telefono,String email){
            view.provInputTextId.setText(id);
            view.provInputTextCuilT.setText(cuilCuit);
            view.provInputTextNombre.setText(nombre);
            view.provInputTextDireccion.setText(direccion);
            view.provDropdownRazonSocial.setSelectedItem(razonSocial);
    
    
    }
    
    
    //provInputTextId   provInputTextCuilT   provInputTextNombre   provDropdownRazonSocial  
    
    
    
}

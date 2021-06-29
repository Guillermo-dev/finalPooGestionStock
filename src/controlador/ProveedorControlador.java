package controlador;

import controlador.excepciones.Excepcion;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Proveedor;
import vista.Index;
import modelo.services.ProveedorConsultas;
import javax.swing.DefaultComboBoxModel;
import org.hibernate.exception.ConstraintViolationException;
import vista.ListaComprasProveedor;

public class ProveedorControlador {

    private static final String[] RAZONES_SOCIALES = {"Responsable inscripto", "Monotributista", "Consumidor final"};
    private static final String SELECCIONAR_RAZON_SOCIAL = "<Seleccionar Razon Social>";

    public static void iniciarDropdownRazonSocial(Index view) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.provDropdownRazonSocial.getModel();

        view.provDropdownRazonSocial.removeAllItems();
        dropModel.addElement(SELECCIONAR_RAZON_SOCIAL);
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

    public static void iniciarTabla(Index view) {
        ArrayList<Proveedor> proveedores = ProveedorConsultas.getAllProveedores();
        cargarTabla(view.provTabla, proveedores);
    }

    public static void buscarTabla(Index view) {
        String buscador = view.provInputTextBuscador.getText();
        ArrayList<Proveedor> proveedores = ProveedorConsultas.getProveedoresBuscador(buscador);
        cargarTabla(view.provTabla, proveedores);
    }

    public static void cargarInputTexts(Index view) {
        view.provInputTextId.setText(view.provTabla.getValueAt(view.provTabla.getSelectedRow(), 0).toString());
        view.provInputTextCuilT.setText(view.provTabla.getValueAt(view.provTabla.getSelectedRow(), 1).toString());
        view.provInputTextNombre.setText(view.provTabla.getValueAt(view.provTabla.getSelectedRow(), 2).toString());
        view.provDropdownRazonSocial.setSelectedItem(view.provTabla.getValueAt(view.provTabla.getSelectedRow(), 3).toString());
        view.provInputTextDireccion.setText(view.provTabla.getValueAt(view.provTabla.getSelectedRow(), 4).toString());
        view.provInputTextTelefono.setText(view.provTabla.getValueAt(view.provTabla.getSelectedRow(), 5).toString());
        view.provInputTextEmail.setText(view.provTabla.getValueAt(view.provTabla.getSelectedRow(), 6).toString());
    }

    public static void vaciarInputTexts(Index view) {
        view.provInputTextId.setText("");
        view.provInputTextCuilT.setText("");
        view.provInputTextNombre.setText("");
        view.provInputTextDireccion.setText("");
        view.provDropdownRazonSocial.setSelectedItem(SELECCIONAR_RAZON_SOCIAL);
        view.provInputTextDireccion.setText("");
        view.provInputTextTelefono.setText("");
        view.provInputTextEmail.setText("");
    }

    public static boolean inputsTextInvalidos(Index view) {
        if (view.provInputTextCuilT.getText().equals("")
                || view.provInputTextNombre.getText().equals("")
                || view.provInputTextDireccion.getText().equals("")
                || view.provDropdownRazonSocial.getSelectedItem().equals(SELECCIONAR_RAZON_SOCIAL)
                || view.provInputTextDireccion.getText().equals("")
                || view.provInputTextTelefono.getText().equals("")
                || view.provInputTextEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(view, "Rellene todos los campos");
            return true;
        } else {
            try {
                Excepcion.comprobarEmail(view.provInputTextEmail.getText());
                Long.parseLong(view.provInputTextTelefono.getText());
                Long.parseLong(view.provInputTextCuilT.getText());
                Excepcion.comprobarTextos(view.provInputTextNombre.getText(), "nombre");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Error, compruebe los datos del proveedor");
                return true;
            } catch (Excepcion e) {
                JOptionPane.showMessageDialog(view, e.getMessage());
                return true;
            }
            return false;
        }
    }

    public static boolean proveedorSeleccionado(Index view) {
        return !view.provInputTextId.getText().equals("");
    }

    public static void guardarProveedor(Index view) {
        if (!inputsTextInvalidos(view)) {
            Proveedor proveedor = new Proveedor(
                    view.provInputTextNombre.getText(),
                    view.provInputTextCuilT.getText(),
                    view.provDropdownRazonSocial.getSelectedItem().toString(),
                    view.provInputTextDireccion.getText(),
                    view.provInputTextTelefono.getText(),
                    view.provInputTextEmail.getText());
            if (proveedorSeleccionado(view)) {
                try {
                    ProveedorConsultas.updateProveedor(proveedor, Integer.parseInt(view.provInputTextId.getText()));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, "Error al actualizar el proveedor");
                }
            } else {
                try {
                    ProveedorConsultas.saveProveedor(proveedor);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, "Error al guardar el proveedor");
                }
            }
            iniciarTabla(view);
            vaciarInputTexts(view);
        }
    }

    public static void eliminarProveedor(Index view) {
        if (proveedorSeleccionado(view)) {
            try {
                ProveedorConsultas.deleteProveedor(Integer.parseInt(view.provInputTextId.getText()));

                DefaultTableModel tablaModel = (DefaultTableModel) view.provTabla.getModel();
                tablaModel.removeRow(view.provTabla.getSelectedRow());

                vaciarInputTexts(view);
            } catch (ConstraintViolationException e) {
                JOptionPane.showMessageDialog(view, "No puede borrar éste proveedor.  \n Está asociado a una factura");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Seleccione un proveedor");
        }

    }

    public static void abrirListaCompra(Index view, ListaComprasProveedor listaProveedores) {
        if (view.provInputTextId.getText().equals("")) {
            JOptionPane.showMessageDialog(view, "Seleccione un proveedor");
        } else {
            int idProveedor = Integer.parseInt(view.provInputTextId.getText());
            Proveedor proveedor = ProveedorConsultas.getProveedor(idProveedor);
            ListaProvControlador.abrirListaCompra(listaProveedores, proveedor);
        }
    }
}

package controlador;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;
import modelo.Proveedor;
import modelo.Rubro;
import vista.Index;
import modelo.services.ArticuloConsultas;
import modelo.services.ProveedorConsultas;
import modelo.services.RubroConsultas;
import org.hibernate.exception.ConstraintViolationException;

public class ArticuloControlador {

    public static void iniciarDropdownRubros(Index view) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.artDropdownRubro.getModel();
        ArrayList<Rubro> rubros = RubroConsultas.getAllRubros();

        view.artDropdownRubro.removeAllItems();
        dropModel.addElement("<Seleccionar rubro>");
        rubros.forEach(rubro -> {
            dropModel.addElement(rubro.getId() + "- " + rubro.getNombre());
        });
        dropModel.addElement("Nuevo rubro");
    }

    public static void iniciarDropdownProveedores(Index view) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.artDropdownProveedor.getModel();
        ArrayList<Proveedor> proveedores = ProveedorConsultas.getAllProveedores();

        view.artDropdownProveedor.removeAllItems();
        dropModel.addElement("<Seleccionar Proveedor>");
        proveedores.forEach(proveedor -> {
            dropModel.addElement(proveedor.getId() + "- " + proveedor.getNombre());
        });
    }

    public static void cargarTabla(JTable artTabla, ArrayList<Articulo> articulos) {
        DefaultTableModel tableModel = (DefaultTableModel) artTabla.getModel();
        tableModel.setNumRows(0);

        articulos.forEach((articulo) -> {
            String[] data = new String[8];
            data[0] = Integer.toString(articulo.getId());
            data[1] = articulo.getNombre();
            data[2] = articulo.getRubro().getId() + "- " + articulo.getRubro().getNombre();
            data[3] = articulo.getDescripcion();
            data[4] = articulo.getProveedor().getId() + "- " + articulo.getProveedor().getNombre();
            data[5] = Integer.toString(articulo.getStockActual());
            data[6] = Integer.toString(articulo.getStockMinimo());
            data[7] = String.valueOf(articulo.getPrecioUnitario());
            tableModel.addRow(data);
        });
    }

    public static void iniciarTabla(Index view) {
        ArrayList<Articulo> articulos = ArticuloConsultas.getAllArticulos();
        cargarTabla(view.artTabla, articulos);
    }

    public static void buscarTabla(Index view) {
        String buscador = view.artInputTextBuscador.getText();
        ArrayList<Articulo> articulos = ArticuloConsultas.getArticulosBusacador(buscador);
        cargarTabla(view.artTabla, articulos);
    }

    public static void stockMinimoTabla(Index view) {
        if (view.artCheckBoxStockMinimo.isSelected()) {
            ArrayList<Articulo> articulos = ArticuloConsultas.getArticulosStockMinimo();
            cargarTabla(view.artTabla, articulos);
        } else {
            iniciarTabla(view);
        }

    }

    public static void cargarInputTexts(Index view) {
        view.artInputTextId.setText(view.artTabla.getValueAt(view.artTabla.getSelectedRow(), 0).toString());
        view.artInputTextNombre.setText(view.artTabla.getValueAt(view.artTabla.getSelectedRow(), 1).toString());
        view.artDropdownRubro.setSelectedItem(view.artTabla.getValueAt(view.artTabla.getSelectedRow(), 2).toString());
        view.artInputTextDescripcion.setText(view.artTabla.getValueAt(view.artTabla.getSelectedRow(), 3).toString());
        view.artDropdownProveedor.setSelectedItem(view.artTabla.getValueAt(view.artTabla.getSelectedRow(), 4).toString());
        view.artInputTextStock.setText(view.artTabla.getValueAt(view.artTabla.getSelectedRow(), 5).toString());
        view.artInputTextStockMin.setText(view.artTabla.getValueAt(view.artTabla.getSelectedRow(), 6).toString());
        view.artInputTextPrecio.setText(view.artTabla.getValueAt(view.artTabla.getSelectedRow(), 7).toString());
    }

    public static void vaciarInputTexts(Index view) {
        view.artInputTextId.setText("");
        view.artInputTextNombre.setText("");
        view.artInputTextDescripcion.setText("");
        view.artInputTextPrecio.setText("");
        view.artInputTextStock.setText("");
        view.artInputTextStockMin.setText("");
        view.artDropdownRubro.setSelectedItem("<Seleccionar rubro>");
        view.artDropdownProveedor.setSelectedItem("<Seleccionar Proveedor>");
    }

    public static boolean inputsTextInvalidos(Index view) {
        if (view.artInputTextNombre.equals("")
                || view.artInputTextDescripcion.equals("")
                || view.artInputTextPrecio.equals("")
                || view.artInputTextStock.equals("")
                || view.artInputTextStockMin.equals("")
                || view.artDropdownRubro.getSelectedItem() == "<Seleccionar rubro>"
                || view.artDropdownProveedor.getSelectedItem() == "<Seleccionar Proveedor>") {
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
            return true;
        } else {
            try {
                Integer.parseInt(view.artInputTextStock.getText());
                Integer.parseInt(view.artInputTextStockMin.getText());
                Double.parseDouble(view.artInputTextPrecio.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese solo valores numericos enteros para los stocks y valores reales para el precio.");
                return true;
            }
            return false;
        }
    }

    public static boolean articuloSeleccionado(Index view) {
        return !view.artInputTextId.getText().equals("");
    }

    public static void guardarArticulo(Index view) {
        if (inputsTextInvalidos(view)) {
            System.out.println("Error al cargar articulo");
        } else {
            int idProveedor = Integer.parseInt(view.artDropdownProveedor.getSelectedItem().toString().split("-")[0]);
            int idRubro = Integer.parseInt(view.artDropdownRubro.getSelectedItem().toString().split("-")[0]);
            Proveedor proveedor = ProveedorConsultas.getProveedor(idProveedor);
            Rubro rubro = RubroConsultas.getRubro(idRubro);

            Articulo articulo = new Articulo(proveedor, rubro,
                    view.artInputTextNombre.getText(),
                    view.artInputTextDescripcion.getText(),
                    Float.parseFloat(view.artInputTextPrecio.getText()),
                    Integer.parseInt(view.artInputTextStock.getText()),
                    Integer.parseInt(view.artInputTextStockMin.getText())
            );
            if (articuloSeleccionado(view)) {
                try {
                    ArticuloConsultas.updateArticulo(articulo, Integer.parseInt(view.artInputTextId.getText()));
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            } else {
                try {
                    ArticuloConsultas.saveArticulo(articulo);
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            }
            iniciarTabla(view);
            vaciarInputTexts(view);
        }
    }

    public static void eliminarArticulo(Index view) {
        try {
            ArticuloConsultas.deleteArticulo(Integer.parseInt(view.artInputTextId.getText()));

            DefaultTableModel tablaModel = (DefaultTableModel) view.artTabla.getModel();
            tablaModel.removeRow(view.artTabla.getSelectedRow());

            vaciarInputTexts(view);
        } catch (ConstraintViolationException e) {
            JOptionPane.showMessageDialog(view, "Este articulo esta asociado a una factura");
        }
    }

    public static boolean nuevoRubroSeleccionado(Index view) {
        if (view.artDropdownRubro.getSelectedItem() == "Nuevo rubro") {
            int resp = JOptionPane.showConfirmDialog(view, "Crear nuevo rubro?");
            return (resp == 0);
        }
        return false;
    }
}

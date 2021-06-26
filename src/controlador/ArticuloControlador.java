package controlador;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
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

public class ArticuloControlador {

    public static void iniciarDropdownRubros(Index view, RubroConsultas services) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.artDropdownRubro.getModel();
        ArrayList<Rubro> rubros = services.getAllRubros();

        view.artDropdownRubro.removeAllItems();
        dropModel.addElement("<Seleccionar rubro>");
        rubros.forEach(rubro -> {
            dropModel.addElement(rubro.getId() + "- " + rubro.getNombre());
        });
        dropModel.addElement("Nuevo rubro");
    }

    public static void iniciarDropdownProveedores(Index view, ProveedorConsultas services) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.artDropdownProveedor.getModel();
        ArrayList<Proveedor> proveedores = services.getAllProveedores();

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

    public static void iniciarTabla(JTable artTabla, ArticuloConsultas services) {
        ArrayList<Articulo> articulos = services.getAllArticulos();
        cargarTabla(artTabla, articulos);
    }

    public static void buscarTabla(JTable artTabla, ArticuloConsultas services, String buscador) {
        ArrayList<Articulo> articulos = services.getArticulosBusacador(buscador);
        cargarTabla(artTabla, articulos);
    }

    public static void stockMinimoTabla(JTable artTabla, JCheckBox checkBoxStockMinimo, ArticuloConsultas services) {
        if (checkBoxStockMinimo.isSelected()) {
            ArrayList<Articulo> articulos = services.getArticulosStockMinimo();
            cargarTabla(artTabla, articulos);
        } else {
            iniciarTabla(artTabla, services);
        }

    }

    public static void cargarInputTexts(Index view, String id, String nombre, String rubro, String descripcion, String proveedor, String stockActual, String stockMinimo, String precio) {
        view.artInputTextId.setText(id);
        view.artInputTextNombre.setText(nombre);
        view.artDropdownRubro.setSelectedItem(rubro);
        view.artInputTextDescripcion.setText(descripcion);
        view.artDropdownProveedor.setSelectedItem(proveedor);
        view.artInputTextStock.setText(stockActual);
        view.artInputTextStockMin.setText(stockMinimo);
        view.artInputTextPrecio.setText(precio);
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
        return view.artInputTextNombre.equals("")
                || view.artInputTextDescripcion.equals("")
                || view.artInputTextPrecio.equals("")
                || view.artInputTextStock.equals("")
                || view.artInputTextStockMin.equals("")
                || view.artDropdownRubro.getSelectedItem() == "<Seleccionar rubro>"
                || view.artDropdownProveedor.getSelectedItem() == "<Seleccionar Proveedor>";
    }

    public static boolean articuloSeleccionado(Index view) {
        return !view.artInputTextId.getText().equals("");
    }

    public static void guardarArticulo(Index view, ArticuloConsultas servicesArt, RubroConsultas servicesRub, ProveedorConsultas servicesProv) {
        if (inputsTextInvalidos(view)) {
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
        } else {
            int idProveedor = Integer.parseInt(view.artDropdownProveedor.getSelectedItem().toString().split("-")[0]);
            int idRubro = Integer.parseInt(view.artDropdownRubro.getSelectedItem().toString().split("-")[0]);
            Proveedor proveedor = servicesProv.getProveedor(idProveedor);
            Rubro rubro = servicesRub.getRubro(idRubro);

            Articulo articulo = new Articulo(proveedor, rubro,
                    view.artInputTextNombre.getText(),
                    view.artInputTextDescripcion.getText(),
                    Float.parseFloat(view.artInputTextPrecio.getText()),
                    Integer.parseInt(view.artInputTextStock.getText()),
                    Integer.parseInt(view.artInputTextStockMin.getText())
            );
            if (articuloSeleccionado(view)) {
                try {
                    servicesArt.updateArticulo(articulo, Integer.parseInt(view.artInputTextId.getText()));
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            } else {
                try {
                    servicesArt.saveArticulo(articulo);
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            }
            iniciarTabla(view.artTabla, servicesArt);
            vaciarInputTexts(view);
        }
    }

    public static void eliminarArticulo(Index view, ArticuloConsultas services) {
        try {
            services.deleteArticulo(Integer.parseInt(view.artInputTextId.getText()));

            DefaultTableModel tablaModel = (DefaultTableModel) view.artTabla.getModel();
            tablaModel.removeRow(view.artTabla.getSelectedRow());

            vaciarInputTexts(view);
        } catch (Exception e) {
            // TODO
            JOptionPane.showMessageDialog(null, "ERROR INESPERADO \n Intentolo mas tarde");
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

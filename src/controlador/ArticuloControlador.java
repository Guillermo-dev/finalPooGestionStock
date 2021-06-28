package controlador;

import controlador.excepciones.Excepcion;
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
    
    private static final String SELECCIONAR_RUBRO = "<Seleccionar rubro>";
    private static final String NUEVO_RUBRO = "Nuevo rubro";
    private static final String SELECCIONAR_PROVEEDOR = "<Seleccionar Proveedor>";
    

    public static void iniciarDropdownRubros(Index view) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.artDropdownRubro.getModel();
        ArrayList<Rubro> rubros = RubroConsultas.getAllRubros();

        view.artDropdownRubro.removeAllItems();
        dropModel.addElement(SELECCIONAR_RUBRO);
        rubros.forEach(rubro -> {
            dropModel.addElement(rubro.getId() + "- " + rubro.getNombre());
        });
        dropModel.addElement(NUEVO_RUBRO);
    }

    public static void iniciarDropdownProveedores(Index view) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.artDropdownProveedor.getModel();
        ArrayList<Proveedor> proveedores = ProveedorConsultas.getAllProveedores();

        view.artDropdownProveedor.removeAllItems();
        dropModel.addElement(SELECCIONAR_PROVEEDOR);
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
        view.artCheckBoxStockMinimo.setSelected(false);
        String buscador = view.artInputTextBuscador.getText();
        ArrayList<Articulo> articulos = ArticuloConsultas.getArticulosBusacador(buscador);
        cargarTabla(view.artTabla, articulos);
    }

    public static void stockMinimoTabla(Index view) {
        view.artInputTextBuscador.setText("");
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
        view.artDropdownRubro.setSelectedItem(SELECCIONAR_RUBRO);
        view.artDropdownProveedor.setSelectedItem(SELECCIONAR_PROVEEDOR);
    }

    public static boolean inputsTextInvalidos(Index view) {
        if (view.artInputTextNombre.equals("")
                || view.artInputTextDescripcion.equals("")
                || view.artInputTextPrecio.equals("")
                || view.artInputTextStock.equals("")
                || view.artInputTextStockMin.equals("")
                || view.artDropdownRubro.getSelectedItem() == SELECCIONAR_RUBRO
                || view.artDropdownProveedor.getSelectedItem() == SELECCIONAR_PROVEEDOR) {
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
            return true;
        } else {
            try {
                int stock = Integer.parseInt(view.artInputTextStock.getText());
                int stock_min = Integer.parseInt(view.artInputTextStockMin.getText());
                Double.parseDouble(view.artInputTextPrecio.getText());
                // Excepcion.comprobarStocks(stock_min, stock);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese solo valores numericos enteros para los stocks y valores reales para el precio.");
                return true;
            }
            /*catch (Excepcion e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return true;
            }*/
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
        if (articuloSeleccionado(view)) {
            try {
                ArticuloConsultas.deleteArticulo(Integer.parseInt(view.artInputTextId.getText()));

                DefaultTableModel tablaModel = (DefaultTableModel) view.artTabla.getModel();
                tablaModel.removeRow(view.artTabla.getSelectedRow());

                vaciarInputTexts(view);
            } catch (ConstraintViolationException e) {
                JOptionPane.showMessageDialog(view, "Este articulo esta asociado a una factura");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un articulo");
        }

    }

    public static boolean nuevoRubroSeleccionado(Index view) {
        if (view.artDropdownRubro.getSelectedItem() == NUEVO_RUBRO) {
            int resp = JOptionPane.showConfirmDialog(view, "Crear nuevo rubro?");
            return (resp == 0);
        }
        return false;
    }
}

package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;
import modelo.Factura;
import modelo.Linea;
import modelo.Proveedor;
import modelo.Rubro;
import modelo.services.ArticuloConsultas;
import modelo.services.FacturaConsultas;
import modelo.services.ProveedorConsultas;
import modelo.services.RubroConsultas;
import vista.FacturaVistaCompra;
import vista.Index;

public class FacturaCompraControlador {

    public static void inicializarDropdownProveedores(FacturaVistaCompra facturaDetallesCompra) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesCompra.dropdownProveedor.getModel();
        ArrayList<Proveedor> proveedores = ProveedorConsultas.getAllProveedores();

        facturaDetallesCompra.dropdownProveedor.removeAllItems();
        dropModel.addElement("<Seleccionar Proveedor>");
        proveedores.forEach(proveedor -> {
            dropModel.addElement(proveedor.getId() + "- " + proveedor.getNombre());
        });
    }

    public static void inicializarDropdownArticulos(FacturaVistaCompra facturaDetallesCompra) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesCompra.dropdownArticulo.getModel();
        facturaDetallesCompra.dropdownArticulo.removeAllItems();
        dropModel.addElement("<Seleccionar Articulo>");
        dropModel.addElement("Nuevo Articulo");
    }

    public static void vaciarInputTextArticulo(FacturaVistaCompra facturaDetallesCompra) {
        facturaDetallesCompra.dropdownArticulo.setSelectedItem("<Seleccionar Articulo>");
        facturaDetallesCompra.dropdownRubro.setSelectedItem("<Seleccionar rubro>");
        facturaDetallesCompra.inputTextNombre.setText("");
        facturaDetallesCompra.inputTextPrecio.setText("");
        facturaDetallesCompra.spinnerCantidad.setValue(1);
        facturaDetallesCompra.inputTextDescripcion.setText("");
        facturaDetallesCompra.inputTextStockMinimo.setText("");
    }

    public static void agregarArticulosDropdown(FacturaVistaCompra facturaDetallesCompra) {
        vaciarInputTextArticulo(facturaDetallesCompra);
        if (facturaDetallesCompra.dropdownProveedor.getItemCount() != 0) {
            if (!facturaDetallesCompra.dropdownProveedor.getSelectedItem().equals("<Seleccionar Proveedor>")) {
                DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesCompra.dropdownArticulo.getModel();

                facturaDetallesCompra.dropdownArticulo.removeAllItems();
                dropModel.addElement("<Seleccionar Articulo>");
                dropModel.addElement("Nuevo Articulo");
                int idProveedor = Integer.parseInt(facturaDetallesCompra.dropdownProveedor.getSelectedItem().toString().split("-")[0]);
                ArrayList<Articulo> articulos = ArticuloConsultas.getAllArticulosIdProveedor(idProveedor);

                articulos.forEach(articulo -> {
                    dropModel.addElement(articulo.getId() + "- " + articulo.getNombre());
                });
            }
        }
    }

    public static void iniciarDropdownRubros(FacturaVistaCompra facturaDetallesCompra) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesCompra.dropdownRubro.getModel();
        ArrayList<Rubro> rubros = RubroConsultas.getAllRubros();

        dropModel.addElement("<Seleccionar rubro>");
        rubros.forEach(rubro -> {
            dropModel.addElement(rubro.getId() + "- " + rubro.getNombre());
        });
    }

    public static void noNegativosSpinner(JSpinner spinner) {
        if ((Integer) spinner.getValue() < 1) {
            spinner.setValue(1);
        }
    }

    public static void cargarInputTexts(FacturaVistaCompra facturaDetallesCompra, String nombre, String descripcion, String rubro, String stockMinimo, String precio, String cantidad) {
        facturaDetallesCompra.dropdownArticulo.setSelectedItem("<Seleccionar Articulo>");
        facturaDetallesCompra.inputTextNombre.setText(nombre);
        facturaDetallesCompra.dropdownRubro.setSelectedItem(rubro);
        facturaDetallesCompra.inputTextPrecio.setText(precio);
        facturaDetallesCompra.spinnerCantidad.setValue(Integer.parseInt(cantidad));
        facturaDetallesCompra.inputTextDescripcion.setText(descripcion);
        facturaDetallesCompra.inputTextStockMinimo.setText(stockMinimo);
    }

    public static void vaciarInputTexts(FacturaVistaCompra facturaDetallesCompra) {
        facturaDetallesCompra.inputTextNumero.setText("");
        facturaDetallesCompra.dropdownProveedor.setSelectedItem("<Seleccionar Proveedor>");
        facturaDetallesCompra.dropdownArticulo.setSelectedItem("<Seleccionar Articulo>");
        facturaDetallesCompra.dropdownRubro.setSelectedItem("<Seleccionar rubro>");
        facturaDetallesCompra.inputTextNombre.setText("");
        facturaDetallesCompra.inputTextPrecio.setText("");
        facturaDetallesCompra.spinnerCantidad.setValue(1);
        facturaDetallesCompra.inputTextDescripcion.setText("");
        facturaDetallesCompra.inputTextStockMinimo.setText("");
        facturaDetallesCompra.inputTextTotal.setText("");
    }

    public static void seleccionarArticuloCargarInputsText(FacturaVistaCompra facturaDetallesCompra) {
        if (facturaDetallesCompra.dropdownArticulo.getSelectedItem().equals("Nuevo Articulo")) {
            facturaDetallesCompra.inputTextNombre.setText("");
            facturaDetallesCompra.dropdownRubro.setSelectedItem("<Seleccionar rubro>");
            facturaDetallesCompra.inputTextPrecio.setText("");
            facturaDetallesCompra.inputTextDescripcion.setText("");
            facturaDetallesCompra.inputTextStockMinimo.setText("");
            facturaDetallesCompra.spinnerCantidad.setValue(1);
        } else if (!facturaDetallesCompra.dropdownArticulo.getSelectedItem().equals("<Seleccionar Articulo>")) {
            int idArticulo = Integer.parseInt(facturaDetallesCompra.dropdownArticulo.getSelectedItem().toString().split("-")[0]);
            Articulo articulo = ArticuloConsultas.getArticulo(idArticulo);
            facturaDetallesCompra.inputTextNombre.setText(articulo.getNombre());
            facturaDetallesCompra.dropdownRubro.setSelectedItem(articulo.getRubro().getId() + "- " + articulo.getRubro().getNombre());
            facturaDetallesCompra.inputTextPrecio.setText(Float.toString(articulo.getPrecioUnitario()));
            facturaDetallesCompra.inputTextDescripcion.setText(articulo.getDescripcion());
            facturaDetallesCompra.inputTextStockMinimo.setText(Integer.toString(articulo.getStockMinimo()));
            facturaDetallesCompra.spinnerCantidad.setValue(1);
        }
    }

    public static void seleccionarArticulo(FacturaVistaCompra facturaDetallesCompra) {
        if (facturaDetallesCompra.dropdownArticulo.getItemCount() != 0) {
            if (facturaDetallesCompra.dropdownArticulo.getSelectedItem().equals("Nuevo Articulo")) {
                facturaDetallesCompra.dropdownRubro.enable();
                facturaDetallesCompra.inputTextNombre.setEditable(true);
                facturaDetallesCompra.inputTextPrecio.setEditable(true);
                facturaDetallesCompra.inputTextDescripcion.setEditable(true);
                facturaDetallesCompra.inputTextStockMinimo.setEditable(true);
                seleccionarArticuloCargarInputsText(facturaDetallesCompra);
            } else {
                facturaDetallesCompra.dropdownRubro.disable();
                facturaDetallesCompra.inputTextNombre.setEditable(false);
                facturaDetallesCompra.inputTextPrecio.setEditable(false);
                facturaDetallesCompra.inputTextDescripcion.setEditable(false);
                facturaDetallesCompra.inputTextStockMinimo.setEditable(false);
                seleccionarArticuloCargarInputsText(facturaDetallesCompra);
            }
        }

    }

    public static void agregarArticulo(FacturaVistaCompra facturaDetallesCompra) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) facturaDetallesCompra.tabla.getModel();
            float subtotal = (Integer) facturaDetallesCompra.spinnerCantidad.getValue() * Float.parseFloat(facturaDetallesCompra.inputTextPrecio.getText());

            String[] data = new String[8];
            if (facturaDetallesCompra.dropdownArticulo.getSelectedItem().equals("Nuevo Articulo")) {
                data[0] = "NUEVO";
            } else {
                data[0] = facturaDetallesCompra.dropdownArticulo.getSelectedItem().toString().split("-")[0];
            }
            data[1] = facturaDetallesCompra.inputTextNombre.getText();
            data[2] = facturaDetallesCompra.inputTextDescripcion.getText();
            data[3] = facturaDetallesCompra.dropdownRubro.getSelectedItem().toString();
            data[4] = facturaDetallesCompra.inputTextStockMinimo.getText();
            data[5] = facturaDetallesCompra.inputTextPrecio.getText();
            data[6] = facturaDetallesCompra.spinnerCantidad.getValue().toString();
            data[7] = Float.toString(subtotal);
            tableModel.addRow(data);

            float total;
            if (facturaDetallesCompra.inputTextTotal.getText().equals("")) {
                total = subtotal;
            } else {
                total = Float.parseFloat(facturaDetallesCompra.inputTextTotal.getText()) + subtotal;
            }
            facturaDetallesCompra.inputTextTotal.setText(Float.toString(total));
            facturaDetallesCompra.dropdownProveedor.disable();
        } catch (Exception e) {
            // TODO: MANEJO VALIDACION
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error inesperado");
        }
    }

    public static void quitarArticulo(FacturaVistaCompra facturaDetallesCompra) {
        DefaultTableModel tablaModel = (DefaultTableModel) facturaDetallesCompra.tabla.getModel();
        float subtotal = Float.parseFloat(facturaDetallesCompra.tabla.getValueAt(facturaDetallesCompra.tabla.getSelectedRow(), 7).toString());
        float total = Float.parseFloat(facturaDetallesCompra.inputTextTotal.getText()) - subtotal;
        facturaDetallesCompra.inputTextTotal.setText(Float.toString(total));

        tablaModel.removeRow(facturaDetallesCompra.tabla.getSelectedRow());
        if (facturaDetallesCompra.tabla.getRowCount() == 0) {
            facturaDetallesCompra.dropdownProveedor.enable();
        }
    }

    public static boolean facturaInvalida(FacturaVistaCompra facturaDetallesCompra) {
        if (facturaDetallesCompra.tabla.getRowCount() == 0) {
            return true;
        }
        if (facturaDetallesCompra.dropdownProveedor.getSelectedItem().equals("<Seleccionar Proveedor>")) {
            return true;
        }
        if (facturaDetallesCompra.inputTextNumero.getText().equals("")) {
            return true;
        }
        return false;
    }

    public static ArrayList<Linea> generarLineas(FacturaVistaCompra facturaDetallesCompra, Factura factura, Proveedor proveedor) {
        ArrayList<Linea> lineas = new ArrayList<>();
        Articulo articulo;
        for (int i = 0; i <= facturaDetallesCompra.tabla.getRowCount() - 1; i++) {
            // guardar nuevo articulo
            if (facturaDetallesCompra.tabla.getValueAt(i, 0).toString().equals("NUEVO")) {
                Rubro rubro = RubroConsultas.getRubro(Integer.parseInt(facturaDetallesCompra.tabla.getValueAt(i, 3).toString().split("-")[0]));
                String nombre = facturaDetallesCompra.tabla.getValueAt(i, 1).toString();
                String descripcion = facturaDetallesCompra.tabla.getValueAt(i, 2).toString();
                float precioUnitario = Float.parseFloat(facturaDetallesCompra.tabla.getValueAt(i, 5).toString());
                int stockActual = Integer.parseInt(facturaDetallesCompra.tabla.getValueAt(i, 6).toString());
                int stockMinimo = Integer.parseInt(facturaDetallesCompra.tabla.getValueAt(i, 4).toString());
                articulo = new Articulo(proveedor, rubro, nombre, descripcion, precioUnitario, stockActual, stockMinimo);
                ArticuloConsultas.saveArticulo(articulo);
            } else {
                // actualizar stock articulo
                int idArticulo = Integer.parseInt(facturaDetallesCompra.tabla.getValueAt(i, 0).toString());
                articulo = ArticuloConsultas.getArticulo(idArticulo);

                articulo.sumarStock(Integer.parseInt(facturaDetallesCompra.tabla.getValueAt(i, 6).toString()));
                ArticuloConsultas.updateArticulo(articulo, idArticulo);
            }

            float precioUnitario = Float.parseFloat(facturaDetallesCompra.tabla.getValueAt(i, 5).toString());
            int cantidad = Integer.parseInt(facturaDetallesCompra.tabla.getValueAt(i, 6).toString());
            float subtotal = Float.parseFloat(facturaDetallesCompra.tabla.getValueAt(i, 7).toString());

            Linea nuevaLinea = new Linea(articulo, factura, precioUnitario, cantidad, subtotal);
            lineas.add(nuevaLinea);
        }
        return lineas;
    }

    public static void guardarFactura(Index view, FacturaVistaCompra facturaDetallesCompra) {
        if (facturaDetallesCompra.getTitle().equals("Detalles Factura")) {
            vaciarInputTexts(facturaDetallesCompra);
            facturaDetallesCompra.setVisible(false);
        } else {
            if (facturaInvalida(facturaDetallesCompra)) {
                JOptionPane.showMessageDialog(null, "Error intentelo nuevamente");
            } else {
                int idProveedor = Integer.parseInt(facturaDetallesCompra.dropdownProveedor.getSelectedItem().toString().split("-")[0]);

                Proveedor proveedor = ProveedorConsultas.getProveedor(idProveedor);
                String numeroFactura = facturaDetallesCompra.inputTextNumero.getText();
                Date fecha = new Date();
                float total = Float.parseFloat(facturaDetallesCompra.inputTextTotal.getText());

                Factura factura = new Factura(proveedor, 'C', numeroFactura, fecha, total);

                ArrayList<Linea> lineas = generarLineas(facturaDetallesCompra, factura, proveedor);
                factura.setLineas(lineas);

                FacturaConsultas.saveFactura(factura);

                vaciarInputTexts(facturaDetallesCompra);
                facturaDetallesCompra.setVisible(false);
                FacturaControlador.iniciarTabla(view.factTabla, view.factCheckBoxCompra, view.factCheckBoxVenta);
            }
        }
    }

    public static void activarInteraccionVista(FacturaVistaCompra facturaDetallesCompra) {
        facturaDetallesCompra.inputTextNumero.setEnabled(true);
        facturaDetallesCompra.dropdownArticulo.setEnabled(true);
        facturaDetallesCompra.dropdownProveedor.setEnabled(true);
        facturaDetallesCompra.inputTextNombre.setEnabled(true);
        facturaDetallesCompra.inputTextPrecio.setEditable(true);
        facturaDetallesCompra.spinnerCantidad.setEnabled(true);
        facturaDetallesCompra.inputTextDescripcion.setEditable(true);
        facturaDetallesCompra.inputTextStockMinimo.setEditable(true);
        facturaDetallesCompra.btnAgregarProducto.setVisible(true);
        facturaDetallesCompra.btnBorrarProducto.setVisible(true);
    }

    public static void abrirVistaFacturaCompra(FacturaVistaCompra facturaDetallesCompra) {
        facturaDetallesCompra.setTitle("Nueva factura de compra");
        facturaDetallesCompra.setLocationRelativeTo(null);
        facturaDetallesCompra.setVisible(true);

        activarInteraccionVista(facturaDetallesCompra);

        DefaultTableModel tableModel = (DefaultTableModel) facturaDetallesCompra.tabla.getModel();
        tableModel.setNumRows(0);
        vaciarInputTexts(facturaDetallesCompra);

        inicializarDropdownProveedores(facturaDetallesCompra);
        inicializarDropdownArticulos(facturaDetallesCompra);
        iniciarDropdownRubros(facturaDetallesCompra);
    }

    public static void cargarTablaVerMas(FacturaVistaCompra facturaDetallesCompra, List<Linea> lineas) {
        DefaultTableModel tableModel = (DefaultTableModel) facturaDetallesCompra.tabla.getModel();
        tableModel.setNumRows(0);
        lineas.forEach((Linea linea) -> {
            String[] data = new String[8];
            data[1] = linea.getArticulo().getId() + "- " + linea.getArticulo().getNombre();
            data[2] = linea.getArticulo().getDescripcion();
            data[3] = linea.getArticulo().getRubro().getId() + "- " + linea.getArticulo().getRubro().getNombre();
            data[4] = Integer.toString(linea.getArticulo().getStockMinimo());
            data[5] = Float.toString(linea.getPrecioUnitario());
            data[6] = Integer.toString(linea.getCantidad());
            data[7] = Float.toString(linea.getSubTotal());
            tableModel.addRow(data);
        });
    }

    public static void desactivarInteraccionVista(FacturaVistaCompra facturaDetallesCompra) {
        facturaDetallesCompra.inputTextNumero.setEnabled(false);
        facturaDetallesCompra.dropdownArticulo.setEnabled(false);
        facturaDetallesCompra.dropdownProveedor.setEnabled(false);
        facturaDetallesCompra.inputTextNombre.setEnabled(false);
        facturaDetallesCompra.inputTextPrecio.setEditable(false);
        facturaDetallesCompra.spinnerCantidad.setEnabled(false);
        facturaDetallesCompra.inputTextDescripcion.setEditable(false);
        facturaDetallesCompra.inputTextStockMinimo.setEditable(false);
        facturaDetallesCompra.btnAgregarProducto.setVisible(false); // setEnabled(false);
        facturaDetallesCompra.btnBorrarProducto.setVisible(false); // setEnabled(false);
    }

    public static void abrirVistaFacturaVenta(FacturaVistaCompra facturaDetallesCompra, Factura factura) {
        facturaDetallesCompra.setTitle("Detalles Factura");
        facturaDetallesCompra.setLocationRelativeTo(null);
        facturaDetallesCompra.setVisible(true);

        inicializarDropdownProveedores(facturaDetallesCompra);
        inicializarDropdownArticulos(facturaDetallesCompra);
        iniciarDropdownRubros(facturaDetallesCompra);

        desactivarInteraccionVista(facturaDetallesCompra);

        facturaDetallesCompra.inputTextNumero.setText(factura.getNumeroFactura());
        facturaDetallesCompra.dropdownProveedor.setSelectedItem(factura.getProveedor().getId() + "- " + factura.getProveedor().getNombre());
        cargarTablaVerMas(facturaDetallesCompra, factura.getLineas());
        facturaDetallesCompra.inputTextTotal.setText(Float.toString(factura.getTotal()));
    }

}

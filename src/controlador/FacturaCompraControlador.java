package controlador;

import controlador.excepciones.Excepcion;
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

    public static void inicializarDropdownProveedores(FacturaVistaCompra viewFacturasDetallesCompra) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) viewFacturasDetallesCompra.dropdownProveedor.getModel();
        ArrayList<Proveedor> proveedores = ProveedorConsultas.getAllProveedores();

        viewFacturasDetallesCompra.dropdownProveedor.removeAllItems();
        dropModel.addElement("<Seleccionar Proveedor>");
        proveedores.forEach(proveedor -> {
            dropModel.addElement(proveedor.getId() + "- " + proveedor.getNombre());
        });
    }

    public static void inicializarDropdownArticulos(FacturaVistaCompra viewFacturasDetallesCompra) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) viewFacturasDetallesCompra.dropdownArticulo.getModel();

        viewFacturasDetallesCompra.dropdownArticulo.removeAllItems();
        dropModel.addElement("<Seleccionar Articulo>");
        dropModel.addElement("Nuevo Articulo");
    }

    public static void vaciarInputTextArticulo(FacturaVistaCompra viewFacturasDetallesCompra) {
        viewFacturasDetallesCompra.dropdownArticulo.setSelectedItem("<Seleccionar Articulo>");
        viewFacturasDetallesCompra.dropdownRubro.setSelectedItem("<Seleccionar rubro>");
        viewFacturasDetallesCompra.inputTextNombre.setText("");
        viewFacturasDetallesCompra.inputTextPrecio.setText("");
        viewFacturasDetallesCompra.spinnerCantidad.setValue(1);
        viewFacturasDetallesCompra.inputTextDescripcion.setText("");
        viewFacturasDetallesCompra.inputTextStockMinimo.setText("");
    }

    public static void agregarArticulosDropdown(FacturaVistaCompra viewFacturasDetallesCompra) {
        vaciarInputTextArticulo(viewFacturasDetallesCompra);
        if (viewFacturasDetallesCompra.dropdownProveedor.getItemCount() != 0) {
            if (!viewFacturasDetallesCompra.dropdownProveedor.getSelectedItem().equals("<Seleccionar Proveedor>")) {
                DefaultComboBoxModel dropModel = (DefaultComboBoxModel) viewFacturasDetallesCompra.dropdownArticulo.getModel();

                viewFacturasDetallesCompra.dropdownArticulo.removeAllItems();
                dropModel.addElement("<Seleccionar Articulo>");
                dropModel.addElement("Nuevo Articulo");
                int idProveedor = Integer.parseInt(viewFacturasDetallesCompra.dropdownProveedor.getSelectedItem().toString().split("-")[0]);
                ArrayList<Articulo> articulos = ArticuloConsultas.getAllArticulosIdProveedor(idProveedor);

                articulos.forEach(articulo -> {
                    dropModel.addElement(articulo.getId() + "- " + articulo.getNombre());
                });
            }
        }
    }

    public static void iniciarDropdownRubros(FacturaVistaCompra viewFacturasDetallesCompra) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) viewFacturasDetallesCompra.dropdownRubro.getModel();
        ArrayList<Rubro> rubros = RubroConsultas.getAllRubros();

        viewFacturasDetallesCompra.dropdownRubro.removeAllItems();
        dropModel.addElement("<Seleccionar rubro>");
        rubros.forEach(rubro -> {
            dropModel.addElement(rubro.getId() + "- " + rubro.getNombre());
        });
    }

    public static void noNegativosSpinner(FacturaVistaCompra viewFacturasDetallesCompra) {
        if ((Integer) viewFacturasDetallesCompra.spinnerCantidad.getValue() < 1) {
            viewFacturasDetallesCompra.spinnerCantidad.setValue(1);
        }
    }

    public static void cargarInputTexts(FacturaVistaCompra viewFacturasDetallesCompra) {
        viewFacturasDetallesCompra.dropdownArticulo.setSelectedItem(viewFacturasDetallesCompra.tabla.getValueAt(viewFacturasDetallesCompra.tabla.getSelectedRow(), 0).toString()
                + "- " + viewFacturasDetallesCompra.tabla.getValueAt(viewFacturasDetallesCompra.tabla.getSelectedRow(), 1).toString());
        viewFacturasDetallesCompra.inputTextNombre.setText(viewFacturasDetallesCompra.tabla.getValueAt(viewFacturasDetallesCompra.tabla.getSelectedRow(), 1).toString());
        viewFacturasDetallesCompra.inputTextDescripcion.setText(viewFacturasDetallesCompra.tabla.getValueAt(viewFacturasDetallesCompra.tabla.getSelectedRow(), 2).toString());
        viewFacturasDetallesCompra.dropdownRubro.setSelectedItem(viewFacturasDetallesCompra.tabla.getValueAt(viewFacturasDetallesCompra.tabla.getSelectedRow(), 3).toString());
        viewFacturasDetallesCompra.inputTextStockMinimo.setText(viewFacturasDetallesCompra.tabla.getValueAt(viewFacturasDetallesCompra.tabla.getSelectedRow(), 4).toString());
        viewFacturasDetallesCompra.inputTextPrecio.setText(viewFacturasDetallesCompra.tabla.getValueAt(viewFacturasDetallesCompra.tabla.getSelectedRow(), 5).toString());
        viewFacturasDetallesCompra.spinnerCantidad.setValue(Integer.parseInt(viewFacturasDetallesCompra.tabla.getValueAt(viewFacturasDetallesCompra.tabla.getSelectedRow(), 6).toString()));

    }

    public static void vaciarInputTexts(FacturaVistaCompra viewFacturasDetallesCompra) {
        viewFacturasDetallesCompra.inputTextNumero.setText("");
        viewFacturasDetallesCompra.inputTextFecha.setCalendar(null);
        viewFacturasDetallesCompra.dropdownProveedor.setSelectedItem("<Seleccionar Proveedor>");
        viewFacturasDetallesCompra.dropdownArticulo.setSelectedItem("<Seleccionar Articulo>");
        viewFacturasDetallesCompra.dropdownRubro.setSelectedItem("<Seleccionar rubro>");
        viewFacturasDetallesCompra.inputTextNombre.setText("");
        viewFacturasDetallesCompra.inputTextPrecio.setText("");
        viewFacturasDetallesCompra.spinnerCantidad.setValue(1);
        viewFacturasDetallesCompra.inputTextDescripcion.setText("");
        viewFacturasDetallesCompra.inputTextStockMinimo.setText("");
        viewFacturasDetallesCompra.inputTextTotal.setText("");
    }

    public static void seleccionarArticuloCargarInputsText(FacturaVistaCompra viewFacturasDetallesCompra) {
        if (viewFacturasDetallesCompra.dropdownArticulo.getSelectedItem().equals("Nuevo Articulo")) {
            viewFacturasDetallesCompra.inputTextNombre.setText("");
            viewFacturasDetallesCompra.dropdownRubro.setSelectedItem("<Seleccionar rubro>");
            viewFacturasDetallesCompra.inputTextPrecio.setText("");
            viewFacturasDetallesCompra.inputTextDescripcion.setText("");
            viewFacturasDetallesCompra.inputTextStockMinimo.setText("");
            viewFacturasDetallesCompra.spinnerCantidad.setValue(1);
        } else if (!viewFacturasDetallesCompra.dropdownArticulo.getSelectedItem().equals("<Seleccionar Articulo>")) {
            int idArticulo = Integer.parseInt(viewFacturasDetallesCompra.dropdownArticulo.getSelectedItem().toString().split("-")[0]);
            Articulo articulo = ArticuloConsultas.getArticulo(idArticulo);
            viewFacturasDetallesCompra.inputTextNombre.setText(articulo.getNombre());
            viewFacturasDetallesCompra.dropdownRubro.setSelectedItem(articulo.getRubro().getId() + "- " + articulo.getRubro().getNombre());
            viewFacturasDetallesCompra.inputTextPrecio.setText(Float.toString(articulo.getPrecioUnitario()));
            viewFacturasDetallesCompra.inputTextDescripcion.setText(articulo.getDescripcion());
            viewFacturasDetallesCompra.inputTextStockMinimo.setText(Integer.toString(articulo.getStockMinimo()));
            viewFacturasDetallesCompra.spinnerCantidad.setValue(1);
        }
    }

    public static void seleccionarArticulo(FacturaVistaCompra viewFacturasDetallesCompra) {
        if (viewFacturasDetallesCompra.dropdownArticulo.getItemCount() != 0) {
            if (viewFacturasDetallesCompra.dropdownArticulo.getSelectedItem().equals("Nuevo Articulo")) {
                viewFacturasDetallesCompra.dropdownRubro.enable();
                viewFacturasDetallesCompra.inputTextNombre.setEditable(true);
                viewFacturasDetallesCompra.inputTextPrecio.setEditable(true);
                viewFacturasDetallesCompra.inputTextDescripcion.setEditable(true);
                viewFacturasDetallesCompra.inputTextStockMinimo.setEditable(true);
                seleccionarArticuloCargarInputsText(viewFacturasDetallesCompra);
            } else {
                viewFacturasDetallesCompra.dropdownRubro.disable();
                viewFacturasDetallesCompra.inputTextNombre.setEditable(false);
                viewFacturasDetallesCompra.inputTextPrecio.setEditable(false);
                viewFacturasDetallesCompra.inputTextDescripcion.setEditable(false);
                viewFacturasDetallesCompra.inputTextStockMinimo.setEditable(false);
                seleccionarArticuloCargarInputsText(viewFacturasDetallesCompra);
            }
        }

    }

    public static boolean inputsTextInvalidos(FacturaVistaCompra viewFacturasDetallesCompra) {
        if (viewFacturasDetallesCompra.dropdownArticulo.getSelectedItem().equals("<Seleccionar Articulo>")
                || viewFacturasDetallesCompra.dropdownRubro.getSelectedItem().equals("<Seleccionar rubro>")
                || viewFacturasDetallesCompra.dropdownProveedor.getSelectedItem().equals("<Seleccionar Proveedor>")
                || viewFacturasDetallesCompra.inputTextNombre.getText().equals("")
                || viewFacturasDetallesCompra.inputTextDescripcion.getText().equals("")
                || viewFacturasDetallesCompra.inputTextPrecio.getText().equals("")
                || viewFacturasDetallesCompra.inputTextStockMinimo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
            return true;
        } else {
            try {
                int stock = (Integer) viewFacturasDetallesCompra.spinnerCantidad.getValue();
                int stock_min = Integer.parseInt(viewFacturasDetallesCompra.inputTextStockMinimo.getText());
                Double.parseDouble(viewFacturasDetallesCompra.inputTextPrecio.getText());
                // Excepcion.comprobarStocks(stock_min, stock);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese solo un valor numerico entero para el stock minimo y real para el precio.");
                return true;
            } /*catch (Excepcion e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return true;
            }*/
            return false;
        }
    }

    public static void agregarArticulo(FacturaVistaCompra viewFacturasDetallesCompra) {
        if (inputsTextInvalidos(viewFacturasDetallesCompra)) {
            System.out.println("Error al agregar articulo");
        } else {
            try {
                DefaultTableModel tableModel = (DefaultTableModel) viewFacturasDetallesCompra.tabla.getModel();
                float subtotal = (Integer) viewFacturasDetallesCompra.spinnerCantidad.getValue() * Float.parseFloat(viewFacturasDetallesCompra.inputTextPrecio.getText());

                String[] data = new String[8];
                if (viewFacturasDetallesCompra.dropdownArticulo.getSelectedItem().equals("Nuevo Articulo")) {
                    data[0] = "NUEVO";
                } else {
                    data[0] = viewFacturasDetallesCompra.dropdownArticulo.getSelectedItem().toString().split("-")[0];
                }
                data[1] = viewFacturasDetallesCompra.inputTextNombre.getText();
                data[2] = viewFacturasDetallesCompra.inputTextDescripcion.getText();
                data[3] = viewFacturasDetallesCompra.dropdownRubro.getSelectedItem().toString();
                data[4] = viewFacturasDetallesCompra.inputTextStockMinimo.getText();
                data[5] = viewFacturasDetallesCompra.inputTextPrecio.getText();
                data[6] = viewFacturasDetallesCompra.spinnerCantidad.getValue().toString();
                data[7] = Float.toString(subtotal);
                tableModel.addRow(data);

                float total;
                if (viewFacturasDetallesCompra.inputTextTotal.getText().equals("")) {
                    total = subtotal;
                } else {
                    total = Float.parseFloat(viewFacturasDetallesCompra.inputTextTotal.getText()) + subtotal;
                }
                viewFacturasDetallesCompra.inputTextTotal.setText(Float.toString(total));
                viewFacturasDetallesCompra.dropdownProveedor.disable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error de calculo");
            } catch (Exception e) {
                // TODO: MANEJO VALIDACION
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Error inesperado");
            }
        }
    }

    public static void quitarArticulo(FacturaVistaCompra viewFacturasDetallesCompra) {
        if (viewFacturasDetallesCompra.tabla.getSelectedRow() != -1) {
            DefaultTableModel tablaModel = (DefaultTableModel) viewFacturasDetallesCompra.tabla.getModel();
            float subtotal = Float.parseFloat(viewFacturasDetallesCompra.tabla.getValueAt(viewFacturasDetallesCompra.tabla.getSelectedRow(), 7).toString());
            float total = Float.parseFloat(viewFacturasDetallesCompra.inputTextTotal.getText()) - subtotal;
            viewFacturasDetallesCompra.inputTextTotal.setText(Float.toString(total));

            tablaModel.removeRow(viewFacturasDetallesCompra.tabla.getSelectedRow());
            if (viewFacturasDetallesCompra.tabla.getRowCount() == 0) {
                viewFacturasDetallesCompra.dropdownProveedor.enable();
            }
        }else{
            JOptionPane.showMessageDialog(viewFacturasDetallesCompra, "Seleccionar un articulo para eliminar");
        }
    }

    public static boolean facturaInvalida(FacturaVistaCompra viewFacturasDetallesCompra) {
        if (viewFacturasDetallesCompra.tabla.getRowCount() == 0) {
            return true;
        }
        if (viewFacturasDetallesCompra.dropdownProveedor.getSelectedItem().equals("<Seleccionar Proveedor>")) {
            return true;
        }
        if (viewFacturasDetallesCompra.inputTextNumero.getText().equals("")) {
            return true;
        }
        if (viewFacturasDetallesCompra.inputTextFecha.getDate() == null) {
            return true;
        }
        return false;
    }

    public static ArrayList<Linea> generarLineas(FacturaVistaCompra viewFacturasDetallesCompra, Factura factura, Proveedor proveedor) {
        ArrayList<Linea> lineas = new ArrayList<>();
        Articulo articulo;
        for (int i = 0; i <= viewFacturasDetallesCompra.tabla.getRowCount() - 1; i++) {
            // guardar nuevo articulo
            if (viewFacturasDetallesCompra.tabla.getValueAt(i, 0).toString().equals("NUEVO")) {
                Rubro rubro = RubroConsultas.getRubro(Integer.parseInt(viewFacturasDetallesCompra.tabla.getValueAt(i, 3).toString().split("-")[0]));
                String nombre = viewFacturasDetallesCompra.tabla.getValueAt(i, 1).toString();
                String descripcion = viewFacturasDetallesCompra.tabla.getValueAt(i, 2).toString();
                float precioUnitario = Float.parseFloat(viewFacturasDetallesCompra.tabla.getValueAt(i, 5).toString());
                int stockActual = Integer.parseInt(viewFacturasDetallesCompra.tabla.getValueAt(i, 6).toString());
                int stockMinimo = Integer.parseInt(viewFacturasDetallesCompra.tabla.getValueAt(i, 4).toString());

                articulo = new Articulo(proveedor, rubro, nombre, descripcion, precioUnitario, stockActual, stockMinimo);
                ArticuloConsultas.saveArticulo(articulo);
            } else {
                // actualizar stock articulo
                int idArticulo = Integer.parseInt(viewFacturasDetallesCompra.tabla.getValueAt(i, 0).toString());
                articulo = ArticuloConsultas.getArticulo(idArticulo);

                articulo.sumarStock(Integer.parseInt(viewFacturasDetallesCompra.tabla.getValueAt(i, 6).toString()));
                ArticuloConsultas.updateArticulo(articulo, idArticulo);
            }

            float precioUnitario = Float.parseFloat(viewFacturasDetallesCompra.tabla.getValueAt(i, 5).toString());
            int cantidad = Integer.parseInt(viewFacturasDetallesCompra.tabla.getValueAt(i, 6).toString());
            float subtotal = Float.parseFloat(viewFacturasDetallesCompra.tabla.getValueAt(i, 7).toString());

            Linea nuevaLinea = new Linea(articulo, factura, precioUnitario, cantidad, subtotal);
            lineas.add(nuevaLinea);
        }
        return lineas;
    }

    public static void guardarFactura(Index view, FacturaVistaCompra viewFacturasDetallesCompra) {
        if (viewFacturasDetallesCompra.getTitle().equals("Detalles Factura")) {
            vaciarInputTexts(viewFacturasDetallesCompra);
            viewFacturasDetallesCompra.setVisible(false);
        } else {
            if (facturaInvalida(viewFacturasDetallesCompra)) {
                JOptionPane.showMessageDialog(null, "Error intentelo nuevamente");
            } else {
                int idProveedor = Integer.parseInt(viewFacturasDetallesCompra.dropdownProveedor.getSelectedItem().toString().split("-")[0]);

                Proveedor proveedor = ProveedorConsultas.getProveedor(idProveedor);
                String numeroFactura = viewFacturasDetallesCompra.inputTextNumero.getText();
                Date fecha = viewFacturasDetallesCompra.inputTextFecha.getDate();
                float total = Float.parseFloat(viewFacturasDetallesCompra.inputTextTotal.getText());

                Factura factura = new Factura(proveedor, 'C', numeroFactura, fecha, total);

                ArrayList<Linea> lineas = generarLineas(viewFacturasDetallesCompra, factura, proveedor);
                factura.setLineas(lineas);

                FacturaConsultas.saveFactura(factura);

                vaciarInputTexts(viewFacturasDetallesCompra);
                viewFacturasDetallesCompra.setVisible(false);
                FacturaControlador.iniciarTabla(view);
            }
        }
    }

    public static void activarInteraccionVista(FacturaVistaCompra viewFacturasDetallesCompra) {
        viewFacturasDetallesCompra.inputTextNumero.setEnabled(true);
        viewFacturasDetallesCompra.inputTextFecha.setEnabled(true);
        viewFacturasDetallesCompra.dropdownArticulo.setEnabled(true);
        viewFacturasDetallesCompra.dropdownProveedor.setEnabled(true);
        viewFacturasDetallesCompra.inputTextNombre.setEnabled(true);
        viewFacturasDetallesCompra.inputTextPrecio.setEditable(true);
        viewFacturasDetallesCompra.spinnerCantidad.setEnabled(true);
        viewFacturasDetallesCompra.inputTextDescripcion.setEditable(true);
        viewFacturasDetallesCompra.inputTextStockMinimo.setEditable(true);
        viewFacturasDetallesCompra.btnAgregarProducto.setVisible(true);
        viewFacturasDetallesCompra.btnBorrarProducto.setVisible(true);
    }

    public static void abrirVistaFacturaCompra(FacturaVistaCompra viewFacturasDetallesCompra) {
        viewFacturasDetallesCompra.setTitle("Nueva factura de compra");
        viewFacturasDetallesCompra.setLocationRelativeTo(null);
        viewFacturasDetallesCompra.setVisible(true);

        activarInteraccionVista(viewFacturasDetallesCompra);

        ((JSpinner.DefaultEditor) viewFacturasDetallesCompra.spinnerCantidad.getEditor()).getTextField().setEditable(false);
        DefaultTableModel tableModel = (DefaultTableModel) viewFacturasDetallesCompra.tabla.getModel();
        tableModel.setNumRows(0);
        vaciarInputTexts(viewFacturasDetallesCompra);

        inicializarDropdownProveedores(viewFacturasDetallesCompra);
        inicializarDropdownArticulos(viewFacturasDetallesCompra);
        iniciarDropdownRubros(viewFacturasDetallesCompra);
    }

    public static void cargarTablaVerMas(FacturaVistaCompra viewFacturasDetallesCompra, List<Linea> lineas) {
        DefaultTableModel tableModel = (DefaultTableModel) viewFacturasDetallesCompra.tabla.getModel();
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

    public static void desactivarInteraccionVista(FacturaVistaCompra viewFacturasDetallesCompra) {
        viewFacturasDetallesCompra.inputTextNumero.setEnabled(false);
        viewFacturasDetallesCompra.inputTextFecha.setEnabled(false);
        viewFacturasDetallesCompra.dropdownArticulo.setEnabled(false);
        viewFacturasDetallesCompra.dropdownProveedor.setEnabled(false);
        viewFacturasDetallesCompra.inputTextNombre.setEnabled(false);
        viewFacturasDetallesCompra.inputTextPrecio.setEditable(false);
        viewFacturasDetallesCompra.spinnerCantidad.setEnabled(false);
        viewFacturasDetallesCompra.inputTextDescripcion.setEditable(false);
        viewFacturasDetallesCompra.inputTextStockMinimo.setEditable(false);
        viewFacturasDetallesCompra.btnAgregarProducto.setVisible(false); // setEnabled(false);
        viewFacturasDetallesCompra.btnBorrarProducto.setVisible(false); // setEnabled(false);
    }

    public static void abrirVistaFacturaVenta(FacturaVistaCompra viewFacturasDetallesCompra, Factura factura) {
        viewFacturasDetallesCompra.setTitle("Detalles Factura");
        viewFacturasDetallesCompra.setLocationRelativeTo(null);
        viewFacturasDetallesCompra.setVisible(true);

        inicializarDropdownProveedores(viewFacturasDetallesCompra);
        inicializarDropdownArticulos(viewFacturasDetallesCompra);
        iniciarDropdownRubros(viewFacturasDetallesCompra);

        desactivarInteraccionVista(viewFacturasDetallesCompra);

        viewFacturasDetallesCompra.inputTextNumero.setText(factura.getNumeroFactura());
        viewFacturasDetallesCompra.inputTextFecha.setDate(factura.getFecha());
        viewFacturasDetallesCompra.dropdownProveedor.setSelectedItem(factura.getProveedor().getId() + "- " + factura.getProveedor().getNombre());
        cargarTablaVerMas(viewFacturasDetallesCompra, factura.getLineas());
        viewFacturasDetallesCompra.inputTextTotal.setText(Float.toString(factura.getTotal()));
    }

}

package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Factura;
import modelo.Linea;
import modelo.services.ArticuloConsultas;
import modelo.services.ClienteConsultas;
import modelo.services.FacturaConsultas;
import vista.FacturaVistaVenta;
import vista.Index;

public class FacturaVentaControlador {

    public static void inicializarDropdownClientes(FacturaVistaVenta facturaDetallesVenta) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesVenta.dropdownCliente.getModel();
        ArrayList<Cliente> clientes = ClienteConsultas.getAllClientes();

        facturaDetallesVenta.dropdownCliente.removeAllItems();
        dropModel.addElement("<Seleccionar cliente>");
        clientes.forEach(cliente -> {
            dropModel.addElement(cliente.getId() + "- " + cliente.getNombre());
        });
    }

    public static void inicializarDropdownArticulos(FacturaVistaVenta facturaDetallesVenta) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesVenta.dropdownArticulo.getModel();
        ArrayList<Articulo> articulos = ArticuloConsultas.getAllArticulos();

        facturaDetallesVenta.dropdownArticulo.removeAllItems();
        dropModel.addElement("<Seleccionar Articulo>");
        articulos.forEach(articulo -> {
            dropModel.addElement(articulo.getId() + "- " + articulo.getNombre());
        });
    }

    public static void seleccionarArticulo(JComboBox dropdownArticulo, JTextField inputTextPrecio, JSpinner spinnerCantidad) {
        if (dropdownArticulo.getItemCount() != 0) {
            if (!dropdownArticulo.getSelectedItem().equals("<Seleccionar Articulo>")) {
                int idArticulo = Integer.parseInt(dropdownArticulo.getSelectedItem().toString().split("-")[0]);
                Articulo articulo = ArticuloConsultas.getArticulo(idArticulo);

                inputTextPrecio.setText(Float.toString(articulo.getPrecioUnitario()));
                spinnerCantidad.setValue(1);
            }
        }
    }

    public static void noNegativosSpinner(JSpinner spinner) {
        if ((Integer) spinner.getValue() < 1) {
            spinner.setValue(1);
        }
    }

    public static void cargarInputTexts(FacturaVistaVenta facturaDetallesVenta, String articulo, String precio, String cantidad) {
        facturaDetallesVenta.dropdownArticulo.setSelectedItem(articulo);
        facturaDetallesVenta.inputTextPrecio.setText(precio);
        facturaDetallesVenta.spinnerCantidad.setValue(Integer.parseInt(cantidad));
    }

    public static void vaciarInputTexts(FacturaVistaVenta facturaDetallesVenta) {
        facturaDetallesVenta.dropdownCliente.setSelectedItem("<Seleccionar cliente>");
        facturaDetallesVenta.dropdownArticulo.setSelectedItem("<Seleccionar Articulo>");
        facturaDetallesVenta.inputTextPrecio.setText("");
        facturaDetallesVenta.spinnerCantidad.setValue(0);
        facturaDetallesVenta.inputTextTotal.setText("");
    }

    public static void agregarArticulo(FacturaVistaVenta facturaDetallesVenta) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) facturaDetallesVenta.tabla.getModel();
            float subtotal = (Integer) facturaDetallesVenta.spinnerCantidad.getValue() * Float.parseFloat(facturaDetallesVenta.inputTextPrecio.getText());

            String[] data = new String[5];
            data[0] = facturaDetallesVenta.dropdownArticulo.getSelectedItem().toString().split("-")[0];
            data[1] = facturaDetallesVenta.dropdownArticulo.getSelectedItem().toString();
            data[2] = facturaDetallesVenta.inputTextPrecio.getText();
            data[3] = facturaDetallesVenta.spinnerCantidad.getValue().toString();
            data[4] = Float.toString(subtotal);
            tableModel.addRow(data);

            float total;
            if (facturaDetallesVenta.inputTextTotal.getText().equals("")) {
                total = subtotal;
            } else {
                total = Float.parseFloat(facturaDetallesVenta.inputTextTotal.getText()) + subtotal;
            }
            facturaDetallesVenta.inputTextTotal.setText(Float.toString(total));

            facturaDetallesVenta.dropdownCliente.disable();
        } catch (Exception e) {
            // TODO: MANEJO VALIDACION
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error inesperado");
        }
    }

    public static void quitarArticulo(FacturaVistaVenta facturaDetallesVenta) {
        DefaultTableModel tablaModel = (DefaultTableModel) facturaDetallesVenta.tabla.getModel();
        float subtotal = Float.parseFloat(facturaDetallesVenta.tabla.getValueAt(facturaDetallesVenta.tabla.getSelectedRow(), 4).toString());
        float total = Float.parseFloat(facturaDetallesVenta.inputTextTotal.getText()) - subtotal;
        facturaDetallesVenta.inputTextTotal.setText(Float.toString(total));

        tablaModel.removeRow(facturaDetallesVenta.tabla.getSelectedRow());
        if (facturaDetallesVenta.tabla.getRowCount() == 0) {
            facturaDetallesVenta.dropdownCliente.enable();
        }
    }

    public static boolean facturaInvalida(FacturaVistaVenta facturaDetallesVenta) {
        if (facturaDetallesVenta.tabla.getRowCount() == 0) {
            return true;
        }
        if (facturaDetallesVenta.dropdownCliente.getSelectedItem().equals("<Seleccionar cliente>")) {
            return true;
        }
        return false;
    }

    public static ArrayList<Linea> generarLineas(JTable tabla, Factura factura) {
        ArrayList<Linea> lineas = new ArrayList<>();

        for (int i = 0; i <= tabla.getRowCount() - 1; i++) {

            int idArticulo = Integer.parseInt(tabla.getValueAt(i, 0).toString());
            Articulo articulo = ArticuloConsultas.getArticulo(idArticulo);
            float precioUnitario = Float.parseFloat(tabla.getValueAt(i, 2).toString());
            int cantidad = Integer.parseInt(tabla.getValueAt(i, 3).toString());
            float subtotal = Float.parseFloat(tabla.getValueAt(i, 4).toString());

            articulo.restarStock(cantidad);
            ArticuloConsultas.updateArticulo(articulo, idArticulo);

            Linea nuevaLinea = new Linea(articulo, factura, precioUnitario, cantidad, subtotal);
            lineas.add(nuevaLinea);

        }
        return lineas;
    }

    public static void guardarFactura(Index view, FacturaVistaVenta facturaDetallesVenta) {
        if (facturaDetallesVenta.getTitle().equals("Detalles Factura")) {
            vaciarInputTexts(facturaDetallesVenta);
            facturaDetallesVenta.setVisible(false);
        } else {
            if (facturaInvalida(facturaDetallesVenta)) {
                JOptionPane.showMessageDialog(null, "Error intenttelo nuevamente");
            } else {
                int idCliente = Integer.parseInt(facturaDetallesVenta.dropdownCliente.getSelectedItem().toString().split("-")[0]);

                Cliente cliente = ClienteConsultas.getCliente(idCliente);
                String numeroFactura = facturaDetallesVenta.inputTextNumero.getText();
                Date fecha = new Date();
                float total = Float.parseFloat(facturaDetallesVenta.inputTextTotal.getText());

                Factura factura = new Factura(cliente, 'V', numeroFactura, fecha, total);

                ArrayList<Linea> lineas = generarLineas(facturaDetallesVenta.tabla, factura);
                factura.setLineas(lineas);

                FacturaConsultas.saveFactura(factura);

                vaciarInputTexts(facturaDetallesVenta);
                facturaDetallesVenta.setVisible(false);
                FacturaControlador.iniciarTabla(view.factTabla, view.factCheckBoxCompra, view.factCheckBoxVenta);
            }
        }

    }

    public static void activarInteraccionVista(FacturaVistaVenta facturaDetallesVenta) {
        facturaDetallesVenta.spinnerCantidad.setEnabled(true);
        facturaDetallesVenta.btnAgregarProducto.setVisible(true);
        facturaDetallesVenta.btnBorrarProducto.setVisible(true);
        facturaDetallesVenta.dropdownArticulo.setEnabled(true);
        facturaDetallesVenta.dropdownCliente.setEnabled(true);
    }

    public static void imprimirFactura(FacturaVistaVenta facturaDetallesVenta) {
        JOptionPane.showMessageDialog(
                facturaDetallesVenta,
                "Imprimiendo factura numero:" + facturaDetallesVenta.inputTextNumero.getText());
    }

    public static void abrirVistaFacturaVenta(FacturaVistaVenta facturaDetallesVenta) {
        facturaDetallesVenta.setTitle("Nueva factura de venta");
        facturaDetallesVenta.setLocationRelativeTo(null);
        facturaDetallesVenta.setVisible(true);

        facturaDetallesVenta.inputTextNumero.setText(Integer.toString(FacturaConsultas.getLastNumeroFactura() + 1));
        activarInteraccionVista(facturaDetallesVenta);


        DefaultTableModel tableModel = (DefaultTableModel) facturaDetallesVenta.tabla.getModel();
        tableModel.setNumRows(0);
        vaciarInputTexts(facturaDetallesVenta);

        inicializarDropdownClientes(facturaDetallesVenta);
        inicializarDropdownArticulos(facturaDetallesVenta);
    }

    public static void cargarTablaVerMas(FacturaVistaVenta facturaDetallesVenta, List<Linea> lineas) {
        DefaultTableModel tableModel = (DefaultTableModel) facturaDetallesVenta.tabla.getModel();
        tableModel.setNumRows(0);
        lineas.forEach((Linea linea) -> {
            String[] data = new String[5];
            data[1] = linea.getArticulo().getId() + "- " + linea.getArticulo().getNombre();
            data[2] = Float.toString(linea.getPrecioUnitario());
            data[3] = Integer.toString(linea.getCantidad());
            data[4] = Float.toString(linea.getSubTotal());
            tableModel.addRow(data);
        });
    }

    public static void desactivarInteraccionVista(FacturaVistaVenta facturaDetallesVenta) {
        facturaDetallesVenta.spinnerCantidad.setEnabled(false);
        facturaDetallesVenta.btnAgregarProducto.setVisible(false); // setEnabled(false);
        facturaDetallesVenta.btnBorrarProducto.setVisible(false); // setEnabled(false);
        facturaDetallesVenta.dropdownArticulo.setEnabled(false);
        facturaDetallesVenta.dropdownCliente.setEnabled(false);
    }

    public static void abrirVistaFacturaVenta(FacturaVistaVenta facturaDetallesVenta, Factura factura) {
        facturaDetallesVenta.setTitle("Detalles Factura");
        facturaDetallesVenta.setLocationRelativeTo(null);
        facturaDetallesVenta.setVisible(true);

        inicializarDropdownClientes(facturaDetallesVenta);
        inicializarDropdownArticulos(facturaDetallesVenta);

        desactivarInteraccionVista(facturaDetallesVenta);

        facturaDetallesVenta.inputTextNumero.setText(factura.getNumeroFactura());
        facturaDetallesVenta.dropdownCliente.setSelectedItem(factura.getCliente().getId() + "- " + factura.getCliente().getNombre());
        cargarTablaVerMas(facturaDetallesVenta, factura.getLineas());
        facturaDetallesVenta.inputTextTotal.setText(Float.toString(factura.getTotal()));
    }
}

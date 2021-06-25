package controlador;

import static controlador.FacturaControlador.generarLineas;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
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

public class FacturaVentaControlador extends FacturaControlador {

    public static void inicializarDropdownClientes(FacturaVistaVenta facturaDetallesVenta, ClienteConsultas servicesCli) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesVenta.dropdownCliente.getModel();
        ArrayList<Cliente> clientes = servicesCli.getAllClientes();

        dropModel.addElement("<Seleccionar cliente>");
        clientes.forEach(cliente -> {
            dropModel.addElement(cliente.getId() + "- " + cliente.getNombre());
        });
    }

    public static void inicializarDropdownArticulos(FacturaVistaVenta facturaDetallesVenta, ArticuloConsultas servicesArt) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesVenta.dropdownArticulo.getModel();
        ArrayList<Articulo> articulos = servicesArt.getAllArticulos();

        dropModel.addElement("<Seleccionar Articulo>");
        articulos.forEach(articulo -> {
            dropModel.addElement(articulo.getId() + "- " + articulo.getNombre());
        });
    }

    public static void cargarInputTexts(JComboBox dropdownArticulo, JTextField inputTextPrecio, JSpinner spinnerCantidad, ArticuloConsultas servicesArt) {
        if (!dropdownArticulo.getSelectedItem().equals("<Seleccionar Articulo>")) {
            int idArticulo = Integer.parseInt(dropdownArticulo.getSelectedItem().toString().split("-")[0]);
            Articulo articulo = servicesArt.getArticulo(idArticulo);

            inputTextPrecio.setText(Float.toString(articulo.getPrecioUnitario()));
            if ((Integer) spinnerCantidad.getValue() == 0) {
                spinnerCantidad.setValue(1);
            }
        }
    }

    public static void cargarInputTexts(FacturaVistaVenta facturaDetallesVenta, String cliente, String articulo, String precio, String cantidad) {
        facturaDetallesVenta.dropdownCliente.setSelectedItem(cliente);
        facturaDetallesVenta.dropdownArticulo.setSelectedItem(articulo);
        facturaDetallesVenta.inputTextPrecio.setText(precio);
        facturaDetallesVenta.spinnerCantidad.setValue(Integer.parseInt(cantidad));
    }

    public static void vaciarInputTexts(FacturaVistaVenta facturaDetallesVenta) {
        facturaDetallesVenta.dropdownCliente.setSelectedItem("<Seleccionar cliente>");
        facturaDetallesVenta.dropdownArticulo.setSelectedItem("<Seleccionar Articulo>");
        facturaDetallesVenta.inputTextPrecio.setText("");
        facturaDetallesVenta.spinnerCantidad.setValue(0);
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

    public static void guardarFactura(FacturaVistaVenta facturaDetallesVenta, ArticuloConsultas servicesArt, ClienteConsultas servicesClie, FacturaConsultas servicesFact) {
        if (facturaInvalida(facturaDetallesVenta)) {
            JOptionPane.showMessageDialog(null, "Error intenttelo nuevamente");
        } else {
            int idCliente = Integer.parseInt(facturaDetallesVenta.dropdownCliente.getSelectedItem().toString().split("-")[0]);

            Cliente cliente = servicesClie.getCliente(idCliente);
            String numeroFactura = facturaDetallesVenta.inputTextNumero.getText();
            Date fecha = new Date();
            float total = Float.parseFloat(facturaDetallesVenta.inputTextTotal.getText());

            Factura factura = new Factura(cliente, 'V', numeroFactura, fecha, total);

            ArrayList<Linea> lineas = generarLineas(facturaDetallesVenta.tabla, servicesArt, factura);
            factura.setLineas(lineas);

            servicesFact.saveFactura(factura);

            vaciarInputTexts(facturaDetallesVenta);
            facturaDetallesVenta.setVisible(false);
        }
    }

    public static void abrirVistaFacturaVenta(FacturaVistaVenta facturaDetallesVenta, ArticuloConsultas servicesArt, ClienteConsultas servicesClie, FacturaConsultas servicesFact) {
        facturaDetallesVenta.setTitle("Nueva factura de venta");
        facturaDetallesVenta.setLocationRelativeTo(null);
        facturaDetallesVenta.setVisible(true);
        facturaDetallesVenta.inputTextNumero.setText(Integer.toString(servicesFact.getLastNuemroFactura() + 1));

        DefaultTableModel tableModel = (DefaultTableModel) facturaDetallesVenta.tabla.getModel();
        tableModel.setNumRows(0);
        vaciarInputTexts(facturaDetallesVenta);

        inicializarDropdownClientes(facturaDetallesVenta, servicesClie);
        inicializarDropdownArticulos(facturaDetallesVenta, servicesArt);
    }
}

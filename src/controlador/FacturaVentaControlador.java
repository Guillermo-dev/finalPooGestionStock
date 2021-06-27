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

public class FacturaVentaControlador {

    public static void inicializarDropdownClientes(FacturaVistaVenta facturaDetallesVenta, ClienteConsultas servicesCli) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesVenta.dropdownCliente.getModel();
        ArrayList<Cliente> clientes = servicesCli.getAllClientes();

        facturaDetallesVenta.dropdownCliente.removeAllItems();
        dropModel.addElement("<Seleccionar cliente>");
        clientes.forEach(cliente -> {
            dropModel.addElement(cliente.getId() + "- " + cliente.getNombre());
        });
    }

    public static void inicializarDropdownArticulos(FacturaVistaVenta facturaDetallesVenta, ArticuloConsultas servicesArt) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) facturaDetallesVenta.dropdownArticulo.getModel();
        ArrayList<Articulo> articulos = servicesArt.getAllArticulos();

        facturaDetallesVenta.dropdownArticulo.removeAllItems();
        dropModel.addElement("<Seleccionar Articulo>");
        articulos.forEach(articulo -> {
            dropModel.addElement(articulo.getId() + "- " + articulo.getNombre());
        });
    }

    public static void seleccionarArticulo(JComboBox dropdownArticulo, JTextField inputTextPrecio, JSpinner spinnerCantidad, ArticuloConsultas servicesArt) {
        if (dropdownArticulo.getItemCount() != 0) {
            if (!dropdownArticulo.getSelectedItem().equals("<Seleccionar Articulo>")) {
                int idArticulo = Integer.parseInt(dropdownArticulo.getSelectedItem().toString().split("-")[0]);
                Articulo articulo = servicesArt.getArticulo(idArticulo);

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

    public static ArrayList<Linea> generarLineas(JTable tabla, ArticuloConsultas servicesArt, Factura factura) {
        ArrayList<Linea> lineas = new ArrayList<>();

        for (int i = 0; i <= tabla.getRowCount() - 1; i++) {

            int idArticulo = Integer.parseInt(tabla.getValueAt(i, 0).toString());
            Articulo articulo = servicesArt.getArticulo(idArticulo);
            float precioUnitario = Float.parseFloat(tabla.getValueAt(i, 2).toString());
            int cantidad = Integer.parseInt(tabla.getValueAt(i, 3).toString());
            float subtotal = Float.parseFloat(tabla.getValueAt(i, 4).toString());

            articulo.restarStock(cantidad);
            servicesArt.updateArticulo(articulo, idArticulo);

            Linea nuevaLinea = new Linea(articulo, factura, precioUnitario, cantidad, subtotal);
            lineas.add(nuevaLinea);

        }
        return lineas;
    }

    public static void guardarFactura(FacturaVistaVenta facturaDetallesVenta, ArticuloConsultas servicesArt, ClienteConsultas servicesClie, FacturaConsultas servicesFact) {
        if (facturaDetallesVenta.getTitle().equals("Detalles Factura")) {
            vaciarInputTexts(facturaDetallesVenta);
            facturaDetallesVenta.setVisible(false);
        } else {
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

    public static void abrirVistaFacturaVenta(FacturaVistaVenta facturaDetallesVenta, ArticuloConsultas servicesArt, ClienteConsultas servicesClie, FacturaConsultas servicesFact) {
        facturaDetallesVenta.setTitle("Nueva factura de venta");
        facturaDetallesVenta.setLocationRelativeTo(null);
        facturaDetallesVenta.setVisible(true);

        facturaDetallesVenta.inputTextNumero.setText(Integer.toString(servicesFact.getLastNumeroFactura() + 1));
        activarInteraccionVista(facturaDetallesVenta);


        DefaultTableModel tableModel = (DefaultTableModel) facturaDetallesVenta.tabla.getModel();
        tableModel.setNumRows(0);
        vaciarInputTexts(facturaDetallesVenta);

        inicializarDropdownClientes(facturaDetallesVenta, servicesClie);
        inicializarDropdownArticulos(facturaDetallesVenta, servicesArt);
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

    public static void abrirVistaFacturaVenta(FacturaVistaVenta facturaDetallesVenta, ArticuloConsultas servicesArt, ClienteConsultas servicesClie, Factura factura) {
        facturaDetallesVenta.setTitle("Detalles Factura");
        facturaDetallesVenta.setLocationRelativeTo(null);
        facturaDetallesVenta.setVisible(true);

        inicializarDropdownClientes(facturaDetallesVenta, servicesClie);
        inicializarDropdownArticulos(facturaDetallesVenta, servicesArt);

        desactivarInteraccionVista(facturaDetallesVenta);

        facturaDetallesVenta.inputTextNumero.setText(factura.getNumeroFactura());
        facturaDetallesVenta.dropdownCliente.setSelectedItem(factura.getCliente().getId() + "- " + factura.getCliente().getNombre());
        cargarTablaVerMas(facturaDetallesVenta, factura.getLineas());
        facturaDetallesVenta.inputTextTotal.setText(Float.toString(factura.getTotal()));
    }
}

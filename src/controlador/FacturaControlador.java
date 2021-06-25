package controlador;

import static controlador.FacturaVentaControlador.abrirVistaFacturaVenta;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;
import modelo.Factura;
import modelo.Linea;
import modelo.services.ArticuloConsultas;
import modelo.services.ClienteConsultas;
import modelo.services.FacturaConsultas;
import modelo.services.ProveedorConsultas;
import modelo.services.RubroConsultas;
import vista.FacturaVistaCompra;
import vista.FacturaVistaVenta;
import vista.Index;

public class FacturaControlador {

    public static void cargarTabla(JTable factTabla, ArrayList<Factura> facturas) {
        DefaultTableModel tableModel = (DefaultTableModel) factTabla.getModel();
        tableModel.setNumRows(0);

        facturas.forEach((factura) -> {
            String[] data = new String[8];
            data[0] = Integer.toString(factura.getId());
            data[1] = factura.getFecha().toString();
            data[2] = factura.getNumeroFactura();
            data[3] = Character.toString(factura.getProposito());
            data[4] = Float.toString(factura.getTotal());

            tableModel.addRow(data);
        });
    }

    public static void iniciarTabla(JTable factTabla, FacturaConsultas services) {
        ArrayList<Factura> facturas = services.getAllFacturas();
        cargarTabla(factTabla, facturas);
    }

    public static void noNegativosSpinner(JSpinner spinner) {
        if ((Integer) spinner.getValue() < 0) {
            spinner.setValue(0);
        }
    }

    public static void agregarArticulo(JTable tabla, JComboBox dropdownArticulo, JTextField inputTextPrecio, JSpinner spinnerCantidad, JTextField inputTextTotal) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) tabla.getModel();
            float subtotal = (Integer) spinnerCantidad.getValue() * Float.parseFloat(inputTextPrecio.getText());

            String[] data = new String[5];
            data[0] = dropdownArticulo.getSelectedItem().toString().split("-")[0];
            data[1] = dropdownArticulo.getSelectedItem().toString();
            data[2] = inputTextPrecio.getText();
            data[3] = spinnerCantidad.getValue().toString();
            data[4] = Float.toString(subtotal);
            tableModel.addRow(data);

            float total;
            if (inputTextTotal.getText().equals("")) {
                total = subtotal;
            } else {
                total = Float.parseFloat(inputTextTotal.getText()) + subtotal;
            }
            inputTextTotal.setText(Float.toString(total));
        } catch (Exception e) {
            // TODO: MANEJO VALIDACION
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error inesperado");
        }
    }

    public static void quitarArticulo(JTable tabla, JTextField inputTextTotal) {
        DefaultTableModel tablaModel = (DefaultTableModel) tabla.getModel();
        float subtotal = Float.parseFloat(tabla.getValueAt(tabla.getSelectedRow(), 4).toString());
        float total = Float.parseFloat(inputTextTotal.getText()) - subtotal;
        inputTextTotal.setText(Float.toString(total));

        tablaModel.removeRow(tabla.getSelectedRow());
    }

    public static ArrayList<Linea> generarLineas(JTable tabla, ArticuloConsultas servicesArt, Factura factura) {
        ArrayList<Linea> lineas = new ArrayList<>();

        for (int i = 0; i <= tabla.getRowCount() - 1; i++) {

            Articulo articulo = servicesArt.getArticulo(Integer.parseInt(tabla.getValueAt(i, 0).toString()));
            float precioUnitario = Float.parseFloat(tabla.getValueAt(i, 2).toString());
            int cantidad = Integer.parseInt(tabla.getValueAt(i, 3).toString());
            float subtotal = Float.parseFloat(tabla.getValueAt(i, 4).toString());

            Linea nuevaLinea = new Linea(articulo, factura, precioUnitario, cantidad, subtotal);
            lineas.add(nuevaLinea);

        }
        return lineas;
    }

    public static void abrirNuevaFactura(FacturaVistaVenta facturaDetallesVenta, FacturaVistaCompra facturaDetallesCompra,
            ArticuloConsultas servicesArt, ProveedorConsultas servicesProv, ClienteConsultas servicesClie, FacturaConsultas servicesFact) {
        int resp = JOptionPane.showOptionDialog(null, "Que tipo de factura desea hacer", "Nueva factura",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"VENTA", "COMPRA", "CANCELAR"}, null);
        switch (resp) {
            case 0:
                abrirVistaFacturaVenta(facturaDetallesVenta, servicesArt, servicesClie, servicesFact);
                break;
            case 1:
                facturaDetallesCompra.setTitle("Nueva factura de compra");
                facturaDetallesCompra.setLocationRelativeTo(null);
                facturaDetallesCompra.setVisible(true);
                break;
            default:
        }
    }
}

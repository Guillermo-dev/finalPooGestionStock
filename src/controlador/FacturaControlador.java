package controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Factura;
import modelo.services.ArticuloConsultas;
import modelo.services.ClienteConsultas;
import modelo.services.FacturaConsultas;
import modelo.services.ProveedorConsultas;
import modelo.services.RubroConsultas;
import vista.FacturaVistaCompra;
import vista.FacturaVistaVenta;

public class FacturaControlador {

    static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    public static void cargarTabla(JTable factTabla, ArrayList<Factura> facturas) {
        DefaultTableModel tableModel = (DefaultTableModel) factTabla.getModel();
        tableModel.setNumRows(0);

        facturas.forEach((factura) -> {
            String[] data = new String[8];
            data[0] = Integer.toString(factura.getId());
            data[1] = formatoFecha.format(factura.getFecha());
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

    public static void abrirNuevaFactura(FacturaVistaVenta facturaDetallesVenta, FacturaVistaCompra facturaDetallesCompra,
            ArticuloConsultas servicesArt, ProveedorConsultas servicesProv, ClienteConsultas servicesClie, FacturaConsultas servicesFact, RubroConsultas servicesRub) {
        int resp = JOptionPane.showOptionDialog(null, "Que tipo de factura desea hacer", "Nueva factura",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"VENTA", "COMPRA", "CANCELAR"}, null);
        switch (resp) {
            case 0:
                FacturaVentaControlador.abrirVistaFacturaVenta(facturaDetallesVenta, servicesArt, servicesClie, servicesFact);
                break;
            case 1:
                FacturaCompraControlador.abrirVistaFacturaCompra(facturaDetallesCompra, servicesArt, servicesProv, servicesFact, servicesRub);
                break;
            default:
        }
    }
}

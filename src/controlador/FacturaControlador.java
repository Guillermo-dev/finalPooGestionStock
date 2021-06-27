package controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JCheckBox;
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
import vista.Index;

public class FacturaControlador {

    static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    public static void cargarTabla(JTable factTabla, ArrayList<Factura> facturas) {
        DefaultTableModel tableModel = (DefaultTableModel) factTabla.getModel();
        tableModel.setNumRows(0);

        facturas.forEach((factura) -> {
            String[] data = new String[6];
            data[0] = Integer.toString(factura.getId());
            data[1] = formatoFecha.format(factura.getFecha());
            data[2] = factura.getNumeroFactura();
            data[3] = Character.toString(factura.getProposito()).equals("V") ? "VENTA" : "COMPRA";
            data[4] = Character.toString(factura.getProposito()).equals("V") ? factura.getCliente().getNombre()
                    : factura.getProveedor().getNombre() + " " + factura.getProveedor().getCuilCuit();
            data[5] = Float.toString(factura.getTotal());

            tableModel.addRow(data);
        });
    }

    public static void iniciarTabla(JTable factTabla, JCheckBox checkBoxCompra, JCheckBox checkBoxVenta) {
        if (checkBoxCompra.isSelected() && checkBoxVenta.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturas();
            cargarTabla(factTabla, facturas);
        } else if (checkBoxCompra.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturasFiltro('C');
            cargarTabla(factTabla, facturas);
        } else if (checkBoxVenta.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturasFiltro('V');
            cargarTabla(factTabla, facturas);
        } else {
            DefaultTableModel tableModel = (DefaultTableModel) factTabla.getModel();
            tableModel.setNumRows(0);
        }
    }

    public static void buscarTabla(JTable factTabla, JCheckBox checkBoxCompra, JCheckBox checkBoxVenta, String buscador) {
        if (checkBoxCompra.isSelected() && checkBoxVenta.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturas();

            //TODO FILTAR POR BUSCADOR
            cargarTabla(factTabla, facturas);
        } else if (checkBoxCompra.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturasFiltro('C');

            //TODO: FILTAR POR BUSCADOR
            cargarTabla(factTabla, facturas);
        } else if (checkBoxVenta.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturasFiltro('V');

            //TODO FILTAR POR BUSCADOR
            cargarTabla(factTabla, facturas);
        } else {
            DefaultTableModel tableModel = (DefaultTableModel) factTabla.getModel();
            tableModel.setNumRows(0);
        }
    }

    public static void cargarInputTexts(Index view, String idFactura, String fecha, String numero, String proposito, String total) {
        view.factInputTextId.setText(idFactura);
        view.factInputTextFecha.setText(fecha);
        view.factInputTextNumero.setText(numero);
        view.factInputTextProposito.setText(proposito);
        view.factInputTextTotal.setText(total);
    }

    public static void vaciarInputTexts(Index view) {
        view.factInputTextId.setText("");
        view.factInputTextFecha.setText("");
        view.factInputTextNumero.setText("");
        view.factInputTextProposito.setText("");
        view.factInputTextTotal.setText("");
    }

    public static void abrirDetallesFactura(Index view, FacturaVistaVenta facturaDetallesVenta, FacturaVistaCompra facturaDetallesCompra) {
        if (!view.factInputTextId.getText().equals("")) {
            Factura factura = FacturaConsultas.getFactura(Integer.parseInt(view.factInputTextId.getText()));
            if (factura.getProposito() == 'V') {
                FacturaVentaControlador.abrirVistaFacturaVenta(facturaDetallesVenta, factura);
            } else if (factura.getProposito() == 'C') {
                FacturaCompraControlador.abrirVistaFacturaVenta(facturaDetallesCompra, factura);
            }
        } else {
            JOptionPane.showMessageDialog(
                    facturaDetallesVenta,
                    "Selecciones una factura");
        }
    }

    public static void abrirNuevaFactura(FacturaVistaVenta facturaDetallesVenta, FacturaVistaCompra facturaDetallesCompra) {
        int resp = JOptionPane.showOptionDialog(null, "Que tipo de factura desea hacer", "Nueva factura",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"VENTA", "COMPRA", "CANCELAR"}, null);
        switch (resp) {
            case 0:
                FacturaVentaControlador.abrirVistaFacturaVenta(facturaDetallesVenta);
                break;
            case 1:
                FacturaCompraControlador.abrirVistaFacturaCompra(facturaDetallesCompra);
                break;
            default:
        }
    }
}

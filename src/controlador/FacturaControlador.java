package controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Factura;
import modelo.services.FacturaConsultas;
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

    public static void iniciarTabla(Index view) {
        if (view.factCheckBoxCompra.isSelected() && view.factCheckBoxVenta.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturas();
            cargarTabla(view.factTabla, facturas);
        } else if (view.factCheckBoxCompra.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturasFiltro('C');
            cargarTabla(view.factTabla, facturas);
        } else if (view.factCheckBoxVenta.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturasFiltro('V');
            cargarTabla(view.factTabla, facturas);
        } else {
            DefaultTableModel tableModel = (DefaultTableModel) view.factTabla.getModel();
            tableModel.setNumRows(0);
        }
    }

    public static void buscarTabla(Index view) {
        String buscador = view.factInputTextBuscador.getText();
        if (view.factCheckBoxCompra.isSelected() && view.factCheckBoxVenta.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturas();

            //TODO FILTAR POR BUSCADOR
            cargarTabla(view.factTabla, facturas);
        } else if (view.factCheckBoxCompra.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturasFiltro('C');

            //TODO: FILTAR POR BUSCADOR
            cargarTabla(view.factTabla, facturas);
        } else if (view.factCheckBoxVenta.isSelected()) {
            ArrayList<Factura> facturas = FacturaConsultas.getAllFacturasFiltro('V');

            //TODO FILTAR POR BUSCADOR
            cargarTabla(view.factTabla, facturas);
        } else {
            DefaultTableModel tableModel = (DefaultTableModel) view.factTabla.getModel();
            tableModel.setNumRows(0);
        }
    }

    public static void cargarInputTexts(Index view) {
        view.factInputTextId.setText(view.factTabla.getValueAt(view.factTabla.getSelectedRow(), 0).toString());
        view.factInputTextFecha.setText(view.factTabla.getValueAt(view.factTabla.getSelectedRow(), 1).toString());
        view.factInputTextNumero.setText(view.factTabla.getValueAt(view.factTabla.getSelectedRow(), 2).toString());
        view.factInputTextProposito.setText(view.factTabla.getValueAt(view.factTabla.getSelectedRow(), 3).toString());
        view.factInputTextTotal.setText(view.factTabla.getValueAt(view.factTabla.getSelectedRow(), 5).toString());
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

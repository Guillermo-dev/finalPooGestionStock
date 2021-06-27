package controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Factura;
import modelo.NotaCredito;
import modelo.services.ArticuloConsultas;
import modelo.services.ClienteConsultas;
import modelo.services.FacturaConsultas;
import modelo.services.NotaCreditoConsultas;
import vista.FacturaVistaVenta;
import vista.Index;

public class NotasDeCreditoControlador {

    static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    public static void iniciarDropdownFacturas(Index view, FacturaConsultas servicesFact) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.notDropDownFactura.getModel();
        ArrayList<Factura> facturas = servicesFact.getAllFacturasFiltro('V');

        view.notDropDownFactura.removeAllItems();
        dropModel.addElement("<Seleccionar Factura>");
        facturas.forEach(factura -> {
            dropModel.addElement(factura.getId() + "- " + factura.getNumeroFactura());
        });
    }

    public static void cargarTabla(JTable notTabla, ArrayList<NotaCredito> notasCreditos) {
        DefaultTableModel tableModel = (DefaultTableModel) notTabla.getModel();
        tableModel.setNumRows(0);

        notasCreditos.forEach((notaCredito) -> {
            String[] data = new String[5];
            data[0] = Integer.toString(notaCredito.getId());
            data[1] = formatoFecha.format(notaCredito.getFecha());
            data[2] = notaCredito.getFactura().getId() + "- " + notaCredito.getFactura().getNumeroFactura();
            data[3] = notaCredito.getCliente().getNombre() + " " + notaCredito.getCliente().getApellido();
            data[4] = Float.toString(notaCredito.getImporte());

            tableModel.addRow(data);
        });
    }

    public static void iniciarTabla(JTable notTabla, NotaCreditoConsultas servicesNot) {
        ArrayList<NotaCredito> notasCreditos = servicesNot.getAllNotasCredito();
        cargarTabla(notTabla, notasCreditos);
    }

    public static void cargarDatosFactura(Index view, FacturaConsultas servicesFact) {
        if (view.notDropDownFactura.getItemCount() != 0) {
            if (!view.notDropDownFactura.getSelectedItem().equals("<Seleccionar Factura>")) {
                int idFactura = Integer.parseInt(view.notDropDownFactura.getSelectedItem().toString().split("-")[0]);
                Factura factura = servicesFact.getFactura(idFactura);

                view.notInputTextCliente.setText(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
                view.notInputTextFecha.setText(formatoFecha.format(factura.getFecha()));
                view.notInputTextTotal.setText(Float.toString(factura.getTotal()));
            }
        }

    }

    public static void abrirDetallesFactura(Index view, FacturaVistaVenta facturaDetallesVenta, ArticuloConsultas servicesArt, ClienteConsultas servicesClie, FacturaConsultas servicesFact) {
        if (!view.notInputTextId.getText().equals("")) {
            int idFactura = Integer.parseInt(view.notDropDownFactura.getSelectedItem().toString().split("-")[0]);
            Factura factura = servicesFact.getFactura(idFactura);
            FacturaVentaControlador.abrirVistaFacturaVenta(facturaDetallesVenta, servicesArt, servicesClie, factura);
        } else {
            JOptionPane.showMessageDialog(
                    facturaDetallesVenta,
                    "Selecciones una factura");
        }
    }

    public static void cargarInputsTexts(Index view, String idNotaCredito, String fecha, String factura, String cliente, String importe) {
        view.notInputTextId.setText(idNotaCredito);
        view.notDropDownFactura.setSelectedItem(factura);
        view.notInputTextCliente.setText(cliente);
        view.notInputTextFecha.setText(fecha);
        view.notInputTextTotal.setText(importe);
    }

    public static void vaciarInputsTexts(Index view) {
        view.notInputTextId.setText("");
        view.notDropDownFactura.setSelectedItem("<Seleccionar Factura>");
        view.notInputTextCliente.setText("");
        view.notInputTextFecha.setText("");
        view.notInputTextTotal.setText("");
    }

    public static boolean notaCreditoExistente(Index view, NotaCreditoConsultas servicesNot) {
        ArrayList<NotaCredito> notasCredito = servicesNot.getAllNotasCredito();
        int facturaId = Integer.parseInt(view.notDropDownFactura.getSelectedItem().toString().split("-")[0]);
        for (NotaCredito notaCredito : notasCredito) {
            if (notaCredito.getFactura().getId() == facturaId) {
                return true;
            }
        }
        return false;
    }

    public static void crearNotaCredito(Index view, FacturaConsultas servicesFact, NotaCreditoConsultas servicesNot) {
        if (!view.notDropDownFactura.getSelectedItem().equals("<Seleccionar Factura>")) {
            if (notaCreditoExistente(view, servicesNot)) {
                JOptionPane.showMessageDialog(
                        view,
                        "Ya existe una nota de credito para esta factura");
            } else {
                int resp = JOptionPane.showConfirmDialog(view, "Seguro de crear una nota de credito para la factura?\n"
                        + "Numero:" + view.notDropDownFactura.getSelectedItem().toString().split("-")[1]);
                if (resp == 0) {
                    int idFactura = Integer.parseInt(view.notDropDownFactura.getSelectedItem().toString().split("-")[0]);
                    Factura factura = servicesFact.getFactura(idFactura);
                    Cliente cliente = factura.getCliente();
                    Date fecha = new Date();

                    NotaCredito notaCredito = new NotaCredito(factura, cliente, factura.getNumeroFactura(), factura.getTotal(), fecha);
                    servicesNot.saveFactura(notaCredito);

                    iniciarTabla(view.notTabla, servicesNot);
                    vaciarInputsTexts(view);
                }
            }
        }
    }
}

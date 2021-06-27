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
import modelo.services.FacturaConsultas;
import modelo.services.NotaCreditoConsultas;
import vista.FacturaVistaVenta;
import vista.Index;

public class NotasDeCreditoControlador {

    static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    public static void iniciarDropdownFacturas(Index view) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.notDropDownFactura.getModel();
        ArrayList<Factura> facturas = FacturaConsultas.getAllFacturasFiltro('V');

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

    public static void iniciarTabla(Index view) {
        ArrayList<NotaCredito> notasCreditos = NotaCreditoConsultas.getAllNotasCredito();
        cargarTabla(view.notTabla, notasCreditos);
    }

    public static void cargarDatosFactura(Index view) {
        if (view.notDropDownFactura.getItemCount() != 0) {
            if (!view.notDropDownFactura.getSelectedItem().equals("<Seleccionar Factura>")) {
                int idFactura = Integer.parseInt(view.notDropDownFactura.getSelectedItem().toString().split("-")[0]);
                Factura factura = FacturaConsultas.getFactura(idFactura);

                view.notInputTextCliente.setText(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
                view.notInputTextFecha.setText(formatoFecha.format(factura.getFecha()));
                view.notInputTextTotal.setText(Float.toString(factura.getTotal()));
            }
        }

    }

    public static void abrirDetallesFactura(Index view, FacturaVistaVenta facturaDetallesVenta) {
        if (!view.notInputTextId.getText().equals("")) {
            int idFactura = Integer.parseInt(view.notDropDownFactura.getSelectedItem().toString().split("-")[0]);
            Factura factura = FacturaConsultas.getFactura(idFactura);
            FacturaVentaControlador.abrirVistaFacturaVenta(facturaDetallesVenta, factura);
        } else {
            JOptionPane.showMessageDialog(
                    facturaDetallesVenta,
                    "Selecciones una factura");
        }
    }

    public static void cargarInputsTexts(Index view) {
        view.notInputTextId.setText(view.notTabla.getValueAt(view.notTabla.getSelectedRow(), 0).toString());
        view.notInputTextFecha.setText(view.notTabla.getValueAt(view.notTabla.getSelectedRow(), 1).toString());
        view.notDropDownFactura.setSelectedItem(view.notTabla.getValueAt(view.notTabla.getSelectedRow(), 2).toString());
        view.notInputTextCliente.setText(view.notTabla.getValueAt(view.notTabla.getSelectedRow(), 3).toString());
        view.notInputTextTotal.setText(view.notTabla.getValueAt(view.notTabla.getSelectedRow(), 4).toString());
    }

    public static void vaciarInputsTexts(Index view) {
        view.notInputTextId.setText("");
        view.notDropDownFactura.setSelectedItem("<Seleccionar Factura>");
        view.notInputTextCliente.setText("");
        view.notInputTextFecha.setText("");
        view.notInputTextTotal.setText("");
    }

    public static boolean notaCreditoExistente(Index view) {
        ArrayList<NotaCredito> notasCredito = NotaCreditoConsultas.getAllNotasCredito();
        int facturaId = Integer.parseInt(view.notDropDownFactura.getSelectedItem().toString().split("-")[0]);
        for (NotaCredito notaCredito : notasCredito) {
            if (notaCredito.getFactura().getId() == facturaId) {
                return true;
            }
        }
        return false;
    }

    public static void crearNotaCredito(Index view) {
        if (!view.notDropDownFactura.getSelectedItem().equals("<Seleccionar Factura>")) {
            if (notaCreditoExistente(view)) {
                JOptionPane.showMessageDialog(
                        view,
                        "Ya existe una nota de credito para esta factura");
            } else {
                int resp = JOptionPane.showConfirmDialog(view, "Seguro de crear una nota de credito para la factura?\n"
                        + "Numero:" + view.notDropDownFactura.getSelectedItem().toString().split("-")[1]);
                if (resp == 0) {
                    int idFactura = Integer.parseInt(view.notDropDownFactura.getSelectedItem().toString().split("-")[0]);
                    Factura factura = FacturaConsultas.getFactura(idFactura);
                    Cliente cliente = factura.getCliente();
                    Date fecha = new Date();

                    NotaCredito notaCredito = new NotaCredito(factura, cliente, factura.getNumeroFactura(), factura.getTotal(), fecha);
                    NotaCreditoConsultas.saveFactura(notaCredito);

                    iniciarTabla(view);
                    vaciarInputsTexts(view);
                }
            }
        }
    }
}

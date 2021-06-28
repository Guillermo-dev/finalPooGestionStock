package controlador;

import controlador.excepciones.Excepcion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
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

    private static final String SELECCIONAR_CLIENTE = "<Seleccionar cliente>";
    private static final String SELECCIONAR_ARTICULO = "<Seleccionar Articulo>";

    public static void inicializarDropdownClientes(FacturaVistaVenta viewFacturasDetallesVenta) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) viewFacturasDetallesVenta.dropdownCliente.getModel();
        ArrayList<Cliente> clientes = ClienteConsultas.getAllClientes();

        viewFacturasDetallesVenta.dropdownCliente.removeAllItems();
        dropModel.addElement(SELECCIONAR_CLIENTE);
        clientes.forEach(cliente -> {
            dropModel.addElement(cliente.getId() + "- " + cliente.getNombre());
        });
    }

    public static boolean articuloStockMinimo(Articulo articulo) {
        return articulo.getStockActual() < articulo.getStockMinimo();
    }

    public static void inicializarDropdownArticulos(FacturaVistaVenta viewFacturasDetallesVenta) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) viewFacturasDetallesVenta.dropdownArticulo.getModel();
        ArrayList<Articulo> articulos = ArticuloConsultas.getAllArticulos();

        viewFacturasDetallesVenta.dropdownArticulo.removeAllItems();
        dropModel.addElement(SELECCIONAR_ARTICULO);
        articulos.forEach(articulo -> {
            if (!articuloStockMinimo(articulo)) {
                dropModel.addElement(articulo.getId() + "- " + articulo.getNombre());
            }

        });
    }

    public static void seleccionarArticulo(FacturaVistaVenta viewFacturasDetallesVenta) {
        if (viewFacturasDetallesVenta.dropdownArticulo.getItemCount() != 0) {
            if (!viewFacturasDetallesVenta.dropdownArticulo.getSelectedItem().equals(SELECCIONAR_ARTICULO)) {
                int idArticulo = Integer.parseInt(viewFacturasDetallesVenta.dropdownArticulo.getSelectedItem().toString().split("-")[0]);
                Articulo articulo = ArticuloConsultas.getArticulo(idArticulo);

                viewFacturasDetallesVenta.inputTextPrecio.setText(Float.toString(articulo.getPrecioUnitario()));
                viewFacturasDetallesVenta.spinnerCantidad.setValue(1);
            }
        }
    }

    public static void noNegativosSpinner(FacturaVistaVenta viewFacturasDetallesVenta) {
        if ((Integer) viewFacturasDetallesVenta.spinnerCantidad.getValue() < 1) {
            viewFacturasDetallesVenta.spinnerCantidad.setValue(1);
        }
    }

    public static void cargarInputTexts(FacturaVistaVenta viewFacturasDetallesVenta) {
        viewFacturasDetallesVenta.dropdownArticulo.setSelectedItem(viewFacturasDetallesVenta.tabla.getValueAt(viewFacturasDetallesVenta.tabla.getSelectedRow(), 1).toString());
        viewFacturasDetallesVenta.inputTextPrecio.setText(viewFacturasDetallesVenta.tabla.getValueAt(viewFacturasDetallesVenta.tabla.getSelectedRow(), 2).toString());
        viewFacturasDetallesVenta.spinnerCantidad.setValue(Integer.parseInt(viewFacturasDetallesVenta.tabla.getValueAt(viewFacturasDetallesVenta.tabla.getSelectedRow(), 3).toString()));
    }

    public static void vaciarInputTexts(FacturaVistaVenta viewFacturasDetallesVenta) {
        viewFacturasDetallesVenta.dropdownCliente.setSelectedItem(SELECCIONAR_CLIENTE);
        viewFacturasDetallesVenta.dropdownArticulo.setSelectedItem(SELECCIONAR_ARTICULO);
        viewFacturasDetallesVenta.inputTextPrecio.setText("");
        viewFacturasDetallesVenta.spinnerCantidad.setValue(0);
        viewFacturasDetallesVenta.inputTextTotal.setText("");
    }

    
    public static int calcularStockSolicitado (FacturaVistaVenta viewFacturasDetallesVenta, Articulo articulo){
        int stock = (Integer) viewFacturasDetallesVenta.spinnerCantidad.getValue();
                
        for (int i = 0; i <= viewFacturasDetallesVenta.tabla.getRowCount() - 1; i++) {
            if (Integer.parseInt(viewFacturasDetallesVenta.tabla.getValueAt(i, 0).toString()) == articulo.getId()){
                stock =  stock + Integer.parseInt(viewFacturasDetallesVenta.tabla.getValueAt(i, 3).toString());
            }
        }
        return stock;
    }
    
    
    public static void agregarArticulo(FacturaVistaVenta viewFacturasDetallesVenta) {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) viewFacturasDetallesVenta.tabla.getModel();
            float subtotal = (Integer) viewFacturasDetallesVenta.spinnerCantidad.getValue() * Float.parseFloat(viewFacturasDetallesVenta.inputTextPrecio.getText());
            
            int idArticulo = Integer.parseInt(viewFacturasDetallesVenta.dropdownArticulo.getSelectedItem().toString().split("-")[0]);
            Articulo articulo = ArticuloConsultas.getArticulo(idArticulo);
            int stockActual = articulo.getStockActual();
            int stockSolicitado = calcularStockSolicitado(viewFacturasDetallesVenta, articulo);
            Excepcion.comprobarVentaArticulo(stockActual, stockSolicitado);
            
            String[] data = new String[5];
            data[0] = viewFacturasDetallesVenta.dropdownArticulo.getSelectedItem().toString().split("-")[0];
            data[1] = viewFacturasDetallesVenta.dropdownArticulo.getSelectedItem().toString();
            data[2] = viewFacturasDetallesVenta.inputTextPrecio.getText();
            data[3] = viewFacturasDetallesVenta.spinnerCantidad.getValue().toString();
            data[4] = Float.toString(subtotal);
            tableModel.addRow(data);

            float total;
            if (viewFacturasDetallesVenta.inputTextTotal.getText().equals("")) {
                total = subtotal;
            } else {
                total = Float.parseFloat(viewFacturasDetallesVenta.inputTextTotal.getText()) + subtotal;
            }
            viewFacturasDetallesVenta.inputTextTotal.setText(Float.toString(total));

            viewFacturasDetallesVenta.dropdownCliente.disable();
        } 
        catch (Excepcion e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch (Exception e) {
            // TODO: MANEJO VALIDACION
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error inesperado");
        }
    }

    public static void quitarArticulo(FacturaVistaVenta viewFacturasDetallesVenta) {
        if (viewFacturasDetallesVenta.tabla.getSelectedRow() != -1) {
            DefaultTableModel tablaModel = (DefaultTableModel) viewFacturasDetallesVenta.tabla.getModel();
            float subtotal = Float.parseFloat(viewFacturasDetallesVenta.tabla.getValueAt(viewFacturasDetallesVenta.tabla.getSelectedRow(), 4).toString());
            float total = Float.parseFloat(viewFacturasDetallesVenta.inputTextTotal.getText()) - subtotal;
            viewFacturasDetallesVenta.inputTextTotal.setText(Float.toString(total));

            tablaModel.removeRow(viewFacturasDetallesVenta.tabla.getSelectedRow());
            if (viewFacturasDetallesVenta.tabla.getRowCount() == 0) {
                viewFacturasDetallesVenta.dropdownCliente.enable();
            }
        } else {
            JOptionPane.showMessageDialog(viewFacturasDetallesVenta, "Seleccionar un articulo para eliminar");
        }
    }

    public static boolean facturaInvalida(FacturaVistaVenta viewFacturasDetallesVenta) {
        if (viewFacturasDetallesVenta.tabla.getRowCount() == 0) {
            return true;
        }
        if (viewFacturasDetallesVenta.dropdownCliente.getSelectedItem().equals(SELECCIONAR_CLIENTE)) {
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

            if (articuloStockMinimo(articulo)) {
                JOptionPane.showMessageDialog(null, "El articulo:" + articulo.getNombre() + "\n Esta por debajo de su stock minimo");
            }

            Linea nuevaLinea = new Linea(articulo, factura, precioUnitario, cantidad, subtotal);
            lineas.add(nuevaLinea);

        }
        return lineas;
    }

    public static void guardarFactura(Index view, FacturaVistaVenta viewFacturasDetallesVenta) {
        if (viewFacturasDetallesVenta.getTitle().equals("Detalles Factura")) {
            vaciarInputTexts(viewFacturasDetallesVenta);
            viewFacturasDetallesVenta.setVisible(false);
        } else {
            if (facturaInvalida(viewFacturasDetallesVenta)) {
                JOptionPane.showMessageDialog(null, "Error intenttelo nuevamente");
            } else {
                int idCliente = Integer.parseInt(viewFacturasDetallesVenta.dropdownCliente.getSelectedItem().toString().split("-")[0]);

                Cliente cliente = ClienteConsultas.getCliente(idCliente);
                String numeroFactura = viewFacturasDetallesVenta.inputTextNumero.getText();
                Date fecha = new Date();
                float total = Float.parseFloat(viewFacturasDetallesVenta.inputTextTotal.getText());

                Factura factura = new Factura(cliente, 'V', numeroFactura, fecha, total);

                ArrayList<Linea> lineas = generarLineas(viewFacturasDetallesVenta.tabla, factura);
                factura.setLineas(lineas);

                FacturaConsultas.saveFactura(factura);

                vaciarInputTexts(viewFacturasDetallesVenta);
                viewFacturasDetallesVenta.setVisible(false);
                FacturaControlador.iniciarTabla(view);
            }
        }

    }

    public static void activarInteraccionVista(FacturaVistaVenta viewFacturasDetallesVenta) {
        viewFacturasDetallesVenta.spinnerCantidad.setEnabled(true);
        viewFacturasDetallesVenta.btnAgregarProducto.setVisible(true);
        viewFacturasDetallesVenta.btnBorrarProducto.setVisible(true);
        viewFacturasDetallesVenta.dropdownArticulo.setEnabled(true);
        viewFacturasDetallesVenta.dropdownCliente.setEnabled(true);
    }

    public static void imprimirFactura(FacturaVistaVenta viewFacturasDetallesVenta) {
        if (!facturaInvalida(viewFacturasDetallesVenta)) {
            JOptionPane.showMessageDialog(
                    viewFacturasDetallesVenta,
                    "Imprimiendo factura numero:" + viewFacturasDetallesVenta.inputTextNumero.getText()
                    + "\n chuck  \n chuck  \n chuck \n primmmmmm");
        }
    }

    public static void abrirVistaFacturaVenta(FacturaVistaVenta viewFacturasDetallesVenta) {
        viewFacturasDetallesVenta.setTitle("Nueva factura de venta");
        viewFacturasDetallesVenta.setLocationRelativeTo(null);
        viewFacturasDetallesVenta.setVisible(true);

        ((JSpinner.DefaultEditor) viewFacturasDetallesVenta.spinnerCantidad.getEditor()).getTextField().setEditable(false);
        viewFacturasDetallesVenta.inputTextNumero.setText(Integer.toString(FacturaConsultas.getLastNumeroFactura() + 1));
        activarInteraccionVista(viewFacturasDetallesVenta);

        DefaultTableModel tableModel = (DefaultTableModel) viewFacturasDetallesVenta.tabla.getModel();
        tableModel.setNumRows(0);
        vaciarInputTexts(viewFacturasDetallesVenta);

        inicializarDropdownClientes(viewFacturasDetallesVenta);
        inicializarDropdownArticulos(viewFacturasDetallesVenta);
    }

    public static void cargarTablaVerMas(FacturaVistaVenta viewFacturasDetallesVenta, List<Linea> lineas) {
        DefaultTableModel tableModel = (DefaultTableModel) viewFacturasDetallesVenta.tabla.getModel();
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

    public static void desactivarInteraccionVista(FacturaVistaVenta viewFacturasDetallesVenta) {
        viewFacturasDetallesVenta.spinnerCantidad.setEnabled(false);
        viewFacturasDetallesVenta.btnAgregarProducto.setVisible(false);
        viewFacturasDetallesVenta.btnBorrarProducto.setVisible(false);
        viewFacturasDetallesVenta.dropdownArticulo.setEnabled(false);
        viewFacturasDetallesVenta.dropdownCliente.setEnabled(false);
    }

    public static void abrirVistaFacturaVenta(FacturaVistaVenta viewFacturasDetallesVenta, Factura factura) {
        viewFacturasDetallesVenta.setTitle("Detalles Factura");
        viewFacturasDetallesVenta.setLocationRelativeTo(null);
        viewFacturasDetallesVenta.setVisible(true);

        inicializarDropdownClientes(viewFacturasDetallesVenta);
        inicializarDropdownArticulos(viewFacturasDetallesVenta);

        desactivarInteraccionVista(viewFacturasDetallesVenta);

        viewFacturasDetallesVenta.inputTextNumero.setText(factura.getNumeroFactura());
        viewFacturasDetallesVenta.dropdownCliente.setSelectedItem(factura.getCliente().getId() + "- " + factura.getCliente().getNombre());
        cargarTablaVerMas(viewFacturasDetallesVenta, factura.getLineas());
        viewFacturasDetallesVenta.inputTextTotal.setText(Float.toString(factura.getTotal()));
    }
}

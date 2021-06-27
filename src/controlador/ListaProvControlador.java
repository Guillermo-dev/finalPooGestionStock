package controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Factura;
import modelo.Proveedor;
import modelo.services.FacturaConsultas;
import vista.ListaComprasProveedor;

public class ListaProvControlador {

    static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    public static void cargarTabla(JTable tabla, ArrayList<Factura> ventas) {
        DefaultTableModel tableModel = (DefaultTableModel) tabla.getModel();
        tableModel.setNumRows(0);

        ventas.forEach((venta) -> {
            venta.getLineas().forEach((linea) -> {
                String[] data = new String[6];
                data[0] = formatoFecha.format(venta.getFecha());
                data[1] = linea.getArticulo().getNombre();
                data[2] = Integer.toString(linea.getCantidad());
                data[3] = Float.toString(linea.getPrecioUnitario());
                data[4] = Float.toString(linea.getSubTotal());
                data[5] = linea.getArticulo().getRubro().getNombre();
                tableModel.addRow(data);
            });
        });
    }

    public static void cargarInfo(ListaComprasProveedor listaProveedores, Proveedor proveedor) {
        listaProveedores.inputTextProv.setText(proveedor.getCuilCuit() + " " + proveedor.getNombre());

        ArrayList<Factura> ventas = FacturaConsultas.getFacturasProveedor(proveedor);
        cargarTabla(listaProveedores.tabla, ventas);
    }

    public static void abrirListaCompra(ListaComprasProveedor listaProveedores, Proveedor proveedor) {
        listaProveedores.setTitle("Lista de Proveedores");
        listaProveedores.setLocationRelativeTo(null);
        listaProveedores.setVisible(true);

        cargarInfo(listaProveedores, proveedor);
    }
    
    public static void cerrarListaCompra(ListaComprasProveedor listaProveedores){
        listaProveedores.setVisible(false);
    }
}

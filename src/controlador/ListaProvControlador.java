package controlador;

import vista.ListaComprasProveedor;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;
import modelo.services.ArticuloConsultas;
import vista.Index;

public class ListaProvControlador {

    public static void listarProveedores(ListaComprasProveedor listaProveedores) {
        listaProveedores.setTitle("Lista de Proveedores");
        listaProveedores.setLocationRelativeTo(null);
        listaProveedores.setVisible(true);
    }

    /*
     public static void cargarTabla(JTable listaProvTabla, ArrayList<Articulo> listaCompraArticulo, int idProveedor) {
        DefaultTableModel tableModel = (DefaultTableModel) listaProvTabla.getModel();
        tableModel.setNumRows(0);

        // estos datos no se traen de la factura del proveedor??
        
        listaCompraArticulo.forEach((articulo) -> {
            String[] data = new String[6];
            data[0] = Integer.toString(articulo.getFecha());
            data[1] = articulo.getNombre();
            data[2] = Integer.toString(articulo.getCantidad());
            data[3] = Integer.toString(articulo.getPrecioUnitario());
            data[4] = Integer.toString(articulo.getSubtotal()));
            data[5] = articulo.getRubro());
            tableModel.addRow(data);
        });
    }
    
    
     //pasar lista de articulos
    public static void iniciarTabla(JTable clieTabla, ArticuloConsultas services) {
        ArrayList<Articulo> articulos = services.getAllArticulosIdProveedor(idProveedor);
        cargarTabla(listaProvTabla, articulos, idProveedor);
    }
    */
    
    
  
    
    
    
    
    
    
    
    
    
    
}

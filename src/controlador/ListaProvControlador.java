package controlador;

import vista.ListaComprasProveedor;

public class ListaProvControlador {

    public static void listarProveedores(ListaComprasProveedor listaProveedores) {
        listaProveedores.setTitle("Lista de Proveedores");
        listaProveedores.setLocationRelativeTo(null);
        listaProveedores.setVisible(true);
    }

}

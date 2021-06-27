package gestiondestock;

import controlador.Controlador;
import vista.FacturaVistaCompra;
import vista.FacturaVistaVenta;
import vista.Index;
import vista.ListaComprasProveedor;

public class GestionDeStock {

    public static void main(String[] args) {
        // PATRON DE DISEÑO MVC (MODELO, VISTA, CONTROLADOR)
        Index view = new Index();
        FacturaVistaVenta viewFacturasDetallesVenta = new FacturaVistaVenta();
        FacturaVistaCompra viewFacturasDetallesCompra = new FacturaVistaCompra();
        ListaComprasProveedor viewListaProveedores = new ListaComprasProveedor();

        Controlador ctrl = new Controlador(view, viewFacturasDetallesVenta, viewFacturasDetallesCompra, viewListaProveedores);

        ctrl.iniciar();

        //Agregar el diseño de la vista  3- AcrylLookAndFeel()
    }

}

package gestiondestock;

import controlador.Controlador;
import modelo.services.ArticuloConsultas;
import modelo.services.ClienteConsultas;
import modelo.services.FacturaConsultas;
import modelo.services.NotaCreditoConsultas;
import modelo.services.ProveedorConsultas;
import modelo.services.RubroConsultas;
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
        ArticuloConsultas domConsultasArt = new ArticuloConsultas();
        ProveedorConsultas domConsultasProv = new ProveedorConsultas();
        ClienteConsultas domConsultasClie = new ClienteConsultas();
        RubroConsultas domConsultasRub = new RubroConsultas();
        FacturaConsultas domConsultasFact = new FacturaConsultas();
        NotaCreditoConsultas domConsultasNot = new NotaCreditoConsultas();
        

        Controlador ctrl = new Controlador(view, viewFacturasDetallesVenta, viewFacturasDetallesCompra, viewListaProveedores, 
                domConsultasArt, domConsultasProv, domConsultasClie, domConsultasRub, domConsultasFact, domConsultasNot );

        ctrl.iniciar();

        //Agregar el diseño de la vista  3- AcrylLookAndFeel()
    }

}

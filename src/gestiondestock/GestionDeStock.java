package gestiondestock;

import controlador.Controlador;
import modelo.services.ArticuloConsultas;
import modelo.services.ClienteConsultas;
import modelo.services.ProveedorConsultas;
import modelo.services.RubroConsultas;
import vista.FacturaVista;
import vista.Index;

public class GestionDeStock {

    public static void main(String[] args) {
        // PATRON DE DISEÑO MVC (MODELO, VISTA, CONTROLADOR)
        Index view = new Index();
        FacturaVista facturasDetalles = new FacturaVista();
        ArticuloConsultas domConsultasArt = new ArticuloConsultas();
        ProveedorConsultas domConsultasProv = new ProveedorConsultas();
        ClienteConsultas domConsultasClie = new ClienteConsultas();
        RubroConsultas domConsultasRub = new RubroConsultas();

        Controlador ctrl = new Controlador(view, domConsultasArt, facturasDetalles, domConsultasProv, domConsultasClie, domConsultasRub);

        ctrl.iniciar();

        //Agregar el diseño de la vista  3- AcrylLookAndFeel()
    }

}

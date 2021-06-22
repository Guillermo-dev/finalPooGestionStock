package gestiondestock;

import controlador.Controlador;
import modelo.services.ClienteConsultas;
import vista.FacturaVista;
import vista.Index;

public class GestionDeStock {

    public static void main(String[] args) {
        // PATRON DE DISEÑO MVC (MODELO, VISTA, CONTROLADOR)
       Index view = new Index();
       FacturaVista facturasDetalles = new FacturaVista();
       ClienteConsultas domConsultasClie = new ClienteConsultas();
       
       Controlador ctrl = new Controlador(view,facturasDetalles, domConsultasClie);
       ctrl.iniciar();
    }

}

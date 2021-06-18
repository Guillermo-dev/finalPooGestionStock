package gestiondestock;

import controlador.Controlador;
import modelo.services.ClienteConsultas;
import vista.FacturaDetalles;
import vista.Index;

public class GestionDeStock {

    public static void main(String[] args) {
        // PATRON DE DISEÑO MVC (MODELO, VISTA, CONTROLADOR)
       Index view = new Index();
       FacturaDetalles facturasDetalles = new FacturaDetalles();
       ClienteConsultas domConsultasClie = new ClienteConsultas();
       
       Controlador ctrl = new Controlador(view,facturasDetalles, domConsultasClie);
       ctrl.iniciar();
       view.setVisible(true);

    }

}

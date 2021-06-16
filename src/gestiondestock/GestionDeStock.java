package gestiondestock;

import controlador.Controlador;
import modelo.services.ClienteConsultas;
import vista.Index;

public class GestionDeStock {

    public static void main(String[] args) {
        // PATRON DE DISEÑO MVC (MODELO, VISTA, CONTROLADOR)
       Index view = new Index();
       ClienteConsultas domConsultasClie = new ClienteConsultas();
       
       Controlador ctrl = new Controlador(view, domConsultasClie);
       ctrl.iniciar();
       view.setVisible(true);

    }

}

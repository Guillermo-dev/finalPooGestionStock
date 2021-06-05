package gestiondestock;

import controlador.Controlador;
import vista.Index;

public class GestionDeStock {

    public static void main(String[] args) {
        // PATRON DE DISEÑO MVC (MODELO, VISTA, CONTROLADOR)
        // Modelo model = new Modelo(); CREO que no es necesario
        Index view = new Index();
        Controlador ctrl = new Controlador(view);
        ctrl.iniciar();
    }

}

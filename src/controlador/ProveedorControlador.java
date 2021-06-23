package controlador;

import javax.swing.DefaultComboBoxModel;
import vista.Index;

public class ProveedorControlador {

    // En bd responsable_inscripto", "monotributista", "consumidor_final"
    static String[] RAZONES_SOCIALES = {"Responsable inscripto", "Monotributista", "Consumidor final"};

    public static void iniciarDropdownRazonSocial(Index view) {
        DefaultComboBoxModel dropModel = (DefaultComboBoxModel) view.provDropdownRazonSocial.getModel();
        for (String RAZONES_SOCIALES1 : RAZONES_SOCIALES) {
            dropModel.addElement(RAZONES_SOCIALES1);
        }
    }
}

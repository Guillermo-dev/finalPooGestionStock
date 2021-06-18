package controlador;

import vista.FacturaDetalles;

public class FacturaControlador {
    
    public static void open (FacturaDetalles facturasDetalles) {
        facturasDetalles.setTitle("Detalles de fatura");
        facturasDetalles.setLocationRelativeTo(null);
        facturasDetalles.setVisible(true);
    }
}

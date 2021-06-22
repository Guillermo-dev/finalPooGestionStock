package controlador;

import vista.FacturaVista;

public class FacturaControlador {
    
    public static void open (FacturaVista facturasDetalles) {
        facturasDetalles.setTitle("Detalles de fatura");
        facturasDetalles.setLocationRelativeTo(null);
        facturasDetalles.setVisible(true);
    }
}

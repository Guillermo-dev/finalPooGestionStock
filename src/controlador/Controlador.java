package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.services.ClienteConsultas;
import vista.FacturaDetalles;
import vista.Index;

public class Controlador implements ActionListener, ListSelectionListener {

    private final Index view;
    private final FacturaDetalles facturasDetalles;
    private final ClienteConsultas domConsultasClie;

    public Controlador(Index view, FacturaDetalles facturasDetalles, ClienteConsultas domConsultasClie) {
        this.view = view;
        this.domConsultasClie = domConsultasClie;
        this.facturasDetalles = facturasDetalles;

        //PROVEEDORES
        this.view.provDropdownRazonSocial.addActionListener(this);
        
        // CLIENTES
        this.view.clieTabla.getSelectionModel().addListSelectionListener(this);
        this.view.clieBtnEliminar.addActionListener(this);

        // FACTURAS
        this.view.factBtnVerMas.addActionListener(this);
    }

    public void iniciar() {
        view.setTitle("Gestion de stock");
        view.setLocationRelativeTo(null);

        // iniciar tabla
        ClienteControlador.cargarTabla(view, domConsultasClie);
        
        // Valores de DropDown
        ProveedorControlador.setDropdownOptions(view);
    }

    // BOTONES
    @Override
    public void actionPerformed(ActionEvent lse) {
        // Proveedores
        if(lse.getSource() == view.provDropdownRazonSocial) {
            System.out.println(view.provDropdownRazonSocial.getSelectedItem());
        }
        
        // Clientes
        if (lse.getSource() == view.clieBtnEliminar) {
            ClienteControlador.eliminarCliente(view, domConsultasClie);
        }

        // Facturas
        if (lse.getSource() == view.factBtnVerMas) {
            FacturaControlador.open(facturasDetalles);
        }
    }

    // TABLAS
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        // Clientes
        if (lse.getSource() == view.clieTabla.getSelectionModel()) {
            try {
                // Limitar una sola seleccion
                int row = this.view.clieTabla.getSelectedRow();
                this.view.clieTabla.setRowSelectionInterval(row, row);

                ClienteControlador.seleccionarCliente(
                        view,
                        view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 0).toString(),
                        view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 1).toString(),
                        view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 2).toString(),
                        view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 3).toString(),
                        view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 4).toString(),
                        view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 5).toString(),
                        view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 6).toString()
                );
            } catch (Exception e) {

            }
        }
    }

}

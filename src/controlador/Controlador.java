package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.services.ClienteConsultas;
import vista.FacturaDetalles;
import vista.Index;

public class Controlador implements ActionListener, ListSelectionListener {

    private final Index view;
    private final FacturaDetalles viewDacturasDetalles;
    private final ClienteConsultas domConsultasClie;

    public Controlador(Index view, FacturaDetalles facturasDetalles, ClienteConsultas domConsultasClie) {
        this.view = view;
        this.domConsultasClie = domConsultasClie;
        this.viewDacturasDetalles = facturasDetalles;

        // PROVEEDORES
        this.view.provDropdownRazonSocial.addActionListener(this);

        // CLIENTES
        this.view.clieTabla.getSelectionModel().addListSelectionListener(this);
        this.view.clieBtnBusacar.addActionListener(this);
        this.view.clieBtnEliminar.addActionListener(this);

        // FACTURAS
        this.view.factBtnVerMas.addActionListener(this);
    }

    public void iniciar() {
        this.view.setTitle("Gestion de stock");
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);

        // iniciar tabla
        ClienteControlador.cargarTabla(this.view, this.domConsultasClie);

        // Valores de DropDown
        ProveedorControlador.setDropdownOptions(this.view);
    }

    // BOTONES
    @Override
    public void actionPerformed(ActionEvent lse) {
        // Proveedores
        if (lse.getSource() == this.view.provDropdownRazonSocial) {
            System.out.println(this.view.provDropdownRazonSocial.getSelectedItem());
        }

        // Clientes
        if (lse.getSource() == this.view.clieBtnBusacar) {
            ClienteControlador.buscar(this.view, this.domConsultasClie, this.view.clieInputTextBuscador.getText());
        }
        if (lse.getSource() == this.view.clieBtnEliminar) {
            ClienteControlador.eliminarCliente(this.view, this.domConsultasClie);
        }

        // Facturas
        if (lse.getSource() == this.view.factBtnVerMas) {
            // DIALOG MENSAJE (https://www.youtube.com/watch?v=sT5zeMX8X50&t=205s)
            JOptionPane.showMessageDialog(null, "HOLA MUNDO");
            FacturaControlador.open(this.viewDacturasDetalles);
        }
    }

    // TABLAS
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        // Clientes
        if (lse.getSource() == this.view.clieTabla.getSelectionModel()) {
            try {
                // Limitar una sola seleccion
                int row = this.view.clieTabla.getSelectedRow();
                this.view.clieTabla.setRowSelectionInterval(row, row);

                ClienteControlador.seleccionarCliente(
                        this.view,
                        this.view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 0).toString(),
                        this.view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 1).toString(),
                        this.view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 2).toString(),
                        this.view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 3).toString(),
                        this.view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 4).toString(),
                        this.view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 5).toString(),
                        this.view.clieTabla.getValueAt(this.view.clieTabla.getSelectedRow(), 6).toString()
                );
            } catch (Exception e) {

            }
        }
    }

}

package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.services.ClienteConsultas;
import modelo.services.RubrosConsultas;
import vista.FacturaVista;
import vista.Index;

// diasble button  setEnabled(false);

public class Controlador implements ActionListener, ListSelectionListener {

    private final Index view;
    private final FacturaVista viewDacturasDetalles;
    private final ClienteConsultas domConsultasClie;
    private final RubrosConsultas domConsultasRub;

    public Controlador(Index view, FacturaVista viewFacturasDetalles, ClienteConsultas domConsultasClie, RubrosConsultas domConsultasRub) {
        this.view = view;
        this.domConsultasClie = domConsultasClie;
        this.domConsultasRub = domConsultasRub;
        this.viewDacturasDetalles = viewFacturasDetalles;
        
        // BOTONERA
        this.view.botoneraArt.addActionListener(this);

        // PROVEEDORES
        this.view.provDropdownRazonSocial.addActionListener(this);

        // CLIENTES
        this.view.clieTabla.getSelectionModel().addListSelectionListener(this);
        this.view.clieBtnBusacar.addActionListener(this);
        this.view.clieBtnGuardar.addActionListener(this);
        this.view.clieBtnLimpiar.addActionListener(this);
        this.view.clieBtnEliminar.addActionListener(this);
        
        //RUBROS
        this.view.rubroTabla.getSelectionModel().addListSelectionListener(this);
        this.view.rubBtnBuscar.addActionListener(this);
        this.view.rubBtnGuardar.addActionListener (this);
         this.view.rubBtnLimpiar.addActionListener(this);
         this.view.rubBtnEliminar.addActionListener(this);
         
        // FACTURAS
        this.view.factBtnVerMas.addActionListener(this);
    }

    public void iniciar() {
        this.view.setTitle("Gestion de stock");
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);

        // iniciar tabla
        ClienteControlador.iniciarTabla(this.view.clieTabla, this.domConsultasClie);

        // Valores de DropDown
        ProveedorControlador.setDropdownOptions(this.view);
    }

    // BOTONES
    @Override
    public void actionPerformed(ActionEvent lse) {
        /// BOTONERA
        if (lse.getSource() == this.view.botoneraArt) {

        }
        // Proveedores
        if (lse.getSource() == this.view.provDropdownRazonSocial) {
            System.out.println(this.view.provDropdownRazonSocial.getSelectedItem());
        }

        // Clientes
        if (lse.getSource() == this.view.clieBtnBusacar) {
            ClienteControlador.buscarTabla(this.view.clieTabla, this.domConsultasClie, this.view.clieInputTextBuscador.getText());
        }
        if (lse.getSource() == this.view.clieBtnGuardar) {
            ClienteControlador.agregarCliente(this.view, this.domConsultasClie);
        }
        if (lse.getSource() == this.view.clieBtnLimpiar) {
            ClienteControlador.vaciarInputTexts(view);
        }
        if (lse.getSource() == this.view.clieBtnEliminar) {
            ClienteControlador.eliminarCliente(this.view, this.domConsultasClie);
        }

        
        //Rubros
        if (lse.getSource() == this.view.rubBtnBuscar){
            RubroControlador.buscarTabla(this.view.rubroTabla, this.domConsultasRub, this.view.rubInputTextBuscador.getText());
        }
        if (lse.getSource() == this.view.rubBtnGuardar){
            RubroControlador.agregarRubro(this.view, this.domConsultasRub);
        }
        if (lse.getSource() == this.view.rubBtnLimpiar){
            RubroControlador.vaciarInputTexts(view);
        }
        if (lse.getSource() == this.view.rubBtnEliminar){
            RubroControlador.eliminarRubro(this.view, this.domConsultasRub);
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

                ClienteControlador.cargarInputTexts(
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

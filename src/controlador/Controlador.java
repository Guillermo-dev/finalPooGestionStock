package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.services.ArticuloConsultas;
import modelo.services.ClienteConsultas;
import modelo.services.ProveedorConsultas;
import modelo.services.RubroConsultas;
import vista.FacturaVista;
import vista.Index;
import vista.ListaComprasProveedor;

// diasble button  setEnabled(false);
public class Controlador implements ActionListener, ListSelectionListener {

    private final Index view;
    private final FacturaVista viewFacturasDetalles;
    private final ArticuloConsultas domConsultasArt;
    private final ClienteConsultas domConsultasClie;
    private final RubroConsultas domConsultasRub;
    private final ProveedorConsultas domConsultasProv;
    private final ListaComprasProveedor viewListaProveedores; 

    public Controlador(Index view, FacturaVista viewFacturasDetalles,ListaComprasProveedor viewListaProveedores,
            ArticuloConsultas domConsultasArt, ProveedorConsultas domConsultasProv, ClienteConsultas domConsultasClie, RubroConsultas domConsultasRub) {
        this.view = view;
        this.viewFacturasDetalles = viewFacturasDetalles;
        this.domConsultasArt = domConsultasArt;
        this.domConsultasProv = domConsultasProv;
        this.domConsultasClie = domConsultasClie;
        this.domConsultasRub = domConsultasRub;
        this.viewListaProveedores = viewListaProveedores;

        // BOTONERA
        this.view.botoneraArt.addActionListener(this);
        this.view.botoneraProv.addActionListener(this);
        this.view.botoneraClie.addActionListener(this);
        this.view.botoneraFact.addActionListener(this);
        this.view.botoneraRub.addActionListener(this);

        // ARTICULOS
        this.view.artTabla.getSelectionModel().addListSelectionListener(this);
        this.view.artDropdownRubro.addActionListener(this);
        this.view.artBtnBuscar.addActionListener(this);
        this.view.artBtnGuardar.addActionListener(this);
        this.view.artBtnLimpiar.addActionListener(this);
        this.view.artBtnEliminar.addActionListener(this);

        // PROVEEDORES
        this.view.provTabla.getSelectionModel().addListSelectionListener(this);
        this.view.provDropdownRazonSocial.addActionListener(this);
        this.view.provBtnBuscar.addActionListener(this);
        this.view.provBtnGuardar.addActionListener(this);
        this.view.provBtnLimpiar.addActionListener(this);
        this.view.provBtnEliminar.addActionListener(this);
        this.view.provBtnNuevaFactura.addActionListener(this);
        this.view.provBtnListaCompra.addActionListener(this);
        
        // CLIENTES
        this.view.clieTabla.getSelectionModel().addListSelectionListener(this);
        this.view.clieBtnBuscar.addActionListener(this);
        this.view.clieBtnGuardar.addActionListener(this);
        this.view.clieBtnLimpiar.addActionListener(this);
        this.view.clieBtnEliminar.addActionListener(this);

        //RUBROS
        this.view.rubroTabla.getSelectionModel().addListSelectionListener(this);
        this.view.rubBtnBuscar.addActionListener(this);
        this.view.rubBtnGuardar.addActionListener(this);
        this.view.rubBtnLimpiar.addActionListener(this);
        this.view.rubBtnEliminar.addActionListener(this);

        // FACTURAS
        this.view.factBtnNuevaFactura.addActionListener(this);
        this.view.factBtnVerMas.addActionListener(this);
    }

    public void iniciar() {
        this.view.setTitle("Gestion de stock");
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);

        // iniciar tabla
        ArticuloControlador.iniciarTabla(this.view.artTabla, domConsultasArt);
        // Valores de DropDown
        ArticuloControlador.iniciarDropdownRubros(this.view, this.domConsultasRub);
        ArticuloControlador.iniciarDropdownProveedores(this.view, this.domConsultasProv);

        ProveedorControlador.iniciarDropdownRazonSocial(this.view);
    }

    // BOTONES
    @Override
    public void actionPerformed(ActionEvent lse) {
        /// BOTONERA
        if (lse.getSource() == this.view.botoneraArt) {
            this.view.ventanasContainer.setSelectedComponent(this.view.art);
            ArticuloControlador.iniciarTabla(this.view.artTabla, domConsultasArt);
            ArticuloControlador.iniciarDropdownRubros(this.view, this.domConsultasRub);
            ArticuloControlador.iniciarDropdownProveedores(this.view, this.domConsultasProv);
        }
        if (lse.getSource() == this.view.botoneraProv) {
            this.view.ventanasContainer.setSelectedComponent(this.view.prov);
        }
        if (lse.getSource() == this.view.botoneraClie) {
            this.view.ventanasContainer.setSelectedComponent(this.view.clie);
            ClienteControlador.iniciarTabla(this.view.clieTabla, this.domConsultasClie);
        }
        if (lse.getSource() == this.view.botoneraFact) {
            this.view.ventanasContainer.setSelectedComponent(this.view.rub);
        }
        if (lse.getSource() == this.view.botoneraRub) {
            this.view.ventanasContainer.setSelectedComponent(this.view.fact);
        }

        // ARTICULOS
        if (lse.getSource() == this.view.artDropdownRubro) {

        }
        if (lse.getSource() == this.view.artBtnGuardar) {
            ArticuloControlador.guardarArticulo(this.view, this.domConsultasArt, this.domConsultasRub, this.domConsultasProv);
        }
        if (lse.getSource() == this.view.artBtnEliminar) {
            ArticuloControlador.eliminarArticulo(this.view, this.domConsultasArt);
        }

        // Proveedores
        if (lse.getSource() == this.view.provDropdownRazonSocial) {
            System.out.println(this.view.provDropdownRazonSocial.getSelectedItem());
        }

        if (lse.getSource() == this.view.provBtnBuscar) {
            ProveedorControlador.buscarTabla(this.view.provTabla, this.domConsultasProv, this.view.provInputTextBuscador.getText());
        }

        if (lse.getSource() == this.view.provBtnGuardar) {
            ProveedorControlador.guardarProveedor(this.view, this.domConsultasProv);
        }
        if (lse.getSource() == this.view.clieBtnLimpiar) {
            ProveedorControlador.vaciarInputTexts(view);
        }
        if (lse.getSource() == this.view.clieBtnEliminar) {
            ProveedorControlador.eliminarProveedor(this.view, this.domConsultasProv);
        }

        //BotonCargarFactura  --ESTA BIEN?--
         if (lse.getSource() == this.view.provBtnNuevaFactura) {
            FacturaControlador.crearFactura(this.viewFacturasDetalles);
        }
        
        //BotonListaCompra 
        if (lse.getSource() == this.view.provBtnListaCompra) {
            ListaProvControlador.listarProveedores(this.viewListaProveedores);
        }

       
        // Clientes
        if (lse.getSource() == this.view.clieBtnBuscar) {
            ClienteControlador.buscarTabla(this.view.clieTabla, this.domConsultasClie, this.view.clieInputTextBuscador.getText());
        }
        if (lse.getSource() == this.view.clieBtnGuardar) {
            ClienteControlador.guardarCliente(this.view, this.domConsultasClie);
        }
        if (lse.getSource() == this.view.clieBtnLimpiar) {
            ClienteControlador.vaciarInputTexts(view);
        }
        if (lse.getSource() == this.view.clieBtnEliminar) {
            ClienteControlador.eliminarCliente(this.view, this.domConsultasClie);
        }

        //Rubros
        if (lse.getSource() == this.view.rubBtnBuscar) {
            RubroControlador.buscarTabla(this.view.rubroTabla, this.domConsultasRub, this.view.rubInputTextBuscador.getText());
        }
        if (lse.getSource() == this.view.rubBtnGuardar) {
            RubroControlador.agregarRubro(this.view, this.domConsultasRub);
        }
        if (lse.getSource() == this.view.rubBtnLimpiar) {
            RubroControlador.vaciarInputTexts(view);
        }
        if (lse.getSource() == this.view.rubBtnEliminar) {
            RubroControlador.eliminarRubro(this.view, this.domConsultasRub);
        }

        // Facturas
        if (lse.getSource() == this.view.factBtnNuevaFactura) {
            FacturaControlador.crearFactura(this.viewFacturasDetalles);
        }
    }

    // TABLAS
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        // Articulos
        if (lse.getSource() == this.view.artTabla.getSelectionModel()) {
            try {
                int row = this.view.artTabla.getSelectedRow();
                this.view.artTabla.setRowSelectionInterval(row, row);

                ArticuloControlador.cargarInputTexts(
                        this.view,
                        this.view.artTabla.getValueAt(this.view.artTabla.getSelectedRow(), 0).toString(),
                        this.view.artTabla.getValueAt(this.view.artTabla.getSelectedRow(), 1).toString(),
                        this.view.artTabla.getValueAt(this.view.artTabla.getSelectedRow(), 2).toString(),
                        this.view.artTabla.getValueAt(this.view.artTabla.getSelectedRow(), 3).toString(),
                        this.view.artTabla.getValueAt(this.view.artTabla.getSelectedRow(), 4).toString(),
                        this.view.artTabla.getValueAt(this.view.artTabla.getSelectedRow(), 5).toString(),
                        this.view.artTabla.getValueAt(this.view.artTabla.getSelectedRow(), 6).toString(),
                        this.view.artTabla.getValueAt(this.view.artTabla.getSelectedRow(), 7).toString()
                );
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        // Clientes
        if (lse.getSource() == this.view.clieTabla.getSelectionModel()) {
            try {
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

        // Proveedores
        if (lse.getSource() == this.view.provTabla.getSelectionModel()) {
            try {
                int row = this.view.provTabla.getSelectedRow();
                this.view.provTabla.setRowSelectionInterval(row, row);

                ClienteControlador.cargarInputTexts(
                        this.view,
                        this.view.provTabla.getValueAt(this.view.provTabla.getSelectedRow(), 0).toString(),
                        this.view.provTabla.getValueAt(this.view.provTabla.getSelectedRow(), 1).toString(),
                        this.view.provTabla.getValueAt(this.view.provTabla.getSelectedRow(), 2).toString(),
                        this.view.provTabla.getValueAt(this.view.provTabla.getSelectedRow(), 3).toString(),
                        this.view.provTabla.getValueAt(this.view.provTabla.getSelectedRow(), 4).toString(),
                        this.view.provTabla.getValueAt(this.view.provTabla.getSelectedRow(), 5).toString(),
                        this.view.provTabla.getValueAt(this.view.provTabla.getSelectedRow(), 6).toString()
                );
            } catch (Exception e) {

            }
        }

    }
}

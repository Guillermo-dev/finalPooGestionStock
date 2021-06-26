package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.services.ArticuloConsultas;
import modelo.services.ClienteConsultas;
import modelo.services.FacturaConsultas;
import modelo.services.ProveedorConsultas;
import modelo.services.RubroConsultas;
import vista.FacturaVistaCompra;
import vista.FacturaVistaVenta;
import vista.Index;
import vista.ListaComprasProveedor;

public class Controlador implements ActionListener, ListSelectionListener, ChangeListener {

    private final Index view;
    private final FacturaVistaVenta viewFacturasDetallesVenta;
    private final FacturaVistaCompra viewFacturasDetallesCompra;
    private final ArticuloConsultas domConsultasArt;
    private final ClienteConsultas domConsultasClie;
    private final RubroConsultas domConsultasRub;
    private final ProveedorConsultas domConsultasProv;
    private final ListaComprasProveedor viewListaProveedores;
    private final FacturaConsultas domConsultasFact;

    public Controlador(Index view, FacturaVistaVenta viewFacturasDetallesVenta, FacturaVistaCompra viewFacturasDetallesCompra, ListaComprasProveedor viewListaProveedores,
            ArticuloConsultas domConsultasArt, ProveedorConsultas domConsultasProv, ClienteConsultas domConsultasClie, RubroConsultas domConsultasRub, FacturaConsultas domConsultasFact) {
        this.view = view;
        this.viewFacturasDetallesVenta = viewFacturasDetallesVenta;
        this.viewFacturasDetallesCompra = viewFacturasDetallesCompra;
        this.domConsultasArt = domConsultasArt;
        this.domConsultasProv = domConsultasProv;
        this.domConsultasClie = domConsultasClie;
        this.domConsultasRub = domConsultasRub;
        this.viewListaProveedores = viewListaProveedores;
        this.domConsultasFact = domConsultasFact;

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
        this.view.artCheckBoxStockMinimo.addActionListener(this);
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

        // LISTA DE COMPRA
        this.viewListaProveedores.listaProvTabla.getSelectionModel().addListSelectionListener(this);
        this.viewListaProveedores.listaProvBtnAceptar.addActionListener(this);
        this.viewListaProveedores.listaProvImputTextProv.addActionListener(this);

        // CLIENTES
        this.view.clieTabla.getSelectionModel().addListSelectionListener(this);
        this.view.clieBtnBuscar.addActionListener(this);
        this.view.clieBtnGuardar.addActionListener(this);
        this.view.clieBtnLimpiar.addActionListener(this);
        this.view.clieBtnEliminar.addActionListener(this);

        //RUBROS
        this.view.rubTabla.getSelectionModel().addListSelectionListener(this);
        this.view.rubBtnBuscar.addActionListener(this);
        this.view.rubBtnGuardar.addActionListener(this);
        this.view.rubBtnLimpiar.addActionListener(this);
        this.view.rubBtnEliminar.addActionListener(this);

        // FACTURAS
        this.view.factBtnNuevaFactura.addActionListener(this);
        this.view.factBtnVerMas.addActionListener(this);
        // FACTURAVENTA
        this.viewFacturasDetallesVenta.tabla.getSelectionModel().addListSelectionListener(this);
        this.viewFacturasDetallesVenta.dropdownArticulo.addActionListener(this);
        this.viewFacturasDetallesVenta.btnAgregarProducto.addActionListener(this);
        this.viewFacturasDetallesVenta.btnBorrarProducto.addActionListener(this);
        this.viewFacturasDetallesVenta.btnGuardar.addActionListener(this);
        this.viewFacturasDetallesVenta.btnImprimir.addActionListener(this);
        this.viewFacturasDetallesVenta.spinnerCantidad.addChangeListener(this);
        // FACTURACOMPRA
        this.viewFacturasDetallesCompra.tabla.getSelectionModel().addListSelectionListener(this);
        this.viewFacturasDetallesCompra.dropdownArticulo.addActionListener(this);
        this.viewFacturasDetallesCompra.dropdownProveedor.addActionListener(this);
        this.viewFacturasDetallesCompra.btnAgregarProducto.addActionListener(this);
        this.viewFacturasDetallesCompra.btnBorrarProducto.addActionListener(this);
        this.viewFacturasDetallesCompra.btnGuardar.addActionListener(this);
        this.viewFacturasDetallesCompra.spinnerCantidad.addChangeListener(this);

        //NOTAS DE CREDITO
    }

    public void iniciar() {
        this.view.setTitle("Gestion de stock");
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);

        ArticuloControlador.iniciarTabla(this.view.artTabla, this.domConsultasArt);
        ArticuloControlador.iniciarDropdownRubros(this.view, this.domConsultasRub);
        ArticuloControlador.iniciarDropdownProveedores(this.view, this.domConsultasProv);
    }

    public void goToArticulos() {
        this.view.ventanasContainer.setSelectedComponent(this.view.art);
        ArticuloControlador.iniciarTabla(this.view.artTabla, this.domConsultasArt);
        ArticuloControlador.iniciarDropdownRubros(this.view, this.domConsultasRub);
        ArticuloControlador.iniciarDropdownProveedores(this.view, this.domConsultasProv);
    }

    public void goToProveedores() {
        this.view.ventanasContainer.setSelectedComponent(this.view.prov);
        ProveedorControlador.iniciarTabla(this.view.rubTabla, this.domConsultasProv);
        ProveedorControlador.iniciarDropdownRazonSocial(this.view);
    }

    public void goToClientes() {
        this.view.ventanasContainer.setSelectedComponent(this.view.clie);
        ClienteControlador.iniciarTabla(this.view.clieTabla, this.domConsultasClie);
    }

    public void goToFacturas() {
        this.view.ventanasContainer.setSelectedComponent(this.view.fact);
        FacturaControlador.iniciarTabla(this.view.factTabla, this.domConsultasFact);

    }

    public void goToRubros() {
        this.view.ventanasContainer.setSelectedComponent(this.view.rub);
        RubroControlador.iniciarTabla(this.view.rubTabla, this.domConsultasRub);
    }

    // BOTONES
    @Override
    public void actionPerformed(ActionEvent lse) {
        /// BOTONERA
        if (lse.getSource() == this.view.botoneraArt) {
            goToArticulos();
        }
        if (lse.getSource() == this.view.botoneraProv) {
            goToProveedores();
        }
        if (lse.getSource() == this.view.botoneraClie) {
            goToClientes();
        }
        if (lse.getSource() == this.view.botoneraFact) {
            goToFacturas();
        }
        if (lse.getSource() == this.view.botoneraRub) {
            goToRubros();
        }

        // ARTICULOS
        if (lse.getSource() == this.view.artBtnGuardar) {
            ArticuloControlador.guardarArticulo(this.view, this.domConsultasArt, this.domConsultasRub, this.domConsultasProv);
        }
        if (lse.getSource() == this.view.artCheckBoxStockMinimo) {
            ArticuloControlador.stockMinimoTabla(this.view.artTabla, this.view.artCheckBoxStockMinimo, this.domConsultasArt);
        }
        if (lse.getSource() == this.view.artBtnLimpiar) {
            ArticuloControlador.vaciarInputTexts(view);
        }
        if (lse.getSource() == this.view.artBtnEliminar) {
            ArticuloControlador.eliminarArticulo(this.view, this.domConsultasArt);
        }
        if (lse.getSource() == this.view.artDropdownRubro) {
            if (ArticuloControlador.nuevoRubroSeleccionado(this.view)) {
                goToRubros();
            }
        }

        // PROVEEDORES
        if (lse.getSource() == this.view.provDropdownRazonSocial) {
            System.out.println(this.view.provDropdownRazonSocial.getSelectedItem());
        }

        if (lse.getSource() == this.view.provBtnBuscar) {
            ProveedorControlador.buscarTabla(this.view.provTabla, this.domConsultasProv, this.view.provInputTextBuscador.getText());
        }

        if (lse.getSource() == this.view.provBtnGuardar) {
            ProveedorControlador.guardarProveedor(this.view, this.domConsultasProv);
        }
        if (lse.getSource() == this.view.provBtnLimpiar) {
            ProveedorControlador.vaciarInputTexts(view);
        }
        if (lse.getSource() == this.view.provBtnEliminar) {
            ProveedorControlador.eliminarProveedor(this.view, this.domConsultasProv);
        }

        //TODO otro metodo con distintos parametros
        if (lse.getSource() == this.view.provBtnNuevaFactura) {
            // FacturaControlador.abrirNuevaFactura(this.viewFacturasDetallesVenta, this.viewFacturasDetallesCompra);
        }

        // LISTA DE COMPRAS 
        if (lse.getSource() == this.view.provBtnListaCompra) {
            ListaProvControlador.listarProveedores(this.viewListaProveedores);
        }

        // CLIENTES
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

        // RUBROS
        if (lse.getSource() == this.view.rubBtnBuscar) {
            RubroControlador.buscarTabla(this.view.rubTabla, this.domConsultasRub, this.view.rubInputTextBuscador.getText());
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

        // FACTURAS
        if (lse.getSource() == this.view.factBtnNuevaFactura) {
            FacturaControlador.abrirNuevaFactura(this.viewFacturasDetallesVenta, this.viewFacturasDetallesCompra, this.domConsultasArt, this.domConsultasProv, this.domConsultasClie, this.domConsultasFact, this.domConsultasRub);
        }

        // VISTA FACTURA VENTA          
        if (lse.getSource() == this.viewFacturasDetallesVenta.dropdownArticulo) {
            FacturaVentaControlador.seleccionarArticulo(this.viewFacturasDetallesVenta.dropdownArticulo, this.viewFacturasDetallesVenta.inputTextPrecio, this.viewFacturasDetallesVenta.spinnerCantidad, this.domConsultasArt);
        }
        if (lse.getSource() == this.viewFacturasDetallesVenta.btnAgregarProducto) {
            FacturaVentaControlador.agregarArticulo(this.viewFacturasDetallesVenta);
        }
        if (lse.getSource() == this.viewFacturasDetallesVenta.btnBorrarProducto) {
            FacturaVentaControlador.quitarArticulo(this.viewFacturasDetallesVenta);
        }
        if (lse.getSource() == this.viewFacturasDetallesVenta.btnGuardar) {
            FacturaVentaControlador.guardarFactura(this.viewFacturasDetallesVenta, domConsultasArt, domConsultasClie, domConsultasFact);
        }
        // VISTA FACTURA COMPRA 
        if (lse.getSource() == this.viewFacturasDetallesCompra.dropdownArticulo) {
            FacturaCompraControlador.seleccionarArticulo(this.viewFacturasDetallesCompra, this.domConsultasArt);
        }
        if (lse.getSource() == this.viewFacturasDetallesCompra.dropdownProveedor) {
            FacturaCompraControlador.agregarArticulosDropdown(this.viewFacturasDetallesCompra, this.domConsultasArt);
        }
        if (lse.getSource() == this.viewFacturasDetallesCompra.btnAgregarProducto) {
            FacturaCompraControlador.agregarArticulo(this.viewFacturasDetallesCompra);
        }
        if (lse.getSource() == this.viewFacturasDetallesCompra.btnBorrarProducto) {
            FacturaCompraControlador.quitarArticulo(this.viewFacturasDetallesCompra);
        }
        if (lse.getSource() == this.viewFacturasDetallesCompra.btnGuardar) {
            FacturaCompraControlador.guardarFactura(this.viewFacturasDetallesCompra, this.domConsultasArt, this.domConsultasProv, this.domConsultasFact, this.domConsultasRub);
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
                        this.view.artTabla.getValueAt(row, 0).toString(),
                        this.view.artTabla.getValueAt(row, 1).toString(),
                        this.view.artTabla.getValueAt(row, 2).toString(),
                        this.view.artTabla.getValueAt(row, 3).toString(),
                        this.view.artTabla.getValueAt(row, 4).toString(),
                        this.view.artTabla.getValueAt(row, 5).toString(),
                        this.view.artTabla.getValueAt(row, 6).toString(),
                        this.view.artTabla.getValueAt(row, 7).toString()
                );
            } catch (Exception e) {
            }
        }

        // Clientes
        if (lse.getSource() == this.view.clieTabla.getSelectionModel()) {
            try {
                int row = this.view.clieTabla.getSelectedRow();
                this.view.clieTabla.setRowSelectionInterval(row, row);

                ClienteControlador.cargarInputTexts(
                        this.view,
                        this.view.clieTabla.getValueAt(row, 0).toString(),
                        this.view.clieTabla.getValueAt(row, 1).toString(),
                        this.view.clieTabla.getValueAt(row, 2).toString(),
                        this.view.clieTabla.getValueAt(row, 3).toString(),
                        this.view.clieTabla.getValueAt(row, 4).toString(),
                        this.view.clieTabla.getValueAt(row, 5).toString(),
                        this.view.clieTabla.getValueAt(row, 6).toString()
                );
            } catch (Exception e) {
            }
        }

        // Proveedores
        if (lse.getSource() == this.view.provTabla.getSelectionModel()) {
            try {
                int row = this.view.provTabla.getSelectedRow();
                this.view.provTabla.setRowSelectionInterval(row, row);

                ProveedorControlador.cargarInputTexts(
                        this.view,
                        this.view.provTabla.getValueAt(row, 0).toString(),
                        this.view.provTabla.getValueAt(row, 1).toString(),
                        this.view.provTabla.getValueAt(row, 2).toString(),
                        this.view.provTabla.getValueAt(row, 3).toString(),
                        this.view.provTabla.getValueAt(row, 4).toString(),
                        this.view.provTabla.getValueAt(row, 5).toString(),
                        this.view.provTabla.getValueAt(row, 6).toString()
                );
            } catch (Exception e) {
            }
        }
        // Rubros
        if (lse.getSource() == this.view.rubTabla.getSelectionModel()) {
            try {
                int row = this.view.rubTabla.getSelectedRow();
                this.view.rubTabla.setRowSelectionInterval(row, row);

                RubroControlador.cargarInputTexts(
                        this.view,
                        this.view.rubTabla.getValueAt(row, 0).toString(),
                        this.view.rubTabla.getValueAt(row, 1).toString(),
                        this.view.rubTabla.getValueAt(row, 2).toString()
                );
            } catch (Exception e) {

            }
        }

        // FACTURAS
        // VISTA DE FACTURA DE VENTA
        if (lse.getSource() == this.viewFacturasDetallesVenta.tabla.getSelectionModel()) {
            try {
                int row = this.viewFacturasDetallesVenta.tabla.getSelectedRow();
                this.viewFacturasDetallesVenta.tabla.setRowSelectionInterval(row, row);

                FacturaVentaControlador.cargarInputTexts(
                        this.viewFacturasDetallesVenta,
                        this.viewFacturasDetallesVenta.tabla.getValueAt(row, 1).toString(),
                        this.viewFacturasDetallesVenta.tabla.getValueAt(row, 2).toString(),
                        this.viewFacturasDetallesVenta.tabla.getValueAt(row, 3).toString()
                );
            } catch (Exception e) {
            }
        }
        // VISTA DE FACTURA DE COMPRA
        if (lse.getSource() == this.viewFacturasDetallesCompra.tabla.getSelectionModel()) {
            try {
                int row = this.viewFacturasDetallesCompra.tabla.getSelectedRow();
                this.viewFacturasDetallesCompra.tabla.setRowSelectionInterval(row, row);

                FacturaCompraControlador.cargarInputTexts(
                        this.viewFacturasDetallesCompra,
                        this.viewFacturasDetallesCompra.tabla.getValueAt(row, 1).toString(),
                        this.viewFacturasDetallesCompra.tabla.getValueAt(row, 2).toString(),
                        this.viewFacturasDetallesCompra.tabla.getValueAt(row, 3).toString(),
                        this.viewFacturasDetallesCompra.tabla.getValueAt(row, 4).toString(),
                        this.viewFacturasDetallesCompra.tabla.getValueAt(row, 5).toString(),
                        this.viewFacturasDetallesCompra.tabla.getValueAt(row, 6).toString()
                );
            } catch (Exception e) {
            }
        }
    }

    // SPINENR
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.viewFacturasDetallesVenta.spinnerCantidad) {
            FacturaVentaControlador.noNegativosSpinner(this.viewFacturasDetallesVenta.spinnerCantidad);
        }
        if (e.getSource() == this.viewFacturasDetallesCompra.spinnerCantidad) {
            FacturaCompraControlador.noNegativosSpinner(this.viewFacturasDetallesCompra.spinnerCantidad);
        }
    }
}

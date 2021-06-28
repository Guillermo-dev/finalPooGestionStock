package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import vista.FacturaVistaCompra;
import vista.FacturaVistaVenta;
import vista.Index;
import vista.ListaComprasProveedor;

public class Controlador implements ActionListener, ListSelectionListener, ChangeListener {

    private final Index view;
    private final FacturaVistaVenta viewFacturasDetallesVenta;
    private final FacturaVistaCompra viewFacturasDetallesCompra;
    private final ListaComprasProveedor viewListaProveedores;

    public Controlador(Index view, FacturaVistaVenta viewFacturasDetallesVenta, FacturaVistaCompra viewFacturasDetallesCompra, ListaComprasProveedor viewListaProveedores) {
        this.view = view;
        this.viewFacturasDetallesVenta = viewFacturasDetallesVenta;
        this.viewFacturasDetallesCompra = viewFacturasDetallesCompra;
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
        this.view.artCheckBoxStockMinimo.addActionListener(this);
        this.view.artBtnGuardar.addActionListener(this);
        this.view.artBtnLimpiar.addActionListener(this);
        this.view.artBtnEliminar.addActionListener(this);

        // PROVEEDORES
        this.view.provTabla.getSelectionModel().addListSelectionListener(this);
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
        this.view.rubTabla.getSelectionModel().addListSelectionListener(this);
        this.view.rubBtnBuscar.addActionListener(this);
        this.view.rubBtnGuardar.addActionListener(this);
        this.view.rubBtnLimpiar.addActionListener(this);
        this.view.rubBtnEliminar.addActionListener(this);

        // FACTURAS
        this.view.factTabla.getSelectionModel().addListSelectionListener(this);
        this.view.factBtnFacturas.addActionListener(this);
        this.view.factBtnBuscar.addActionListener(this);
        this.view.factCheckBoxCompra.addActionListener(this);
        this.view.factCheckBoxVenta.addActionListener(this);
        this.view.factBtnLimpiar.addActionListener(this);
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
        this.view.notTabla.getSelectionModel().addListSelectionListener(this);
        this.view.notBtnNotasCredito.addActionListener(this);
        this.view.notBtnBuscar.addActionListener(this);
        this.view.notDropDownFactura.addActionListener(this);
        this.view.notBtnGuardar.addActionListener(this);
        this.view.notBtnLimpiar.addActionListener(this);
        this.view.notBtnVerMas.addActionListener(this);

        // LISTA DE COMPRA
        this.viewListaProveedores.btnAceptar.addActionListener(this);
    }

    public void iniciar() {
        this.view.setTitle("Gestion de stock");
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);

        ArticuloControlador.iniciarTabla(this.view);
        ArticuloControlador.iniciarDropdownRubros(this.view);
        ArticuloControlador.iniciarDropdownProveedores(this.view);
    }

    public void goToArticulos() {
        this.view.ventanasContainer.setSelectedComponent(this.view.art);
        ArticuloControlador.iniciarTabla(this.view);
        ArticuloControlador.iniciarDropdownRubros(this.view);
        ArticuloControlador.iniciarDropdownProveedores(this.view);
    }

    public void goToProveedores() {
        this.view.ventanasContainer.setSelectedComponent(this.view.prov);
        ProveedorControlador.iniciarTabla(this.view);
        ProveedorControlador.iniciarDropdownRazonSocial(this.view);
    }

    public void goToClientes() {
        this.view.ventanasContainer.setSelectedComponent(this.view.clie);
        ClienteControlador.iniciarTabla(this.view);
    }

    public void goToFacturas() {
        this.view.ventanasContainer.setSelectedComponent(this.view.fact);
        FacturaControlador.iniciarTabla(this.view);

    }

    public void goToRubros() {
        this.view.ventanasContainer.setSelectedComponent(this.view.rub);
        RubroControlador.iniciarTabla(this.view);
    }

    
    public void goToFacturasContainer() {
        this.view.facturasContainer.setSelectedComponent(this.view.panelFactura);
        FacturaControlador.iniciarTabla(this.view);
    }

    public void goToNotasContainer() {
        this.view.facturasContainer.setSelectedComponent(this.view.panelNotasC);
        NotasDeCreditoControlador.iniciarDropdownFacturas(this.view);
        NotasDeCreditoControlador.iniciarTabla(this.view);
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
        if (lse.getSource() == this.view.artBtnBuscar) {
            ArticuloControlador.buscarTabla(this.view);
        }
        if (lse.getSource() == this.view.artBtnGuardar) {
            ArticuloControlador.guardarArticulo(this.view);
        }
        if (lse.getSource() == this.view.artCheckBoxStockMinimo) {
            ArticuloControlador.stockMinimoTabla(this.view);
        }
        if (lse.getSource() == this.view.artBtnLimpiar) {
            ArticuloControlador.vaciarInputTexts(this.view);
        }
        if (lse.getSource() == this.view.artBtnEliminar) {
            ArticuloControlador.eliminarArticulo(this.view);
        }
        if (lse.getSource() == this.view.artDropdownRubro) {
            if (ArticuloControlador.nuevoRubroSeleccionado(this.view)) {
                goToRubros();
            }
        }

        // PROVEEDORES
        if (lse.getSource() == this.view.provBtnBuscar) {
            ProveedorControlador.buscarTabla(this.view);
        }

        if (lse.getSource() == this.view.provBtnGuardar) {
            ProveedorControlador.guardarProveedor(this.view);
        }
        if (lse.getSource() == this.view.provBtnLimpiar) {
            ProveedorControlador.vaciarInputTexts(this.view);
        }
        if (lse.getSource() == this.view.provBtnEliminar) {
            ProveedorControlador.eliminarProveedor(this.view);
        }
        if (lse.getSource() == this.view.provBtnNuevaFactura) {
            FacturaCompraControlador.abrirVistaFacturaCompra(this.viewFacturasDetallesCompra);
        }
        if (lse.getSource() == this.view.provBtnListaCompra) {
            ProveedorControlador.abrirListaCompra(this.view, this.viewListaProveedores);
        }

        // CLIENTES
        if (lse.getSource() == this.view.clieBtnBuscar) {
            ClienteControlador.buscarTabla(this.view);
        }
        if (lse.getSource() == this.view.clieBtnGuardar) {
            ClienteControlador.guardarCliente(this.view);
        }
        if (lse.getSource() == this.view.clieBtnLimpiar) {
            ClienteControlador.vaciarInputTexts(this.view);
        }
        if (lse.getSource() == this.view.clieBtnEliminar) {
            ClienteControlador.eliminarCliente(this.view);
        }

        // RUBROS
        if (lse.getSource() == this.view.rubBtnBuscar) {
            RubroControlador.buscarTabla(this.view);
        }
        if (lse.getSource() == this.view.rubBtnGuardar) {
            RubroControlador.agregarRubro(this.view);
        }
        if (lse.getSource() == this.view.rubBtnLimpiar) {
            RubroControlador.vaciarInputTexts(this.view);
        }
        if (lse.getSource() == this.view.rubBtnEliminar) {
            RubroControlador.eliminarRubro(this.view);
        }

        // FACTURAS
        if (lse.getSource() == this.view.factBtnFacturas) {
            goToFacturasContainer();
        }
        if (lse.getSource() == this.view.factBtnBuscar) {
            FacturaControlador.buscarTabla(this.view);
        }
        if (lse.getSource() == this.view.factBtnNuevaFactura) {
            FacturaControlador.abrirNuevaFactura(this.viewFacturasDetallesVenta, this.viewFacturasDetallesCompra);
        }
        if (lse.getSource() == this.view.factBtnVerMas) {
            FacturaControlador.abrirDetallesFactura(this.view, this.viewFacturasDetallesVenta, this.viewFacturasDetallesCompra);
        }
        if (lse.getSource() == this.view.factBtnLimpiar) {
            FacturaControlador.vaciarInputTexts(this.view);
        }
        if (lse.getSource() == this.view.factCheckBoxCompra) {
            FacturaControlador.iniciarTabla(this.view);
        }
        if (lse.getSource() == this.view.factCheckBoxVenta) {
            FacturaControlador.iniciarTabla(this.view);
        }

        // VISTA FACTURA VENTA          
        if (lse.getSource() == this.viewFacturasDetallesVenta.dropdownArticulo) {
            FacturaVentaControlador.seleccionarArticulo(this.viewFacturasDetallesVenta);
        }
        if (lse.getSource() == this.viewFacturasDetallesVenta.btnAgregarProducto) {
            FacturaVentaControlador.agregarArticulo(this.viewFacturasDetallesVenta);
        }
        if (lse.getSource() == this.viewFacturasDetallesVenta.btnBorrarProducto) {
            FacturaVentaControlador.quitarArticulo(this.viewFacturasDetallesVenta);
        }
        if (lse.getSource() == this.viewFacturasDetallesVenta.btnGuardar) {
            FacturaVentaControlador.guardarFactura(this.view, this.viewFacturasDetallesVenta);
        }
        if (lse.getSource() == this.viewFacturasDetallesVenta.btnImprimir) {
            FacturaVentaControlador.imprimirFactura(this.viewFacturasDetallesVenta);
        }
        // VISTA FACTURA COMPRA 
        if (lse.getSource() == this.viewFacturasDetallesCompra.dropdownArticulo) {
            FacturaCompraControlador.seleccionarArticulo(this.viewFacturasDetallesCompra);
        }
        if (lse.getSource() == this.viewFacturasDetallesCompra.dropdownProveedor) {
            FacturaCompraControlador.agregarArticulosDropdown(this.viewFacturasDetallesCompra);
        }
        if (lse.getSource() == this.viewFacturasDetallesCompra.btnAgregarProducto) {
            FacturaCompraControlador.agregarArticulo(this.viewFacturasDetallesCompra);
        }
        if (lse.getSource() == this.viewFacturasDetallesCompra.btnBorrarProducto) {
            FacturaCompraControlador.quitarArticulo(this.viewFacturasDetallesCompra);
        }
        if (lse.getSource() == this.viewFacturasDetallesCompra.btnGuardar) {
            FacturaCompraControlador.guardarFactura(this.view, this.viewFacturasDetallesCompra);
        }

        // NOTAS DE CREDITO
        if (lse.getSource() == this.view.notBtnNotasCredito) {
            goToNotasContainer();
        }
        if (lse.getSource() == this.view.notDropDownFactura) {
            NotasDeCreditoControlador.cargarDatosFactura(this.view);
        }
        if(lse.getSource() == this.view.notBtnBuscar) {
            NotasDeCreditoControlador.buscarTabla(this.view);
        }
        if (lse.getSource() == this.view.notBtnVerMas) {
            NotasDeCreditoControlador.abrirDetallesFactura(this.view, this.viewFacturasDetallesVenta);
        }
        if (lse.getSource() == this.view.notBtnGuardar) {
            NotasDeCreditoControlador.crearNotaCredito(this.view);
        }
        if (lse.getSource() == this.view.notBtnLimpiar) {
            NotasDeCreditoControlador.vaciarInputsTexts(this.view);
        }

        // LISTA DE COMPRAS 
        if (lse.getSource() == this.viewListaProveedores.btnAceptar) {
            ListaProvControlador.cerrarListaCompra(this.viewListaProveedores);
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

                ArticuloControlador.cargarInputTexts(this.view);
            } catch (Exception e) {
            }
        }

        // Clientes
        if (lse.getSource() == this.view.clieTabla.getSelectionModel()) {
            try {
                int row = this.view.clieTabla.getSelectedRow();
                this.view.clieTabla.setRowSelectionInterval(row, row);

                ClienteControlador.cargarInputTexts(this.view);
            } catch (Exception e) {
            }
        }

        // Proveedores
        if (lse.getSource() == this.view.provTabla.getSelectionModel()) {
            try {
                int row = this.view.provTabla.getSelectedRow();
                this.view.provTabla.setRowSelectionInterval(row, row);

                ProveedorControlador.cargarInputTexts(this.view);
            } catch (Exception e) {
            }
        }
        // Rubros
        if (lse.getSource() == this.view.rubTabla.getSelectionModel()) {
            try {
                int row = this.view.rubTabla.getSelectedRow();
                this.view.rubTabla.setRowSelectionInterval(row, row);

                RubroControlador.cargarInputTexts(this.view);
            } catch (Exception e) {
            }
        }

        // FACTURAS
        if (lse.getSource() == this.view.factTabla.getSelectionModel()) {
            try {
                int row = this.view.factTabla.getSelectedRow();
                this.view.factTabla.setRowSelectionInterval(row, row);

                FacturaControlador.cargarInputTexts(this.view);
            } catch (Exception e) {
            }
        }

        // VISTA DE FACTURA DE VENTA
        if (lse.getSource() == this.viewFacturasDetallesVenta.tabla.getSelectionModel()) {
            try {
                int row = this.viewFacturasDetallesVenta.tabla.getSelectedRow();
                this.viewFacturasDetallesVenta.tabla.setRowSelectionInterval(row, row);

                FacturaVentaControlador.cargarInputTexts(this.viewFacturasDetallesVenta);
            } catch (Exception e) {
            }
        }
        // VISTA DE FACTURA DE COMPRA
        if (lse.getSource() == this.viewFacturasDetallesCompra.tabla.getSelectionModel()) {
            try {
                int row = this.viewFacturasDetallesCompra.tabla.getSelectedRow();
                this.viewFacturasDetallesCompra.tabla.setRowSelectionInterval(row, row);

                FacturaCompraControlador.cargarInputTexts(this.viewFacturasDetallesCompra);
            } catch (Exception e) {
            }
        }

        // NOTAS DE CREDITO 
        if (lse.getSource() == this.view.notTabla.getSelectionModel()) {
            try {
                int row = this.view.notTabla.getSelectedRow();
                this.view.notTabla.setRowSelectionInterval(row, row);

                NotasDeCreditoControlador.cargarInputsTexts(this.view);
            } catch (Exception e) {
            }
        }
    }

    // SPINENR
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.viewFacturasDetallesVenta.spinnerCantidad) {
            FacturaVentaControlador.noNegativosSpinner(this.viewFacturasDetallesVenta);
        }
        if (e.getSource() == this.viewFacturasDetallesCompra.spinnerCantidad) {
            FacturaCompraControlador.noNegativosSpinner(this.viewFacturasDetallesCompra);
        }
    }
}

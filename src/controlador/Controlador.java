package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import vista.Index;

public class Controlador implements ActionListener {

    private Index view;

    public Controlador(Index view /*Modelo model*/) {
        this.view = view;
        //Modelo

        //Se declaran todos los botones o posibles actions de la misma forma
        ListSelectionModel model = view.TableClientes.getSelectionModel();
        model.addListSelectionListener((ListSelectionEvent lse) -> {
            TableModel clientesModel = view.TableClientes.getModel();
            String idSelected = clientesModel.getValueAt(this.view.TableClientes.getSelectedRow(), 0).toString();
            ClienteControlador.seleccionarCliente(view, idSelected);
        });

        this.view.listaClientes.addListSelectionListener((ListSelectionEvent lse) -> {
            ClienteControlador.seleccionarCliente(view, this.view.listaClientes.getSelectedValue());
        });

        this.view.boton1.addActionListener(this);
        this.view.botonEliminarCliente.addActionListener(this);
    }

    public void iniciar() {
        view.setTitle("Gestion de stock");
        view.setLocationRelativeTo(null);
        view.setVisible(true);

        //Tabla
        DefaultTableModel clientesModel = new DefaultTableModel();
        ClienteControlador.cargarTabla(view, clientesModel);
        // Lista
        ClienteControlador.cargarLista(view);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.boton1) {
            view.clienteIdText.setText(view.listaClientes.getSelectedValue());
        }
        if (e.getSource() == view.botonEliminarCliente) {
            ClienteControlador.eliminarCliente(view);
        }
    }

}

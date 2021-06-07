package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableModel;
import vista.Index;

public class Controlador implements ActionListener {

    private Index view;

    public Controlador(Index view /*Modelo model*/) {
        this.view = view;    
        
        //Se declaran todos los botones o posibles actions de la misma forma
        
        
        // LISTENER DE ITEM SELECCIONADO EN TALBA DE CLIENTES
        ListSelectionModel model = view.cliTabla.getSelectionModel();
        model.addListSelectionListener((ListSelectionEvent lse) -> {
            TableModel clientesModel = view.cliTabla.getModel();
            try{
               String cliIdSelected = clientesModel.getValueAt(this.view.cliTabla.getSelectedRow(), 0).toString();
               String cliApellidoSelected = clientesModel.getValueAt(this.view.cliTabla.getSelectedRow(), 1).toString();
               String cliNombreSelected = clientesModel.getValueAt(this.view.cliTabla.getSelectedRow(), 2).toString();
               String cliDniSelected = clientesModel.getValueAt(this.view.cliTabla.getSelectedRow(), 3).toString();
               String cliDireccionSelected = clientesModel.getValueAt(this.view.cliTabla.getSelectedRow(), 4).toString();
               String cliTelefonoSelected = clientesModel.getValueAt(this.view.cliTabla.getSelectedRow(), 5).toString();
               String cliEmailSelected = clientesModel.getValueAt(this.view.cliTabla.getSelectedRow(), 6).toString();
            ClienteControlador.seleccionarCliente(view, cliIdSelected, cliApellidoSelected, cliNombreSelected, cliDniSelected, cliDireccionSelected, cliTelefonoSelected, cliEmailSelected); 
            }catch(Exception e){             
            }         
        });
        
        this.view.clienteBotonEliminar.addActionListener(this);
    }

    public void iniciar() {
        view.setTitle("Gestion de stock");
        view.setLocationRelativeTo(null);
        view.setVisible(true);

        //Tabla
        ClienteControlador.cargarTabla(view);
        // Lista

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.clienteBotonEliminar) {
            ClienteControlador.eliminarCliente(view);
        }
    }

}

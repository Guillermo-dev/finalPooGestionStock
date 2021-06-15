package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import vista.Index;

public class Controlador implements ActionListener, ListSelectionListener {

    private Index view;

    public Controlador(Index view /*Modelo model*/) {
        this.view = view;
        // modelo??
        
        this.view.cliTabla.getSelectionModel().addListSelectionListener(this);
        this.view.clienteBotonEliminar.addActionListener(this);
    }

    public void iniciar() {
        view.setTitle("Gestion de stock");
        view.setLocationRelativeTo(null);
        view.setVisible(true);

        //Tabla
        ClienteControlador.cargarTabla(view);
    }

    
    //BOTONES
    @Override
    public void actionPerformed(ActionEvent lse) {
        if (lse.getSource() == view.clienteBotonEliminar) {
            ClienteControlador.eliminarCliente(view);
        }
    }

    //TABLAS
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (lse.getSource() == view.cliTabla.getSelectionModel()) {
            ClienteControlador.seleccionarCliente(
                    view,
                    view.cliTabla.getValueAt(this.view.cliTabla.getSelectedRow(), 0).toString(),
                    view.cliTabla.getValueAt(this.view.cliTabla.getSelectedRow(), 1).toString(),
                    view.cliTabla.getValueAt(this.view.cliTabla.getSelectedRow(), 2).toString(),
                    view.cliTabla.getValueAt(this.view.cliTabla.getSelectedRow(), 3).toString(),
                    view.cliTabla.getValueAt(this.view.cliTabla.getSelectedRow(), 4).toString(),
                    view.cliTabla.getValueAt(this.view.cliTabla.getSelectedRow(), 5).toString(),
                    view.cliTabla.getValueAt(this.view.cliTabla.getSelectedRow(), 6).toString()
            );
        }
    }

}

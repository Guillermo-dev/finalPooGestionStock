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
        
        this.view.clieTabla.getSelectionModel().addListSelectionListener(this);
        this.view.clieBtnEliminar.addActionListener(this);
    }

    public void iniciar() {
        view.setTitle("Gestion de stock");
        view.setLocationRelativeTo(null);

        //Tabla
        ClienteControlador.cargarTabla(view);
    }

    
    //BOTONES
    @Override
    public void actionPerformed(ActionEvent lse) {
        if (lse.getSource() == view.clieBtnEliminar) {
            ClienteControlador.eliminarCliente(view);
        }
    }

    //TABLAS
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (lse.getSource() == view.clieTabla.getSelectionModel()) {
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
        }
    }

}

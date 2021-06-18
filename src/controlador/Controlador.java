package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.services.ClienteConsultas;
import vista.Index;

public class Controlador implements ActionListener, ListSelectionListener {

    private final Index view;
    private final ClienteConsultas domConsultasClie;

    public Controlador(Index view, ClienteConsultas domConsultasClie) {
        this.view = view;
        this.domConsultasClie = domConsultasClie;

        this.view.clieTabla.getSelectionModel().addListSelectionListener(this);
        this.view.clieBtnEliminar.addActionListener(this);
    }

    public void iniciar() {
        view.setTitle("Gestion de stock");
        view.setLocationRelativeTo(null);

        //iniciar tabla
        ClienteControlador.cargarTabla(view, domConsultasClie);
    }

    //BOTONES
    @Override
    public void actionPerformed(ActionEvent lse) {
        if (lse.getSource() == view.clieBtnEliminar) {
            ClienteControlador.eliminarCliente(view, domConsultasClie);
        }
    }

    //TABLAS
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (lse.getSource() == view.clieTabla.getSelectionModel()) {
            try {
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

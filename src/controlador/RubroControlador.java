
package controlador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Rubro;
import modelo.services.RubroConsultas;
import vista.Index;

public class RubroControlador {
    
    public static void cargarTabla(JTable rubroTabla, ArrayList<Rubro> Rubros) {
        DefaultTableModel tableModel = (DefaultTableModel) rubroTabla.getModel();
        tableModel.setNumRows(0);

        Rubros.forEach((rubro) -> {
            String[] data = new String[3];
            data[0] = Integer.toString(rubro.getId());
            data[1] = rubro.getNombre();
            data[2] = rubro.getDescripcion();
            tableModel.addRow(data);
        });
    }
    
    public static void iniciarTabla(JTable rubroTabla, RubroConsultas services) {
        ArrayList<Rubro> rubros = services.getAllRubros();
        cargarTabla(rubroTabla, rubros);
    }
    
    public static void buscarTabla(JTable rubroTabla, RubroConsultas services, String buscador) {
        ArrayList<Rubro> rubros = services.getRubrosBuscador(buscador);
        cargarTabla(rubroTabla, rubros);
    }
    
    public static void cargarInputTexts(Index view, String id, String nombre, String descripcion) {
        view.rubInputTextId.setText(id);
        view.rubInputTextNombre.setText(nombre);
        view.rubInputTextDescripcion.setText(descripcion);
    }
    
    public static void vaciarInputTexts(Index view) {
        view.rubInputTextId.setText(null);
        view.rubInputTextNombre.setText(null);
        view.rubInputTextDescripcion.setText(null);
    }
    
    public static void eliminarCliente(Index view, RubroConsultas services) {
        try {
            services.deleteRubro(Integer.parseInt(view.rubInputTextId.getText()));

            DefaultTableModel rubrosModel = (DefaultTableModel) view.rubroTabla.getModel();
            rubrosModel.removeRow(view.rubroTabla.getSelectedRow());

            vaciarInputTexts(view);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(null, "ERROR INESPERADO \n Intentelo mas tarde");
        }

    }
}

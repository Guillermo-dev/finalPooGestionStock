package controlador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Rubro;
import modelo.services.RubroConsultas;
import org.hibernate.exception.ConstraintViolationException;
import vista.Index;

public class RubroControlador {

    public static void cargarTabla(JTable rubTabla, ArrayList<Rubro> Rubros) {
        DefaultTableModel tableModel = (DefaultTableModel) rubTabla.getModel();
        tableModel.setNumRows(0);

        Rubros.forEach((rubro) -> {
            String[] data = new String[3];
            data[0] = Integer.toString(rubro.getId());
            data[1] = rubro.getNombre();
            data[2] = rubro.getDescripcion();
            tableModel.addRow(data);
        });
    }

    public static void iniciarTabla(Index view) {
        ArrayList<Rubro> rubros = RubroConsultas.getAllRubros();
        cargarTabla(view.rubTabla, rubros);
    }

    public static void buscarTabla(Index view) {
        String buscador = view.rubInputTextBuscador.getText();
        ArrayList<Rubro> rubros = RubroConsultas.getRubrosBuscador(buscador);
        cargarTabla(view.rubTabla, rubros);
    }

    public static void cargarInputTexts(Index view) {
        view.rubInputTextId.setText(view.rubTabla.getValueAt(view.rubTabla.getSelectedRow(), 0).toString());
        view.rubInputTextNombre.setText(view.rubTabla.getValueAt(view.rubTabla.getSelectedRow(), 1).toString());
        view.rubInputTextDescripcion.setText(view.rubTabla.getValueAt(view.rubTabla.getSelectedRow(), 2).toString());
    }

    public static void vaciarInputTexts(Index view) {
        view.rubInputTextId.setText(null);
        view.rubInputTextNombre.setText(null);
        view.rubInputTextDescripcion.setText(null);
    }

    public static boolean inputsTextValido(Index view) {
        // TODO: Agregar logica de validacion
        return view.rubInputTextNombre.getText().equals("")
                || view.rubInputTextDescripcion.getText().equals("");
    }

    public static boolean rubroSeleccionado(Index view) {
        return !view.rubInputTextId.getText().equals("");
    }

    public static void agregarRubro(Index view) {
        if (inputsTextValido(view)) {
            JOptionPane.showMessageDialog(null, "Rellene todos los campos");
        } else {
            Rubro rubro = new Rubro(
                    view.rubInputTextNombre.getText(),
                    view.rubInputTextDescripcion.getText());
            if (rubroSeleccionado(view)) {
                try {
                    RubroConsultas.updateRubro(rubro, Integer.parseInt(view.rubInputTextId.getText()));
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            } else {
                try {
                    RubroConsultas.saveRubro(rubro);
                } catch (Exception e) {
                    // TODO
                    JOptionPane.showMessageDialog(null, "Error inesperado");
                }
            }
            iniciarTabla(view);
            vaciarInputTexts(view);
        }
    }

    public static void eliminarRubro(Index view) {
        try {
            RubroConsultas.deleteRubro(Integer.parseInt(view.rubInputTextId.getText()));

            DefaultTableModel rubrosModel = (DefaultTableModel) view.rubTabla.getModel();
            rubrosModel.removeRow(view.rubTabla.getSelectedRow());

            vaciarInputTexts(view);
        } catch (ConstraintViolationException e) {
            JOptionPane.showMessageDialog(view, "El rubro esata asociado a un articulo, no se puede eliminar");
        }
    }
}

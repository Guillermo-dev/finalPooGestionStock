package controlador;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import vista.Index;
import modelo.services.HibernateUtil;

public class ClienteControlador {

    public static void cargarTabla(Index view, DefaultTableModel clientesModel) {
        view.TableClientes.setModel(clientesModel);
        ArrayList<Cliente> clientes = HibernateUtil.getAllClientes();
        
        clientesModel.addColumn("Id");
        clientesModel.addColumn("Nombre");
        clientesModel.addColumn("Apellido");
        clientesModel.addColumn("Direccion");
        
        clientes.forEach((cliente) -> {
            String[] data = new String[4];
            data[0] = Integer.toString(cliente.getId());
            data[1] = cliente.getNombre();
            data[2] = cliente.getApellido();
            data[3] = cliente.getDireccion();
            System.out.println(data[1]);
            clientesModel.addRow(data);
        });
//        String clientesM[][] = new String[clientes.size()][4];
//        for (int i = 0; i < clientes.size(); i++) {
//            clientesM[i][0] = Integer.toString(clientes.get(i).getId());
//            clientesM[i][1] = clientes.get(i).getNombre();
//            clientesM[i][2] = clientes.get(i).getApellido();
//            clientesM[i][3] = clientes.get(i).getDireccion();
//        }
//        view.TableClientes.setModel(new javax.swing.table.DefaultTableModel(
//                clientesM,
//                new String[]{"Id", "Nombre", "Apellido", "Direccion"}
//        ));
    }

    public static void cargarLista(Index view) {
        DefaultListModel clientesModel = new DefaultListModel();
        view.listaClientes.setModel(clientesModel);
        ArrayList<Cliente> clientes = HibernateUtil.getAllClientes();
        clientes.forEach((cliente) -> {
            clientesModel.addElement(cliente.getId() + ", " + cliente.getNombre() + ", " + cliente.getApellido());
        });
    }

    public static void seleccionarCliente(Index view, String id) {
        try {
            view.clienteIdText.setText(id.split(",")[0]);
        } catch (Exception e) {

        }
    }

    public static void eliminarCliente(Index view) {
        try {

            HibernateUtil.deleteCliente(Integer.parseInt(view.clienteIdText.getText()));
            view.clienteIdText.setText("");
            cargarLista(view);
            System.out.println("Eliminado uscces");
        } catch (Exception e) {
            System.out.println("Error");
        }

    }
}

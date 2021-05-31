package modelo;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private int id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    @Column(name = "proposito")
    private char proposito;

    @Column(name = "num_factura")
    private int numeroFactura;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "total")
    private float total;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Linea> lineas;

    public Factura() {
    }

    public Factura(Cliente cliente, char proposito, int numeroFactura, String fecha, List<Linea> lineas) {
        this.cliente = cliente;
        this.proposito = proposito;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.lineas = lineas;
    }

    public Factura(Proveedor proveedor, char proposito, int numeroFactura, String fecha, float total, List<Linea> lineas) {
        this.proveedor = proveedor;
        this.proposito = proposito;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.total = total;
        this.lineas = lineas;
    }

}

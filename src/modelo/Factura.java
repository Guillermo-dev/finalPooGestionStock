package modelo;

import java.util.Date;
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
    
    @OneToOne(mappedBy="factura")
    private NotaCredito notaCredito;

    @Column(name = "proposito")
    private char proposito;

    @Column(name = "num_factura")
    private String numeroFactura;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "total")
    private float total;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Linea> lineas;

    public Factura() {
    }

    public Factura(Cliente cliente, char proposito, String numeroFactura, Date fecha, float total) {
        this.cliente = cliente;
        this.proposito = proposito;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.total = total;
    }

    public Factura(Proveedor proveedor, char proposito, String numeroFactura, Date fecha, float total) {
        this.proveedor = proveedor;
        this.proposito = proposito;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.total = total;
    }

    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
    }
    

    public int getId() {
        return id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public char getProposito() {
        return proposito;
    }

    public Date getFecha() {
        return fecha;
    }

    public float getTotal() {
        return total;
    }

    
}

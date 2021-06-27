package modelo;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "notas_de_credito")
public class NotaCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota_credito")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

    @ManyToOne()
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "numero_factura")
    private String nuemroFactura;

    @Column(name = "importe")
    private float importe;

    @Column(name = "fecha")
    private Date fecha;

    public NotaCredito() {
    }

    public NotaCredito(Factura factura, Cliente cliente, String nuemroFactura, float importe, Date fecha) {
        this.factura = factura;
        this.cliente = cliente;
        this.nuemroFactura = nuemroFactura;
        this.importe = importe;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public Factura getFactura() {
        return factura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getNuemroFactura() {
        return nuemroFactura;
    }

    public float getImporte() {
        return importe;
    }

    public Date getFecha() {
        return fecha;
    }

}

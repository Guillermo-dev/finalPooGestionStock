package modelo;

import java.util.List;
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
    private int nuemroFactura;

    @Column(name = "importe")
    private float importe;

    @Column(name = "fecha")
    private String fecha;

    public NotaCredito() {
    }

    public NotaCredito(Factura factura, Cliente cliente, int nuemroFactura, float importe, String fecha) {
        this.factura = factura;
        this.cliente = cliente;
        this.nuemroFactura = nuemroFactura;
        this.importe = importe;
        this.fecha = fecha;
    }

}

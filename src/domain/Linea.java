package domain;

import javax.persistence.*;

@Entity
@Table(name = "facturas")
public class Linea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linea")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;

    @ManyToOne()
    @JoinColumn(name = "id_factura")
    private Factura factura;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio_unit")
    private float precioUnitario;

    @Column(name = "subtotal")
    private float subTotal;

    public Linea() {
    }

    public Linea(Articulo articulo, Factura factura, int cantidad, float precioUnitario, float subTotal) {
        this.articulo = articulo;
        this.factura = factura;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
    }
    
    
}

package domain;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "articulos")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    @ManyToOne()
    @JoinColumn(name = "id_rubro")
    private Rubro rubro;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_unit")
    private float precioUnitario;

    @Column(name = "stock_actual")
    private int stockActual;

    @Column(name = "stock_minimo")
    private int stockMinimo;
    
    @OneToMany(mappedBy="proveedores",cascade= CascadeType.ALL)
    private List<Linea> lineas;

    public Articulo() {
    }

    public Articulo(Proveedor proveedor, Rubro rubro, String nombre, String descripcion, float precioUnitario, int stockActual, int stockMinimo) {
        this.proveedor = proveedor;
        this.rubro = rubro;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
    }
    
}

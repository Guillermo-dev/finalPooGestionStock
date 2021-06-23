package modelo;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cuil_cuit")
    private String cuilCuit;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<Articulo> articulos;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<Factura> facturas;

    public Proveedor() {
    }

    public Proveedor(String nombre, String cuilCuit, String razonSocial, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.cuilCuit = cuilCuit;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCuilCuit() {
        return cuilCuit;
    }

    public String getEmail() {
        return email;
    }
}

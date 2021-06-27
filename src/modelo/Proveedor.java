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

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.PERSIST)
    private List<Articulo> articulos;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.PERSIST)
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

    public void setId(int id) {
        this.id = id;
    }

    public void setCuilCuit(String cuilCuit) {
        this.cuilCuit = cuilCuit;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

package Modelo;

public class Producto {
    private int idProd;
    private String nombre;
    private double precio;
    private String imagen;
    private double precioOnline;
    private String descuento;

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecioOnline() {
        return precioOnline;
    }

    public void setPrecioOnline(double precioOnline) {
        this.precioOnline = precioOnline;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

   

  }

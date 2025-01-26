package gestorAplicacion;

import java.io.Serializable;

public class PedidoItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String producto;
    private int cantidad;

    // Constructor
    public PedidoItem(String producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return producto + " x " + cantidad;
    }
}

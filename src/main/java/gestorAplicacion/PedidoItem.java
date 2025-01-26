package gestorAplicacion;

import java.io.Serializable;
import java.util.ArrayList;

public class PedidoItem implements Serializable {

    private static final long serialVersionUID = 1L;
    public static ArrayList<PedidoItem> pedidoItems = new ArrayList<PedidoItem>();
    private String producto;
    private int cantidad;

    // Constructor
    public PedidoItem(String producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        pedidoItems.add(this);
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

    public static ArrayList<PedidoItem> getPedidoItems() {
        return pedidoItems;
    }
}

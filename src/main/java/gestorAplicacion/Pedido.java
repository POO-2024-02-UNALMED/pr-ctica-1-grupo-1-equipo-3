package gestorAplicacion;
import java.io.Serializable;

public class Pedido implements Serializable{
    private int id;
    private Cliente cliente;
    private Calificacion calificacion;
    private double promCalificacion;
    private Restaurante restaurante;
    private Factura factura;

    public Pedido(int id, Cliente cliente, Factura factura, Restaurante restaurante) {
        this.id = id;
        this.cliente = cliente;
        this.factura = factura;
        this.restaurante = restaurante;
    }
    //Este metodo se encarga de tomar la calificacion del tiempo de espera del cliente para determinar la calificacion general del restaurante
    public void tiempoEsperaRestaurante(Calificacion calificacion){
        double tiempoEspera = calificacion.getTiempoEspera();
        if (tiempoEspera < 3) {
            this.restaurante.setReputacion(this.restaurante.getReputacion()-0.1);
        }
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public double getPromCalificacion() {
        return promCalificacion;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public void promediarCalificacion(Calificacion calificacion) {
        this.promCalificacion = calificacion.calcularPromCalificacion();
    }
}

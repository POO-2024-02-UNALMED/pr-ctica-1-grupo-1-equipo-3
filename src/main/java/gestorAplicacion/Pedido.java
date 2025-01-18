package gestorAplicacion;
import java.io.Serializable;

public class Pedido {
    private int id;
    private Cliente cliente;
    private Calificacion calificacion;
    private double promCalificacion;

    public Pedido(int id, Cliente cliente, Calificacion calificacion) {
        this.id = id;
        this.cliente = cliente;
        this.calificacion = calificacion;
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

    public void promediarCalificacion(Calificacion calificacion) {
        this.promCalificacion = calificacion.calcularPromCalificacion();
    }
}

package gestorAplicacion;

public class Pedido {
    private int id;
    private Cliente cliente;
    private Calificacion calificacion;

    public Pedido(int id, Cliente cliente, Calificacion calificacion) {
        this.id = id;
        this.cliente = cliente;
        this.calificacion = calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    
}

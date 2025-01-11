package gestorAplicacion;

public class Calificacion {
    private Cliente cliente;
    private Pedido pedido;
    private int calidadComida;
    private int calidadMesero;
    private int tiempoEspera;
    private String comentario;
    private double promedioCalificacion;

    public Calificacion(Cliente cliente, Pedido pedido, int calidadComida, int calidadMesero, int tiempoEspera, String comentario) {
        this.cliente = cliente;
        this.pedido = pedido;
        this.calidadComida = calidadComida;
        this.calidadMesero = calidadMesero;
        this.tiempoEspera = tiempoEspera;
        this.comentario = comentario;
        this.promedioCalificacion = this.calcularPromCalificacion();
    }
    public double calcularPromCalificacion() {
        return (this.calidadComida + this.calidadMesero + this.tiempoEspera) / 3.0;
    }

    public double getPromedioCalificacion() {
        return promedioCalificacion;
    }
}


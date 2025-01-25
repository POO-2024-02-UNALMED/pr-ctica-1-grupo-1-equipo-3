package gestorAplicacion;
import java.io.Serializable;
import java.util.ArrayList;

public class Calificacion implements Serializable{
    private static ArrayList<Calificacion> calificaciones = new ArrayList<Calificacion>();
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
        calificaciones.add(this);
    }
    public double calcularPromCalificacion() {
        return (this.calidadComida + this.calidadMesero + this.tiempoEspera) / 3.0;
    }

    public double getPromedioCalificacion() {
        return promedioCalificacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getCalidadMesero() {
        return calidadMesero;
    }

    public void setCalidadMesero(int calidadMesero) {
        this.calidadMesero = calidadMesero;
    }

    public int getCalidadComida() {
        return calidadComida;
    }

    public void setCalidadComida(int calidadComida) {
        this.calidadComida = calidadComida;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public static ArrayList<Calificacion> getCalificaciones() {
        return calificaciones;
    }
}
//private void asignarPuntosPorCalificacion() {
 //   int puntosAsignados = (int) Math.round(promedioCalificacion);
//cliente.actualizarPuntos(puntosAsignados);




package gestorAplicacion;

import java.util.ArrayList;
import java.io.Serializable;

public class Domicilio implements Serializable {

    // Atributos
    private static final long serialVersionUID = 1L;
    private static ArrayList<Domicilio> domicilios = new ArrayList<Domicilio>();
    private Cliente cliente;
    private ArrayList<PedidoItem> pedidoDomicilio; // Cambiado a ArrayList
    private String direccion;
    private boolean domicilioPrioritario;
    private int costoEnvio;
    private Calificacion calificacion;
    private double promCalificacion;
    private Domiciliario domiciliario;

    // Constructor
    public Domicilio(Cliente cliente, ArrayList<PedidoItem> pedidoDomicilio, String direccion, boolean domicilioPrioritario, int costoEnvio, Domiciliario domiciliario) {
        this.cliente = cliente;
        this.pedidoDomicilio = pedidoDomicilio;
        this.direccion = direccion;
        this.domicilioPrioritario = domicilioPrioritario;
        this.costoEnvio = costoEnvio;
        this.domiciliario = domiciliario;
        domicilios.add(this);
    }

    public void promediarCalificacion(Calificacion calificacion) {
        this.promCalificacion = calificacion.calcularPromCalificacion();
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<PedidoItem> getPedidoDomicilio() {
        return pedidoDomicilio;
    }

    public void setPedidoDomicilio(ArrayList<PedidoItem> pedidoDomicilio) {
        this.pedidoDomicilio = pedidoDomicilio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isDomicilioPrioritario() {
        return domicilioPrioritario;
    }

    public void setDomicilioPrioritario(boolean domicilioPrioritario) {
        this.domicilioPrioritario = domicilioPrioritario;
    }

    public int getCosto() {
        return costoEnvio;
    }

    public void setCosto(int costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public static ArrayList<Domicilio> getDomicilios() {
        return domicilios;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public double getPromCalificacion() {
        return promCalificacion;
    }

    public void setPromCalificacion(double promCalificacion) {
        this.promCalificacion = promCalificacion;
    }

    public Domiciliario getDomiciliario() {
        return domiciliario;
    }

    public void setDomiciliario(Domiciliario domiciliario) {
        this.domiciliario = domiciliario;
    }
}

package gestorAplicacion;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;

public class Reserva implements Serializable{
    private static final long serialVersionUID = 1L;
    private static ArrayList<Reserva> reservas = new ArrayList<Reserva>();
    private static int contador;
	private int id;
    private Mesa mesa;
    private LocalDateTime fechaHora;
    private LocalDateTime fechaDeGeneracion;
    private Mesero mesero;
    private int numeroPersonas;
    private Cliente cliente;
    private int recargo;

    public Reserva(Mesa mesa) {
        this.mesa = mesa;
    }
    //terminar
    public Reserva(Cliente cliente,Mesa mesa,LocalDateTime fechaHora,int numeroPersonas,LocalDateTime fechaDeGeneracion, Mesero mesero) {
    }
    
    public Reserva(Mesa mesa,LocalDateTime fechaHora,int numeroPersonas,LocalDateTime fechaDeGeneracion) {
    	this.mesa = mesa;
    	this.fechaHora = fechaHora;
    	this.numeroPersonas = numeroPersonas;
    	this.fechaDeGeneracion = fechaDeGeneracion;
    	this.id = ++contador;
    	this.calcularRecargo();
        reservas.add(this);
    }
    
    public void calcularRecargo() {

        int añosDiferencia = fechaHora.getYear() - fechaDeGeneracion.getYear();
        int mesesDiferencia = (añosDiferencia * 12) + (fechaHora.getMonthValue() - fechaDeGeneracion.getMonthValue());

        if (mesesDiferencia > 1) {
            this.recargo = 50000;
        }
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public void setCliente(Cliente cliente) {
    	this.cliente = cliente;
    }
    
    public Cliente getCliente() {
    	return this.cliente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setMesero(Mesero mesero) {
    	this.mesero = mesero;
    }
    
    public Mesero getMesero() {
    	return this.mesero;
    }
    
    public int getRecargo() {
    	return this.recargo;
    }
    
    public void setRecargo(int recargo) {
    	this.recargo = recargo;
    }

    public static ArrayList<Reserva> getReservas() {
        return reservas;
    }
    
    public int getId() {
    	return this.id;
    }
}
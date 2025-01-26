package gestorAplicacion;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;

public class Reserva implements Serializable{
    private static final long serialVersionUID = 1L;
    private static ArrayList<Reserva> reservas = new ArrayList<Reserva>();
    private static int contador;
    private static final int PRECIO = 30000;
	private int id;
	private Factura factura;
    private Mesa mesa;
    private LocalDateTime fechaHora;
    private LocalDateTime fechaDeGeneracion;
    private Mesero mesero;
    private int numeroPersonas;
    private Cliente cliente;
    private int precioTotal;

    public Reserva(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public Reserva(Mesa mesa,LocalDateTime fechaHora,int numeroPersonas,LocalDateTime fechaDeGeneracion) {
    	this.mesa = mesa;
    	this.fechaHora = fechaHora;
    	this.numeroPersonas = numeroPersonas;
    	this.fechaDeGeneracion = fechaDeGeneracion;
    	this.id = ++contador;
    	this.precioTotal += PRECIO;
    	this.calcularRecargo();
    	this.factura = new Factura();
        reservas.add(this);
    }
    
    public void calcularRecargo() {

        int añosDiferencia = fechaHora.getYear() - fechaDeGeneracion.getYear();
        int mesesDiferencia = (añosDiferencia * 12) + (fechaHora.getMonthValue() - fechaDeGeneracion.getMonthValue());

        if (mesesDiferencia > 1) {
            this.precioTotal += 50000;
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
    
    public Factura getFactura() {
    	return this.factura;
    }
    
    public int getPrecioTotal() {
    	return precioTotal;
    }
    
    //Suma costos al precio total
    public void sumarPrecio(int precio) {
    	this.precioTotal += precio;
    }

    public static ArrayList<Reserva> getReservas() {
        return reservas;
    }
    
    public int getId() {
    	return this.id;
    }
}
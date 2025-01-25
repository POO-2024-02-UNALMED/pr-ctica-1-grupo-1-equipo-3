package gestorAplicacion;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.Serializable;

public class Mesa implements Serializable{
    private static ArrayList<Mesa> mesas = new ArrayList<Mesa>();
    private int numero;
    private int capacidad;
    private Mesero mesero;
    private String tipo;
    private String decoracion;
    private Pedido pedido;
    private ArrayList<Reserva> reservas = new ArrayList<Reserva>();
    
    public Mesa(int numero, int capacidad, String tipo, Restaurante restaurante) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.tipo = tipo;
        mesas.add(this);
        
        if (tipo == "basic") {
        	this.decoracion = "normal";
        }
        
        restaurante.agregarMesa(this);
    }
        
    //Determina si la mesa está disponible para una fecha y hora determinadas, 
    //teniendo en cuenta que cada mesa estará reservada para 1 hora por defecto.
    public boolean estaDisponible(LocalDateTime horario) {

        for (Reserva reserva : reservas) {
            LocalDateTime inicioReserva = reserva.getFechaHora();
            LocalDateTime finReserva = inicioReserva.plusHours(1);

            if ((horario.isAfter(inicioReserva) || horario.isEqual(inicioReserva)) &&
            	    (horario.isBefore(finReserva) || horario.isEqual(finReserva))) {
            	    return false; //Se presenta conflicto
            }
        }
        return true;
    }
    
    
    //Asigna un mesero a la reserva y añade la reserva al ArrayList reservas.true si es exitoso, false, si no pudo asignar
    public boolean reservar(Reserva reserva) {
    	Mesero.organizarMeserosPorCalificacion();
    	
    	for (Mesero mesero : Mesero.getMeseros() ) {
    		if (mesero.disponibilidad(reserva.getFechaHora()) == true) {
    			this.mesero = mesero;
    			reserva.setMesero(mesero);
    			reservas.add(reserva);
    			return true; //Si la reserva se realizó con éxito
    		}
    	}
    	return false; //Si la reserva no se pudo completar
    }

    public int getNumero() {
        return this.numero;
    }

    public int getCapacidad() {
        return this.capacidad;
    }

    public String getTipo() {
        return this.tipo;
    }
    
    public void setMesero(Mesero mesero) {
    	this.mesero = mesero;
    }

    public Mesero getMesero() {
        return mesero;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public void setDecoracion(String decoracion) {
    	this.decoracion = decoracion;
    }
    
    public String getDecoracion() {
    	return this.decoracion;
    }

    public static ArrayList<Mesa> getMesas() {
        return mesas;
    }
}

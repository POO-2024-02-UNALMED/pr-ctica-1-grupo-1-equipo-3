package gestorAplicacion;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Mesa {

    private int numero;
    private int capacidad;
    private Mesero mesero;
    private String tipo;
    private ArrayList<Reserva> reservas = new ArrayList<Reserva>();
    
    public Mesa(int numero, int capacidad, String tipo, Restaurante restaurante) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.tipo = tipo;
        restaurante.agregarMesa(this);
    }
        
    //Determina si la mesa está disponible pra una fecha y hora determinadas, 
    //teniendo en cuenta que cada mesa estará reservada para 1 hora por defecto.
    public boolean estaDisponible(LocalDateTime horario) {
        LocalDateTime finHorario = horario.plusHours(1);

        for (Reserva reserva : reservas) {
            LocalDateTime inicioReserva = reserva.getFechaHora();
            LocalDateTime finReserva = inicioReserva.plusHours(1);

            if (!(horario.isAfter(finReserva) || finHorario.isBefore(inicioReserva))) {
                return false; // No está disponible si hay conflicto
            }
        }
        return true;
    }
    
    
    //Asigna un mesero a la reserva y añade la reserva al ArrayList reservas.
    public void reservar(Reserva reserva) {
    	Mesero.organizarMeserosPorCalificacion();
    	Mesero meseroAsignado = Mesero.getMeseros().get(0);
    	this.mesero = meseroAsignado;
    	reserva.setMesero(meseroAsignado);
    	reservas.add(reserva);
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
    
}

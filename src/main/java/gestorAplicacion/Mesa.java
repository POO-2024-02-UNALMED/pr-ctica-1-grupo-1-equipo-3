package gestorAplicacion;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Mesa {

    private int numero;
    private boolean estado;
    private int capacidad;
    private Mesero mesero;
    private String tipo;
    private ArrayList<Reserva> reservas = new ArrayList<Reserva>();

    public Mesa() {
    }

    public boolean estaDisponible(LocalDateTime horario) {

        for(Reserva reserva : reservas) {
            LocalDateTime inicioReserva = reserva.getFechaHora();
            LocalDateTime finReserva = horario.plusHours(1);

            if (!(horario.isBefore(inicioReserva) || horario.isAfter(finReserva))) {
                return false; // No está disponible si hay conflicto
            }
        }
        return true;
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
}

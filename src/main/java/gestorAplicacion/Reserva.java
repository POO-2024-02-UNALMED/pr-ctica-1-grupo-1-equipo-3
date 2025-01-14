package gestorAplicacion;
import java.time.LocalDateTime;

public class Reserva {
    private Mesa mesa;

    private LocalDateTime fechaHora;

    public Reserva(Mesa mesa) {
        this.mesa = mesa;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

}
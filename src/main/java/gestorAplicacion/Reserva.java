package gestorAplicacion;
import java.time.LocalDateTime;

public class Reserva {
    private Mesa mesa;
    private LocalDateTime fechaHora;
    private LocalDateTime fechaDeGeneracion;
    private int numeroPersonas;
    private Cliente cliente;
    private int recargo;

    public Reserva(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public Reserva(Mesa mesa,LocalDateTime fechaHora,int numeroPersonas,LocalDateTime fechaDeGeneracion) {
    	this.mesa = mesa;
    	this.fechaHora = fechaHora;
    	this.numeroPersonas = numeroPersonas;
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

}
package gestorAplicacion;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Restaurante {

    private String nombre;
    private String horarioServicio;
    private ArrayList<Mesa> mesas;
    private ArrayList<Reserva> reservas;
    private ArrayList<Mesero> meseros;
    private ArrayList<Mesero> calificacionMeseros;  //Prioridad para ser escogidos

    public void hacerReserva(String fecha, String hora, Mesa mesa) {
        LocalDateTime horario = convertirFechaHora(fecha, hora);
    }

    public boolean validarFechaHora(String fecha, String hora) {
        String[] elementosFecha = fecha.split("/");
        String[] elementosHora = hora.split(":");

        if (elementosFecha.length == 3 && elementosHora.length == 2) {
            return true;
        } else {
            return false;
        }
    }

    public LocalDateTime convertirFechaHora(String fecha, String tiempo) {
        //Fecha en formato dd/MM/AAAA y Hora HH:mm
        String[] diaMesAño = fecha.split("/");
        String[] horaMinutos = tiempo.split(":");

        int dia = Integer.parseInt(diaMesAño[0]);
        int mes = Integer.parseInt(diaMesAño[1]);
        int año = Integer.parseInt(diaMesAño[2]);
        int hora = Integer.parseInt(horaMinutos[0]);
        int minutos = Integer.parseInt(horaMinutos[1]);

        LocalDateTime fechaHora = LocalDateTime.of(año, mes, dia, hora, minutos);

        return fechaHora;
    }

    public ArrayList<String> mesasDisponibles(int personas, String tipoMesa, LocalDateTime horario) {
        ArrayList<Mesa> mesasDisponibles = new ArrayList<Mesa>();
        ArrayList<String> mostrarMesas = new ArrayList<String>();

        for (Mesa mesa : mesas) {
            boolean estado = mesa.estaDisponible(horario);
            int capacidad = mesa.getCapacidad();
            String tipo = mesa.getTipo();

            if (estado == true) {
                if (tipoMesa == tipo) {
                    if (capacidad == personas) {
                        mesasDisponibles.add(mesa);
                    }
                    if (capacidad == personas + 1) {
                        mesasDisponibles.add(mesa);
                    }
                }
            }
        }

        for (Mesa mesa : mesasDisponibles) {
            String formato = "Mesa " + mesa.getNumero();
            mostrarMesas.add(formato);
        }
        return mostrarMesas;
    }

    public Restaurante(String nombre, String horarioServicio, ArrayList<Mesa> mesas, ArrayList<Reserva> reservas, ArrayList<Mesero> meseros, ArrayList<Mesero> calificacionMeseros) {
        this.nombre = nombre;
        this.horarioServicio = horarioServicio;
        this.mesas = mesas;
        this.reservas = reservas;
        this.meseros = meseros;
        this.calificacionMeseros = calificacionMeseros;
    }

    //agregarMesero
    public void agregarMesero(Mesero mesero) {
        meseros.add(mesero);
    }
}

package gestorAplicacion;
import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDateTime;
import java.io.Serializable;

public class Mesero extends Persona implements Serializable{
    private double promCalificaciones;
    private int totalCalificaciones;
    private ArrayList<LocalDateTime> disponibilidad;  //Fechas en las que el mesero debe atender una reserva
    private ArrayList<Integer> calificaciones;
    private static ArrayList<Mesero> meseros = new ArrayList<>();

    public Mesero(long identificacion, String nombre, int promCalificaciones, int totalCalificaciones, Restaurante restaurante) {
        super(nombre, identificacion);
        this.promCalificaciones = promCalificaciones;
        this.totalCalificaciones = totalCalificaciones;
        this.calificaciones = new ArrayList<>();
        this.disponibilidad = new ArrayList<>();
        Mesero.meseros.add(this);
        restaurante.agregarMesero(this);
    }

    public void actualizarDesempenoMesero(Calificacion calificacion){
        double sumaAcumalada = this.promCalificaciones * this.totalCalificaciones;
        totalCalificaciones++;
        promCalificaciones = (Math.round((sumaAcumalada + calificacion.getPromedioCalificacion() / totalCalificaciones) * 10.0))/10.0; //prom nuevo en una cifra decimal
    }
    
    //Organiza a los meseros en base a su atributo promCalificaciones
    public static void organizarMeserosPorCalificacion() {
        meseros.sort(Comparator.comparing(Mesero::getPromCalificaciones).reversed());
    }
    
    //Agrega una fecha en que estará ocupado atendiendo una mesa
    public void agregarFecha(LocalDateTime fecha) {
    	this.disponibilidad.add(fecha);
    }
    
    //Determina si el mesero estará disponible para una fecha en específico
    public boolean disponibilidad(LocalDateTime fecha) {
    	
    	for (LocalDateTime horario : disponibilidad) {
            LocalDateTime inicioHorario = horario;
            LocalDateTime finHorario = inicioHorario.plusHours(1);

            if ((fecha.isAfter(inicioHorario) || fecha.isEqual(inicioHorario)) &&
            	    (fecha.isBefore(finHorario) || fecha.isEqual(finHorario))) {
            	    return false; //Se presenta conflicto
            }
        }
        return true;
    }
    
    @Override
    public String toString(){
        String stringIdentificacion = Long.toString(this.getIdentificacion());
        String stringPromCaificaciones = Double.toString(this.getPromCalificaciones());
        return "Nombre: " + this.getNombre() + ", Identificacion: " + stringIdentificacion + ", Calificación: " + stringPromCaificaciones;
    }
    
    public long getIdentificacion() {
        return super.getIdentificacion();
    }

    public String getNombre() {
        return super.getNombre();
    }

    public double getPromCalificaciones() {
        return promCalificaciones;
    }

    public ArrayList<Integer> getCalificaciones() {
        return calificaciones;
    }

    public int getTotalCalificaciones() {
        return totalCalificaciones;
    }
    
    public static ArrayList<Mesero> getMeseros() {
        return meseros;
    }
    
    public static void setMeseros(ArrayList<Mesero> meseros) {
        Mesero.meseros = meseros;
    }
    
    
}
package gestorAplicacion;
import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDateTime;

public class Mesero {
    private int codigo;
    private String nombre;
    private double promCalificaciones;
    private int totalCalificaciones;
    private ArrayList<LocalDateTime> disponibilidad;  //Fechas en las que el mesero debe atender una reserva
    private ArrayList<Integer> calificaciones;
    private static ArrayList<Mesero> meseros = new ArrayList<>();

    public Mesero(int codigo, String nombre, int promCalificaciones, int totalCalificaciones, Restaurante restaurante) {
        this.codigo = codigo;
        this.nombre = nombre;
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
        String stringCodigo = Integer.toString(this.getCodigo());
        String stringPromCaificaciones = Double.toString(this.getPromCalificaciones());
        return "Nombre: " + this.getNombre() + ", Codigo: " + stringCodigo + ", Calificación: " + stringPromCaificaciones;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
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
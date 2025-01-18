package gestorAplicacion;
import java.io.Serializable;
import java.util.HashMap;

public class Cliente extends Persona implements Serializable{
    private Reserva reserva;
    private Restaurante restaurante;
    private int visitas;
    private HashMap<String, Integer> puntos;


    public Cliente(String nombre, long identificación, Reserva reserva, Restaurante restaurante) {
        super(nombre, identificación);
        this.reserva = reserva;
        this.restaurante = restaurante;
        this.visitas = 0;
        this.puntos = new HashMap<>();
        this.puntos.put("frecuencia", 0);
        this.puntos.put("calificacion", 0);
        this.puntos.put("gasto", 0);
        this.puntos.put("especiales", 0);
        restaurante.agregarCliente(this);
    }
    public void incrementarVisitas() {
        this.visitas++;
    }

    public int getVisitas() {
        return visitas;
    }

    public void acumularPuntos(String categoria, int cantidad) {
        if (puntos.containsKey(categoria)) {
            puntos.put(categoria, puntos.get(categoria) + cantidad);
        }
    }

    public void reducirPuntos(String categoria, int cantidad) {
        if (puntos.containsKey(categoria) && puntos.get(categoria) >= cantidad) {
            puntos.put(categoria, puntos.get(categoria) - cantidad);
        }
    }

    public int getPuntosGenerales() {
        return puntos.get("frecuencia") + puntos.get("gasto");
    }

    public Calificacion calificar(Pedido pedido, int CalidadComida, int calidadMesero, int tiempoEspera, String comentario) {
        Calificacion nuevaCalificacion = new Calificacion(this, pedido, CalidadComida,calidadMesero,  tiempoEspera,  comentario);
        pedido.setCalificacion(nuevaCalificacion); //Se asocia la calificaión al pedido
        pedido.promediarCalificacion(nuevaCalificacion); // El pedido obtiene su calificacion numerica promediada
        restaurante.getCalificacionesRestaurante().add(nuevaCalificacion.getPromedioCalificacion()); // La calificacion se añade a la lista de califiaciones del restaurante
        this.reserva.getMesa().getMesero().getCalificaciones().add(calidadMesero);  //La calificaión se añáde a lista de calificaciones del mesero
        this.reserva.getMesa().getMesero().actualizarDesempenoMesero(nuevaCalificacion); //se Actualiza el desempeño del mesero
        return nuevaCalificacion;
    }

    public String getNombre() {
        return super.getNombre();
    }

    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    public long getIdentificación() {
        return super.getIdentificacion();
    }

    public void setIdentificación(long identificación) {
        super.setIdentificacion(identificación);
    }
}

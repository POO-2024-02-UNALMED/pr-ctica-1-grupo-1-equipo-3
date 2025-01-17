package gestorAplicacion;

import java.util.HashMap;

public class Cliente {
    private String nombre;
    private int identificación;
    private Reserva reserva;
    private Restaurante restaurante;
    private int visitas;
    private HashMap<String, Integer> puntos;


    public Cliente(String nombre, int identificación, Reserva reserva, Restaurante restaurante) {
        this.nombre = nombre;
        this.identificación = identificación;
        this.reserva = reserva;
        this.restaurante = restaurante;
        this.visitas = 0;
        this.puntos = new HashMap<>();
        this.puntos.put("frecuencia", 0);
        this.puntos.put("calificacion", 0);
        this.puntos.put("gasto", 0);
        this.puntos.put("especiales", 0);
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
        restaurante.getCalificacionesRestaurante().add(nuevaCalificacion.getPromedioCalificacion()); // La calificacion se añade a la lista de califiaciones del restaurante
        this.reserva.getMesa().getMesero().getCalificaciones().add(calidadMesero);  //La calificaión se añáde a lista de calificaciones del mesero
        return nuevaCalificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdentificación() {
        return identificación;
    }

    public void setIdentificación(int identificación) {
        this.identificación = identificación;
    }
}

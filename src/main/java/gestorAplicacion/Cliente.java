package gestorAplicacion;

public class Cliente {
    private String nombre;
    private String identificación;
    private Reserva reserva;
    private Restaurante restaurante;

    public Cliente(String nombre, String identificación, Reserva reserva, Restaurante restaurante) {
        this.nombre = nombre;
        this.identificación = identificación;
        this.reserva = reserva;
        this.restaurante = restaurante;
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

    public String getIdentificación() {
        return identificación;
    }

    public void setIdentificación(String identificación) {
        this.identificación = identificación;
    }
}

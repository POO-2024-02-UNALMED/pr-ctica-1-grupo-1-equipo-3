package gestorAplicacion;

public class Mesero {
    private int codigo;
    private String nombre;
    private double promCalificaciones;
    private int totalCalificaciones;

    public Mesero(int codigo, String nombre, int promCalificaciones, int totalCalificaciones, Restaurante restaurante) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.promCalificaciones = promCalificaciones;
        this.totalCalificaciones = totalCalificaciones;
        restaurante.agregarMesero(this);
    }

    public void actualizarDesempenoMesero(Calificacion calificacion){
        double sumaAcumalada = this.promCalificaciones * this.totalCalificaciones;
        totalCalificaciones++;
        promCalificaciones = (Math.round((sumaAcumalada + calificacion.getPromedioCalificacion() / totalCalificaciones) * 10.0))/10.0; //prom nuevo en una cifra decimal
    }

    //añadir Mesero a la lista de meseros del restaurante
    public void anadirMesero() {

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
}
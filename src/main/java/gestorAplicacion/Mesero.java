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
}
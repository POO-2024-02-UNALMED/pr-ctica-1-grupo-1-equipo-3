package gestorAplicacion;

public class Persona{
    String nombre;
    long identificacion;

    //Constructor sin parametros Persona
    public Persona() {
    }
    
    public Persona(String nombre, long identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(long identificacion) {
        this.identificacion = identificacion;
    }
}

package gestorAplicacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.Serializable;

public class Persona implements Serializable {
    private String nombre;
    private long identificacion;
    private static ArrayList<Persona> personas = new ArrayList<Persona>();

    //Constructor sin parametros Persona
    public Persona() {
        personas.add(this);
    }
    
    public Persona(String nombre, long identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        personas.add(this);
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

    public static ArrayList<Persona> getPersonas() {
        return personas;
    }
}

package gestorAplicacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Domiciliario extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    public static ArrayList<Domiciliario> listaDomiciliarios = new ArrayList<Domiciliario>();
    private List<Integer> billetera;
    private double promCalificaciones;
    private int totalCalificaciones;
    private ArrayList<Integer> calificaciones;



    // Constructor
    public Domiciliario(String nombre, Long identificacion, double promCalificaciones, int totalCalificaciones) {
        super(nombre, identificacion);
        this.billetera = new ArrayList<>();
        inicializarBilletera();
        listaDomiciliarios.add(this);
        this.promCalificaciones = promCalificaciones;
        this.totalCalificaciones = totalCalificaciones;
    }

    // Método para inicializar la billetera con billetes predeterminados
    private void inicializarBilletera() {
        agregarBilletes(50000, 4); 
        agregarBilletes(20000, 6); 
        agregarBilletes(10000, 11); 
        agregarBilletes(5000, 25); 
        agregarBilletes(2000, 60); 
        agregarBilletes(1000, 100); 
    }

    public void agregarBilletes(int denominacion, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            billetera.add(denominacion);
        }
    }
    
    public static void inicializarDomiciliarios() {
        if (Domiciliario.getListaDomiciliarios().isEmpty()) {
            new Domiciliario("Carlos", (long) 123, 4, 8);
            new Domiciliario("María", (long) 456, 3, 12);
            new Domiciliario("Luis", (long) 789, 5, 3);
        }
    }

    // Método para calcular el total de dinero en la billetera
    public int calcularTotalBilletera() {
        int total = 0;
        for (int billete : billetera) {
            total += billete;
        }
        return total;
    }

    // Método para entregar cambio al cliente
    public List<Integer> entregarCambio(int cambio) {
        List<Integer> billetesEntregados = new ArrayList<>();
        int montoRestante = cambio;

        billetera.sort((a, b) -> b - a); // Ordenar de mayor a menor

        for (int i = 0; i < billetera.size() && montoRestante > 0; i++) {
            int billete = billetera.get(i);
            if (billete <= montoRestante) {
                billetesEntregados.add(billete);
                montoRestante -= billete;
                billetera.remove(i);
                i--; // Ajustar índice tras eliminación
            }
        }

        if (montoRestante > 0) {
            System.out.println("No se pudo entregar el cambio completo. Falta: " + montoRestante);
        }

        return billetesEntregados;
    }

    public void actualizarDesempenoDomiciliario(Calificacion calificacion){
        double sumaAcumalada = this.promCalificaciones * this.totalCalificaciones;
        totalCalificaciones++;
        this.promCalificaciones = (Math.round(((sumaAcumalada + calificacion.getTiempoEspera()) / totalCalificaciones) * 10.0))/10.0; //prom nuevo en una cifra decimal
    }

    // Getter para obtener la lista de domiciliarios
    public static ArrayList<Domiciliario> getListaDomiciliarios() {
        return listaDomiciliarios;
    }

    // Getter para obtener el nombre del domiciliario
    public String getNombre() {
        return nombre;
    }

    // Setter para establecer el nombre del domiciliario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter para obtner la lista de califiaciones del domiciliario
    public ArrayList<Integer> getCalificaciones() {
        return calificaciones;
    }
}

package gestorAplicacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Cliente extends Persona implements Serializable{
    private static final long serialVersionUID = 1L;
    private static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private Reserva reserva;
    private Restaurante restaurante;
    private int visitas;
    private HashMap<String, Integer> puntos;
    
    //Constructor con 3 paramentros para cliente
    public Cliente(String nombre, long identificacion, Restaurante restaurante) {
        super(nombre, identificacion);
        this.restaurante = restaurante;
        this.visitas = 0;
        restaurante.agregarCliente(this);
    }

    //Constructor sin parametros para Cliente
    public Cliente() {
        clientes.add(this);
    }

    public Cliente(String nombre, long identificacion, Reserva reserva, Restaurante restaurante) {
        super(nombre, identificacion);
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

    public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

    public Reserva getReserva() {
        return reserva;
    }
    
	public void setVisitas(int visitas) {
		this.visitas = visitas;
	}

	public int getVisitas() {
        return visitas;
    }
	
	public void incrementarVisitas() {
	        this.visitas++;
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

    @Override
	public String toString() {
		return "Cliente [ reserva = " + reserva + ", restaurante = " + restaurante + ", visitas = " + visitas + ", puntos = "
				+ puntos + ", nombre = " + nombre + ", id = " + identificacion + "]";
	}

	public Calificacion calificarPorReserva(Pedido pedido, int calidadComida, int calidadMesero, int tiempoEspera, String comentario) {
        Calificacion nuevaCalificacion = new Calificacion(this, pedido, calidadComida,calidadMesero,  tiempoEspera,  comentario);

        pedido.setCalificacion(nuevaCalificacion); //Se asocia la calificaión al pedido

        pedido.promediarCalificacion(nuevaCalificacion); // El pedido obtiene su calificacion numerica promediada

        restaurante.getCalificacionesRestaurante().add(nuevaCalificacion.getPromedioCalificacion()); // La calificacion se añade a la lista de califiaciones del restaurante

        this.reserva.getMesero().getCalificaciones().add(calidadMesero);  //La calificaión se añáde a lista de calificaciones del mesero

        this.reserva.getMesero().actualizarDesempenoMesero(nuevaCalificacion); //se Actualiza el desempeño del mesero

        this.reserva.getMesa().getPedido().getFactura().setCalificacion(nuevaCalificacion);

        return nuevaCalificacion;
    }

    public Calificacion calificarPorDomicilio(Domicilio domicilio, int calidadComida, int tiempoEspera, String comentario){
        Calificacion nuevaCalificacion = new Calificacion(this, domicilio, calidadComida, tiempoEspera, comentario);

        domicilio.setCalificacion(nuevaCalificacion);  //Se asocia la calificación al domicilio

        domicilio.promediarCalificacion(nuevaCalificacion);  //El domicilio obtiene su calificacion promediada

        restaurante.getCalificacionesRestaurante().add(nuevaCalificacion.getPromedioCalificacion()); // La calificacion se añade a la lista de califiaciones del restaurante

        //this.reserva.getMesero().getCalificaciones().add(calidadMesero); //La calificaión se añáde a lista de calificaciones del domiciliario

        //this.reserva.getMesero().actualizarDesempenoMesero(nuevaCalificacion); //se Actualiza el desempeño del domiciliario

        return nuevaCalificacion;
    }

    public String getNombre() {
        return super.getNombre();
    }

    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    public long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(long identificacion) {
        super.setIdentificacion(identificacion);
    }
    
 // Método para mostrar información del cliente
    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Identificación: " + identificacion);
    }

    // Método basico para ingresar datos del cliente (MODIFICABLE) 
    public void ingresarDatos() {
        Scanner scanner = new Scanner(System.in);

        // Ingreso del nombre
        System.out.print("Ingrese su nombre: ");
        this.nombre = scanner.nextLine();

        // Ingreso y validación de la identificación
        boolean identificacionValida = false;
        while (!identificacionValida) {
            try {
                System.out.print("Ingrese su identificación (número): ");
                this.identificacion = Long.parseLong(scanner.nextLine());
                identificacionValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: La identificación debe ser un número. Intente nuevamente.");
            }
        }
        System.out.println("Datos ingresados correctamente.");
    }

    public static ArrayList<Cliente> getClientes() {
        return clientes;
    }
}

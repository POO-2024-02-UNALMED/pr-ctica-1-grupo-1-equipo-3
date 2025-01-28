package gestorAplicacion;
import java.util.ArrayList;
import java.util.Comparator;
import baseDatos.Serializador;
import uiMain.CreacionPedido;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.Serializable;

public class Restaurante implements Serializable{
    private static final long serialVersionUID = 1L;
    private static ArrayList<Restaurante> restaurante = new ArrayList<Restaurante>();
    private String nombre;
    private double reputacion;
    private int totalCalificaciones;
    private int ingresos;
    private LocalTime horarioServicio;
    private ArrayList<Mesa> mesas;
    private static ArrayList<Integer> idConReservas = new ArrayList<>(); //Lista donde se guardaran los id de las reservas exitosas y se utilizara para confirmar la existencia de la reserva antes de hacer pedido
	private ArrayList<Mesero> meseros;
    private ArrayList<Double> calificacionesRestaurante = new ArrayList<>();
    private static ArrayList<Cliente> ListaClientes = new ArrayList<Cliente>();

    public Restaurante(String nombre) {
        this.nombre = nombre;
        Restaurante.ListaClientes = new ArrayList<>();
        restaurante.add(this);
    }

    public Restaurante(String nombre,LocalTime horarioServicio) {

        this.nombre = nombre;
        this.reputacion = 3.8;
        this.totalCalificaciones = calificacionesRestaurante.size() + 30;
        this.ingresos = 0;
        this.horarioServicio = horarioServicio;
        this.mesas = new ArrayList<>();
        this.meseros = new ArrayList<>();
        this.calificacionesRestaurante = new ArrayList<>();
        Restaurante.ListaClientes = new ArrayList<>(); 
        restaurante.add(this);
    }
    // Registrar visita del cliente
    public void registrarVisita(Cliente cliente) {
        cliente.incrementarVisitas();
      
    }
    //Determinar descuentos por numero de visitas 
    public int DeterminarDescuentos(Cliente cliente) {
    	cliente.incrementarVisitas();
    	int visitas = cliente.getVisitasParaDescuentos();
    	int descuento = 0;
    	if (visitas == 5) {
    		descuento = 10; //asigna descuento del 10% cuando el cliente visite 5 veces
    	}
    	else if (visitas == 10) {
    	   descuento = 15; //asigna descuento del 10% cuando el cliente visite 10 veces
    	}
    	return descuento;
    }

   
    //Actualiza la reputacion del restaurante respecto a una nueva calificacion
    public void actualizarReputacion(Calificacion calificacion) {
        double sumaAcumalada = this.reputacion * this.totalCalificaciones;
        totalCalificaciones++;
        this.reputacion = (Math.round(((sumaAcumalada + calificacion.getPromedioCalificacion()) / totalCalificaciones) * 10.0))/10.0; //prom nuevo en una cifra decimal
    }

  //Retorna un ArrayList con las mesas que estan disponibles para una fecha y hora determinada
    public ArrayList<Mesa> hacerReserva(LocalDateTime horario,int personas,String tipoMesa) {
    	ArrayList<Mesa> mesasDisponibles = new ArrayList<Mesa>();

        for (Mesa mesa : mesas) {
            boolean estado = mesa.estaDisponible(horario);
            int capacidad = mesa.getCapacidad();
            String tipo = mesa.getTipo();

            if (estado == true) {
                if (tipo.equals(tipoMesa)) {
                    if (capacidad == personas || capacidad == personas + 1) {
                        mesasDisponibles.add(mesa);
                    }
                }
            }
        }

        return mesasDisponibles;
    }

    //Valida que la fecha y hora ingresadas sean válidas
    public boolean validarFechaHora(String fecha, String hora) {
        String[] elementosFecha = fecha.split("/");
        String[] elementosHora = hora.split(":");

        int horaEntero = Integer.parseInt(elementosHora[0]);
        int minutos = Integer.parseInt(elementosHora[1]);
        
        if (elementosFecha.length == 3 && elementosHora.length == 2) {
        	if(horaEntero >= 7 && horaEntero <= 23) {
        		if(horaEntero == 23) {
        			if(minutos > 0) {
        				return false;
        			}
        		}
        		return true;
        	}
        }
        return false;
    }

    //Convierte un String fecha y String hora a formato LocalDateTime para que puedan ser usadas en otros métodos
    public LocalDateTime convertirFechaHora(String fecha, String tiempo) {
        //Fecha en formato dd/MM/AAAA y Hora HH:mm
        String[] diaMesAño = fecha.split("/");
        String[] horaMinutos = tiempo.split(":");

        int dia = Integer.parseInt(diaMesAño[0]);
        int mes = Integer.parseInt(diaMesAño[1]);
        int año = Integer.parseInt(diaMesAño[2]);
        int hora = Integer.parseInt(horaMinutos[0]);
        int minutos = Integer.parseInt(horaMinutos[1]);

        LocalDateTime fechaHora = LocalDateTime.of(año, mes, dia, hora, minutos);

        return fechaHora;
    }
        
    
    public void agregarMesa(Mesa mesa) {
        mesas.add(mesa);
    }
    
    //agregarMesero
    public void agregarMesero(Mesero mesero) {
        meseros.add(mesero);
    }
    
   //metodo para iniciar la creacion de un pedido
    public static void crearPedido(Restaurante restaurante) {
    	CreacionPedido.pedirId(restaurante);
    }
    	
   //metodo para agregar la identificación a la lista de reservas cuando esta sea realice exitosamente
    public void addIdConReserva(int id) {
        idConReservas.add(id); 
   }
        
   //metodo para eliminar la identificación de la lista de reservas una vez esta se complete
    public void removeIdConReserva(int id) {
        idConReservas.remove(Integer.valueOf(id));
    }
    
    //metodo para buscar si el id ingresado esta asociado a una reserva
    public static boolean BuscarId(int id) {
    	 for (Cliente cliente : Cliente.getClientes()) {
             if (cliente.getIdentificacion()==id) {
                 return true; //Si se encuentra el id
             }
         }
         return false; // Si no se encuentra el id
     }
    
    public static ArrayList<Integer> getIdConReservas() {
		return idConReservas;
	}

	public String getNombre() {
        return nombre;
    }

    public void setMesas(ArrayList<Mesa> mesas) {
        this.mesas = mesas;
    }

    public void setMeseros(ArrayList<Mesero> meseros) {
        this.meseros = meseros;
    }

    public ArrayList<Mesa> getMesas() {
        return mesas;
    }

    public ArrayList<Mesero> getMeseros() {
        return meseros;
    }

    public double getReputacion() {
        return reputacion;
    }

    public ArrayList<Double> getCalificacionesRestaurante() {
        return calificacionesRestaurante;
    }

    public static ArrayList<Cliente> getListaClientes() {
        return ListaClientes;
    }

    public void setReputacion(double reputacion) {
        this.reputacion = reputacion;
    }

    public static ArrayList<Restaurante> getRestaurante() {
        return restaurante;
    }

    public  void agregarCliente(Cliente cliente) {
        Restaurante.getListaClientes().add(cliente);
    }
    
    //valida si un cliente está en la lista de los clientes del restaurante por medio del id
    public boolean validarCliente(long id, Restaurante restaurante) {
        for (Cliente cliente : Restaurante.getListaClientes()) {
            if (cliente.getIdentificacion() == id) {
                return true;
            }
        }
        return false;
    }
    ////valida si un cliente está en la lista de los clientes del restaurante por medio del id y lo retorna
    public static Cliente retonarCliente(long id) {
        for (Cliente cliente : Restaurante.getListaClientes()) {
            if (cliente.getIdentificacion() == id) {
                return cliente; }
        }
		return null;
    }
    
    //Crea clientes nuevos y evita repetir clientes ya existentes
    public Cliente obtenerOcrearCliente(String nombre, long id) {
        Cliente clienteExistente = indicarCliente(id);

        if (clienteExistente != null) { //si el cliente ya existe
            System.out.println("Cliente encontrado: " + clienteExistente.nombre);
            return clienteExistente;
        } else { //si el cliente es nuevo
            Cliente nuevoCliente = new Cliente(nombre, id, this);
            System.out.println("Cliente creado: " + nuevoCliente.nombre);
            return nuevoCliente;
        }
    }
    
    //Verifica si un cliente existe y lo retorna según su id
    public Cliente indicarCliente(long id){
        for (Cliente cliente : getListaClientes()) {
            if (cliente.getIdentificacion() == id) {
                return cliente;
            }
        }
        return null;
    }
    
    public boolean obtenerTipoMesa(Reserva reserva) {
       if (reserva.getMesa().getTipo().equals("deluxe")) {
    	   return true;
       } else { return false; }
    }
}

package gestorAplicacion;
import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.Serializable;

public class Restaurante implements Serializable{

    private String nombre;
    private double reputacion;
    private int totalCalificaciones;
    private int ingresos;
    private LocalTime horarioServicio;
    private ArrayList<Mesa> mesas;
    private ArrayList<Integer> idConReservas = new ArrayList<>(); //Lista donde se guardaran los id de las reservas exitosas y se utilizara para confirmar la existencia de la reserva antes de hacer pedido
	private ArrayList<Mesero> meseros;
    private ArrayList<Double> calificacionesRestaurante = new ArrayList<>();
    private ArrayList<Cliente> ListaClientes;

    public Restaurante(String nombre) {
        this.nombre = nombre;
        this.ListaClientes = new ArrayList<>();
    }

    public Restaurante(String nombre,LocalTime horarioServicio) {

        this.nombre = nombre;
        this.reputacion = 0;
        this.totalCalificaciones = calificacionesRestaurante.size();
        this.ingresos = 0;
        this.horarioServicio = horarioServicio;
        this.mesas = new ArrayList<>();
        this.meseros = new ArrayList<>();
        this.calificacionesRestaurante = new ArrayList<>();
        this.ListaClientes = new ArrayList<>();
    }
    // Registrar visita del cliente
    public void registrarVisita(Cliente cliente) {
        cliente.incrementarVisitas();
        calcularPuntosPorFrecuencia(cliente);
    }

    // Calcular puntos por frecuencia de visita
    private void calcularPuntosPorFrecuencia(Cliente cliente) {
        int visitas = cliente.getVisitas();
        int puntosPorVisita = 10; // Definido arbitrariamente
        if (visitas > 5) {
            cliente.acumularPuntos("frecuencia", puntosPorVisita * 2);
        } else {
            cliente.acumularPuntos("frecuencia", puntosPorVisita);
        }
    }
     // Calcular puntos por gasto
     public void calcularPuntosPorGasto(Cliente cliente, double totalFactura) {
        int puntos = (int) totalFactura / 1000; // Asignar 1 punto por cada 1000 pesos colombianos
        cliente.acumularPuntos("gasto", puntos);
    }

    // Redimir puntos para reserva
    public boolean redimirPuntosParaReserva(Cliente cliente, int puntosNecesarios) {
        if (cliente.getPuntosGenerales() >= puntosNecesarios) {
            cliente.reducirPuntos("frecuencia", puntosNecesarios);
            return true;
        }
        return false;
    }
    // Redimir puntos para productos
    public boolean redimirPuntosParaProducto(Cliente cliente, int puntosNecesarios) {
        if (cliente.getPuntosGenerales() >= puntosNecesarios) {
            cliente.reducirPuntos("gasto", puntosNecesarios);
            return true;
        }
        return false;
    }

    // Redimir puntos para servicios exclusivos
    public boolean redimirPuntosParaServicio(Cliente cliente, int puntosNecesarios) {
        if (cliente.getPuntosGenerales() >= puntosNecesarios) {
            cliente.reducirPuntos("especiales", puntosNecesarios);
            return true;
        }
        return false;
    }


    public void actualizarReputacion(Calificacion calificacion) {
        double sumaAcumalada = this.reputacion * this.totalCalificaciones;
        reputacion = (Math.round((sumaAcumalada + calificacion.getPromedioCalificacion() / totalCalificaciones) * 10.0))/10.0; //prom nuevo en una cifra decimal
    }

  //Retorna un ArrayList con las mesas que estan disponibles para una fecha y hora determinada
    public ArrayList<Mesa> hacerReserva(LocalDateTime horario,int personas,String tipoMesa) {
    	ArrayList<Mesa> mesasDisponibles = new ArrayList<Mesa>();

        for (Mesa mesa : mesas) {
            boolean estado = mesa.estaDisponible(horario);
            int capacidad = mesa.getCapacidad();
            String tipo = mesa.getTipo();

            if (estado == true) {
                if (mesa.getTipo().equals(tipo)) {
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

        if (elementosFecha.length == 3 && elementosHora.length == 2) {
            return true;
        } else {
            return false;
        }
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

    public ArrayList<Cliente> getListaClientes() {
        return ListaClientes;
    }

    public void setReputacion(double reputacion) {
        this.reputacion = reputacion;
    }

    public  void agregarCliente(Cliente cliente) {
        this.getListaClientes().add(cliente);
    }
    
    public ArrayList<Integer> getIdConReservas() {
		return idConReservas;
	}

	public void setIdConReservas(ArrayList<Integer> idConReservas) {
		this.idConReservas = idConReservas;
	}
	
	//metodo para agregar la identificación a la lista de reservas cuando esta sea realice exitosamente
    public void addIdConReserva(int id) {
    	idConReservas.add(id); 
    }
    
   //metodo para eliminar la identificación de la lista de reservas una vez esta se complete
    public void removeIdConReserva(int id) {
        idConReservas.remove(Integer.valueOf(id));
    }
    
    //valida si un cliente está en la lista de los clientes del restaurante por medio del id
    public boolean validarCliente(int id, Restaurante restaurante) {
        for (Cliente cliente : restaurante.getListaClientes()) {
            if (cliente.getIdentificación() == id) {
                return true;
            }
        }
        return false;
    }

    //Retorna un cliente según su id
    public Cliente indicarCliente(int id, Restaurante restaurante){
        for (Cliente cliente : restaurante.getListaClientes()) {
            if (cliente.getIdentificación() == id) {
                return cliente;
            }
        }
        return null;
    }
}

package gestorAplicacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uiMain.Utilidad;
import java.util.AbstractMap;

public class Pedido implements Serializable{
    private static final long serialVersionUID = 1L;
    private static ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
    private Restaurante restaurante;
    private Cliente titular;
    private Factura factura;
    private List<Map.Entry<String, Integer>> pedidoListaTuplas = new ArrayList<>();
	private Calificacion calificacion;
    private double promCalificacion;
    private String platoCortesia = "No aplica";
    private long descuento;
    private long valorSinDescuento;
    private long valorConDescuento;
  
    public Pedido(Cliente cliente, Factura factura, Restaurante restaurante) {
        this.titular = cliente;
        this.factura = factura;
        this.restaurante = restaurante;
        pedidos.add(this);
    }
    public Pedido(Cliente cliente) {
    	this.titular = cliente;
    }
    
    //metodo para agregar elementos al pedido
    public void agregarAlPedido(String nombrePlato, int cantidad) {
        pedidoListaTuplas.add(new AbstractMap.SimpleEntry<>(nombrePlato, cantidad));
    }
    
    public void calcularValorDelPedido(List<Map.Entry<String, Integer>> ListaDeTuplas) {
    int subtotal = 0; 
        //recorrer la lista de tuplas del pedido
        for (Map.Entry<String, Integer> item : ListaDeTuplas) {
            String plato = item.getKey();
            int cantidad = item.getValue();
         // Buscar el plato en el menú y calcular el costo
            for (Menu menu : Menu.values()) {
                if (menu.name().equalsIgnoreCase(plato)) {
                    subtotal += menu.getPrecio() * cantidad;
                    break;
                }
            }
        }
        setValorSinDescuento(subtotal);
        //System.out.println("El subtotal de tu pedido es: " + Utilidad.formatoPrecio(subtotal));
    }
    
    public void realizarDescuentos(long total, int descuento) {
    	if (descuento != 0) { 
    		long descuentoAplicado = (total*descuento)/100;
    		long nuevoValorPedido = total-descuentoAplicado;
    		setDescuento(descuentoAplicado);
    		setValorConDescuento(nuevoValorPedido);
    		//System.out.println("El valor del descuento es: " + Utilidad.formatoPrecio(descuentoAplicado));
    		//System.out.println("El costo total de tu pedido es: " + Utilidad.formatoPrecio(nuevoValorPedido));
    	} else {
    	 setDescuento(0);
		 setValorConDescuento(total); }
    }
    
     public void imprimirResumenPedido(List<Map.Entry<String, Integer>> listaDeTuplas) {
     double costoTotal = 0;

     System.out.println("\nResumen del pedido:");
     System.out.println("--------------------------------------------------");
     System.out.println("Producto         Cantidad     Precio Unitario    Precio Total");
     System.out.println("--------------------------------------------------");

     // Recorrer la lista de tuplas para generar la parte de la factura de los platos
     for (Map.Entry<String, Integer> item : listaDeTuplas) {
         String plato = item.getKey();
         int cantidad = item.getValue();

         for (Menu menu : Menu.values()) {
           if (menu.name().equalsIgnoreCase(plato)) {
               double precioUnitario = menu.getPrecio();
               double precioTotal = precioUnitario * cantidad;
               costoTotal += precioTotal;

               // Utilizar Utilidad.formatoPrecio() para dar formato a los precios
               String precioUnitarioFormat = Utilidad.formatoPrecio(precioUnitario);
               String precioTotalFormat = Utilidad.formatoPrecio(precioTotal);

               // Imprimir detalles del producto
               System.out.printf("%-15s %-10d %-15s %-15s%n",
               menu.getNombre(), cantidad, precioUnitarioFormat, precioTotalFormat);
               break; }
         }
      }
            System.out.println("--------------------------------------------------");
            System.out.println("PLATO DE CORTESIA: " + platoCortesia);
            System.out.println("--------------------------------------------------");
            
           if (descuento!=0) {
        	   String descuentoFormat = Utilidad.formatoPrecio(descuento);
        	   String valorSinDescuentoFormat = Utilidad.formatoPrecio(valorSinDescuento);
        	   System.out.println("INFORMACION DE LOS DESCUENTOS POR FIDELIDAD:\n");
        	   System.out.println("subtotal de los platos: " + valorSinDescuentoFormat);
        	   System.out.println("Tienes un descuento por numero de visitas del " + titular.getDescuentoPorVisitas() + "%");
        	   System.out.println("Valor del descuento: " + descuentoFormat);
        	   costoTotal = valorConDescuento;
           }
            
           System.out.println("--------------------------------------------------");
           String costoTotalFormat = Utilidad.formatoPrecio(costoTotal);
            System.out.println("Costo Total de los platos: " + costoTotalFormat);
}
           
	//Este metodo se encarga de tomar la calificacion del tiempo de espera del cliente para determinar la calificacion general del restaurante
    public void tiempoEsperaRestaurante(Calificacion calificacion){
        double tiempoEspera = calificacion.getTiempoEspera();
        if (tiempoEspera < 3) {
            this.restaurante.setReputacion(this.restaurante.getReputacion()-0.1);
        }
    }
    
    public Cliente getTitular() {
		return titular;
	}
	public void setTitular(Cliente titular) {
		this.titular = titular;
	}
	public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public double getPromCalificacion() {
        return promCalificacion;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public void promediarCalificacion(Calificacion calificacion) {
        this.promCalificacion = calificacion.calcularPromCalificacionDomicilio();
    }

    public static ArrayList<Pedido> getPedidos() {
        return pedidos;
    }
    
    public List<Map.Entry<String, Integer>> getPedidoListaTuplas() {
		return pedidoListaTuplas;
	}
	public void setPedidoListaTuplas(List<Map.Entry<String, Integer>> pedidoListaTuplas) {
		this.pedidoListaTuplas = pedidoListaTuplas;
	}
	
	public String getPlatoCortesia() {
		return platoCortesia;
	}
	public void setPlatoCortesia(String string) {
		this.platoCortesia = string;
	}
	public long getDescuento() {
		return descuento;
	}
	public void setDescuento(long descuento) {
		this.descuento = descuento;
	}
	public long getValorSinDescuento() {
		return valorSinDescuento;
	}
	public void setValorSinDescuento(long valorSinDescuento) {
		this.valorSinDescuento = valorSinDescuento;
	}
	public long getValorConDescuento() {
		return valorConDescuento;
	}
	public void setValorConDescuento(long valorConDescuento) {
		this.valorConDescuento = valorConDescuento;
	}
}

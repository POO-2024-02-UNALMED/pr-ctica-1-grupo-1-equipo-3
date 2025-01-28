package uiMain;
import gestorAplicacion.*;
import gestorAplicacion.Menu.MenuCortesias;
import java.io.Serializable;
import baseDatos.Serializador;
import baseDatos.Deserializador;
import java.util.*;

public class CreacionPedido implements Serializable {
	private static final long serialVersionUID = 1L;
	private static ArrayList<CreacionPedido> creacionPedidos = new ArrayList<CreacionPedido>();
	private static boolean ofrecerCortesia;
	private static Scanner scanner = new Scanner(System.in);
	private Cliente cliente;
	
	//metodo para pedir el id y confirmar la existencia de la reserva
	public static void pedirId(Restaurante restaurante) {
	int id = 0;
	// activar si se necesita ver la lista de reservas: System.out.println("lista de reservas: " + Restaurante.getIdConReservas()+"\n");
	while (true) {
		System.out.println("\ningrese el numero de identificacion asociado a su reserva:");
	String input = scanner.nextLine();
	
	//verifica que la entrada sea solo digitos
	if (input.matches("\\d+")) {
		id = Integer.parseInt(input);
		boolean reservaEncontrada = Restaurante.BuscarId(id); //llama el metodo BuscarId para confirmar si está o no en la lista de reservas
		if (reservaEncontrada==true) { //Si el id se encuentra
			Cliente clienteConReserva = Restaurante.retonarCliente(id); //retorna el objeto cliente asociado al id ingresado
			int descuento = restaurante.DeterminarDescuentos(clienteConReserva); //incrementa las visitas del cliente y determina si cumple con las necesarias para obtener un descuento
			clienteConReserva.setDescuentoPorVisitas(descuento);//asigna los descuentos@
			if (descuento == 0) {System.out.println("\nReserva encontrada! Bienvenid@ "+ 
	                   clienteConReserva.getNombre() + "\nesta es su visita #" +
			           clienteConReserva.getVisitas() + " a " + restaurante.getNombre() +
			           "\n(recuerde que tenemos descuentos por fidelidad cada 5 visitas)");
			
			} else {
			System.out.println("\nReserva encontrada! Bienvenid@ "+ 
			                   clienteConReserva.getNombre() + "\npor ser tu visita #" +
					           clienteConReserva.getVisitas() + " obtendrás un descuento del " +
					           clienteConReserva.getDescuentoPorVisitas() +
					           "% sobre el total del pedido"); }
			 try {
		         Thread.sleep(2000); } 
			 catch (InterruptedException e) {
		         e.printStackTrace(); }
			//Para determinar si la mesa asociada a la reserva actual es basic o deluxe
		    Reserva reserva = clienteConReserva.getReserva();
			ofrecerCortesia = restaurante.obtenerTipoMesa(reserva);
				
			completarInfoPedido(clienteConReserva); //llama al metodo que se encarga de manejar toda la informacion del pedido 
			return;} 
		
		else { //Si el id no se encuentra
			System.out.println("No se ha encontrado el numero de identificacion ingresado en la lista de reservas\nRedirigiendo al menú principal...");
			 try {
		         Thread.sleep(1500); } 
			 catch (InterruptedException e) {
		         e.printStackTrace(); }
			 
			Main.menuPrincipal(restaurante); //Para volver al menu principal
			return; }
	 }
	
	else {
		System.out.println("La identificacion ingresada no es valida, intentelo de nuevo");
	    try {
            Thread.sleep(1000); } 
	    catch (InterruptedException e) {
            e.printStackTrace(); }
        }
    }

}

	public static void completarInfoPedido(Cliente cliente) {
		Deserializador.deserializarListas();
	Pedido pedido = new Pedido(cliente, Restaurante.getRestaurante().get(0)); //asignarle titular al pedido
		Serializador.serializarListas();
	creacionDePedido(pedido);
	pedido.calcularValorDelPedido(pedido.getPedidoListaTuplas());
	pedido.realizarDescuentos(pedido.getValorSinDescuento(), cliente.getDescuentoPorVisitas());
	imprimirResumenPedido(pedido.getPedidoListaTuplas(), pedido);

	Main.menuPrincipal(pedido.getRestaurante()); //Para volver al menu principal



	}
	
	//metodo para crear el pedido
	public static void creacionDePedido(Pedido pedido) {
	Almacen almacen = new Almacen();
	 boolean seguirPidiendo = false;
     boolean primerAlimentoAñadido = false; //verifica que al menos se pida un plato y el pedido no vaya ser vacio
	while (!primerAlimentoAñadido || seguirPidiendo) {
        System.out.println("\nMenú disponible:");
        Menu[] menuItems = Menu.values();

        for (int i = 0; i < menuItems.length; i++) {
            System.out.printf("%d. %s - Precio: %s%n", 
                i + 1, 
                menuItems[i].getNombre(), 
                Utilidad.formatoPrecio(menuItems[i].getPrecio()));
        }

        int seleccion;
        while (true) {
            System.out.print("\nSeleccione el número del plato que desea pedir: ");
            try {
                seleccion = scanner.nextInt();
                if (seleccion >= 1 && seleccion <= menuItems.length) {
                    break; }
                else {
                    System.out.println("Numero fuera del rango indicado, "
                    		+ "intentelo de nuevo.\n"); }
            } 
            catch (InputMismatchException e) {
                System.out.println("Entrada inválida. La entrada debe ser "
                		+ "un valor numerico, intentelo de nuevo.\n");
                scanner.nextLine(); // Consumir la entrada inválida
            }
        }
        
        Menu menuSeleccionado = menuItems[seleccion - 1];

        if (almacen.verificarDisponibilidad(menuSeleccionado)) {
        	int unidades;
            while (true) {
                System.out.print("Ingrese cuantas unidades desea pedir: ");
                try {
                    unidades = scanner.nextInt();
                    scanner.nextLine(); // Consumir salto de línea
                    if (unidades > 0) {
                        break;
                    } else {
                        System.out.println("La entrada debe ser positiva,"
                        		+ " intentelo de nuevo.\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. La entrada debe ser"
                    		+ " un número entero. Intente nuevamente.\n");
                    scanner.nextLine(); // Consumir la entrada inválida
                }
            }
            
            almacen.actualizarInventario(menuSeleccionado, unidades);  //comprobar si hay ingredientes suficientes para el plato pedido
            if (almacen.verificarDisponibilidad(menuSeleccionado)) {
            	// Agregar la entrada a la lista de tuplas
                pedido.agregarAlPedido(menuSeleccionado.name(), unidades);
                primerAlimentoAñadido = true;
                System.out.println("El plato fue agregado a su pedido.");
            } else { System.out.println("Lo sentimos, no hay suficientes ingredientes "
            		+ "disponibles para agregar esa cantidad de platos al pedido.");
                    almacen.revertirInventario(menuSeleccionado, unidades);} //revierte las cantidades del inventario sino se puede preparar el plato
   
        } else {
            System.out.println("Lo sentimos, no hay suficientes ingredientes "
            		+ "disponibles para agregar esa cantidad de platos al pedido. "); 
               }
        
        if (primerAlimentoAñadido) {
            System.out.print("\n¿Desea agregar otro alimento a su pedido? (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            seguirPidiendo = respuesta.equals("S");
        } else { System.out.println("Seleccione nuevamente"); }
	}
	

	//si la mesa es deluxe se ofrece un plato de cortesia
	if (ofrecerCortesia) {
	 System.out.println("\n¡Su reserva con mesa tipo deluxe incluye un plato "
	 		+ "de cortesia!\nMenu de cortesias: ");
	 MenuCortesias[] menuItems = MenuCortesias.values(); 
	    for (int i = 0; i < menuItems.length; i++) {
	        System.out.printf("%d. %s%n", i + 1, menuItems[i].getNombre());
	    }
	    System.out.printf("\nIngrese el numero del plato de cortesia deseado: ");

	    int seleccionado;
	    while (true) {
	        try {
	            seleccionado = scanner.nextInt();
	            if (seleccionado >= 1 && seleccionado <= menuItems.length) {
	                break; 
	            } else {
	                System.out.println("Número fuera del rango indicado, inténtelo de nuevo.\n"); 
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Entrada inválida. La entrada debe ser un valor numérico, inténtelo de nuevo.\n");
	            scanner.nextLine(); // Consumir la entrada inválida
	        }
	    }

	    MenuCortesias platoSeleccionado = menuItems[seleccionado - 1];
	    System.out.printf("Su plato de cortesia seleccionado es: " + platoSeleccionado.getNombre()+ "\n");
	    pedido.setPlatoCortesia(platoSeleccionado.getNombre());
	    try {
            Thread.sleep(1500); } 
	    catch (InterruptedException e) {
            e.printStackTrace(); }
	}
	Serializador.serializarListas();
}
	
    public static void imprimirResumenPedido(List<Map.Entry<String, Integer>> listaDeTuplas, Pedido pedido) {
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
           System.out.println("PLATO DE CORTESIA: " + pedido.getPlatoCortesia());
           
          if (pedido.getDescuento()!=0) {
       	   String descuentoFormat = Utilidad.formatoPrecio(pedido.getDescuento());
       	   String valorSinDescuentoFormat = Utilidad.formatoPrecio(pedido.getValorSinDescuento());
       	   System.out.println("--------------------------------------------------");
       	   System.out.println("INFORMACION DE LOS DESCUENTOS POR FIDELIDAD:\n");
       	   System.out.println("subtotal de los platos: " + valorSinDescuentoFormat);
       	   System.out.println("Tienes un descuento por numero de visitas del " + pedido.getTitular().getDescuentoPorVisitas() + "%");
       	   System.out.println("Valor del descuento: " + descuentoFormat);
       	   costoTotal = pedido.getValorConDescuento();
          }
          System.out.println("--------------------------------------------------");
          System.out.print("COBROS ASOCIADOS A LA RESERVA: ");
          if (ofrecerCortesia && pedido.getFactura().getTotalFactura() - pedido.getValorConDescuento() == 80000) {
        	    System.out.println("\nValor de reserva con mesa tipo deluxe: 30.000");
        	    System.out.println("Valor de reserva creada con más de un mes de anticipación: 50.000");
        	} else if (ofrecerCortesia) {
        	    System.out.println("\nValor de reserva con mesa tipo deluxe: 30.000");
        	} else if (pedido.getFactura().getTotalFactura() - pedido.getValorConDescuento() == 50000) {
        	    System.out.println("\nValor de reserva creada con más de un mes de anticipación: 50.000");
        	} else {
        	    System.out.println("No aplica");
        	}
          System.out.println("--------------------------------------------------");
          String costoTotalFormat = Utilidad.formatoPrecio(costoTotal);
          System.out.println("Costo Total de los platos: " + costoTotalFormat);
          String costoTotal2Format = Utilidad.formatoPrecio(pedido.getFactura().getTotalFactura());
          System.out.println("Costo platos + Cobros de reserva: " + costoTotal2Format);
          try {
              Thread.sleep(2000); } 
  	    catch (InterruptedException e) {
              e.printStackTrace(); }
          System.out.println("\nGracias por realizar su pedido! "
          		+ "\nlo invitamos a dejar su calificacion del servicio luego de su consumo");

          try {
              Thread.sleep(2000); } 
  	    catch (InterruptedException e) {
              e.printStackTrace(); }
		Serializador.serializarListas();
}
		//Getter

	public static ArrayList<CreacionPedido> getCreacionPedidos() {
		return creacionPedidos;
	}
}





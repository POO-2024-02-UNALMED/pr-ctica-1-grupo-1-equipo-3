package uiMain;

import gestorAplicacion.Cliente;
import gestorAplicacion.Pedido;
import gestorAplicacion.Restaurante;
import java.util.Scanner;

public class CreacionPedido {
	private static Scanner scanner = new Scanner(System.in);

	//metodo para pedir el id y confirmar la existencia de la reserva
	public static void pedirId(Restaurante restaurante) {
	int id = 0;
	
	while (true) {
	System.out.println("\ningrese el numero de identificacion asociado a su reserva");
	String input = scanner.nextLine();
	
	//verifica que la entrada sea solo digitos
	if (input.matches("\\d+")) {
		id = Integer.parseInt(input);
		
		System.out.println(Restaurante.getListaClientes());
		System.out.println(Restaurante.getIdConReservas()+"\n");
		boolean reservaEncontrada = Restaurante.BuscarId(id); //llama el metodo BuscarId para confirmar si está o no en la lista de reservas
		if (reservaEncontrada==true) { //Si el id se encuentra
			Cliente clienteConReserva = Restaurante.retonarCliente(id); //retorna el objeto cliente asociado al id ingresado
			int descuento = restaurante.DeterminarDescuentos(clienteConReserva); //incrementa las visitas del cliente y determina si cumple con las necesarias para obtener un descuento
			clienteConReserva.setDescuentoPorVisitas(descuento);//asigna los descuentos
			
			if (descuento == 0) {System.out.println("\nReserva encontrada! Bienvenido "+ 
	                   clienteConReserva.getNombre() + "\nesta es su visita #" +
			           clienteConReserva.getVisitas() + " a " + restaurante.getNombre() +
			           "\n(recuerde que tenemos descuentos por fidelidad cada 5 visitas)");
			} else {
			System.out.println("\nReserva encontrada! Bienvenid@ "+ 
			                   clienteConReserva.getNombre() + "\npor ser la visitas #" +
					           clienteConReserva.getVisitas() + " obtendrás un descuento del " +
					           clienteConReserva.getDescuentoPorVisitas() +
					           "% sobre el total del pedido"); }
			//
			asignarTitular(id); //llama al metodo asignar titular 
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
		System.out.println("La identificacion ingresa no es valida, intentelo de nuevo");
	    try {
            Thread.sleep(1000); } 
	    catch (InterruptedException e) {
            e.printStackTrace(); }
        }
    }
}
	 
	//Restaurante.retonarCliente(id);
	 
   
	public static void asignarTitular(long id) {
	Pedido pedido = new Pedido(null, null, null);
	pedido.setTitular(Restaurante.retonarCliente(id)); 
}
}


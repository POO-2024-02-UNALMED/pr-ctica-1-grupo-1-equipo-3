package uiMain;

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
		System.out.println(Restaurante.getIdConReservas());
		boolean reservaEncontrada = Restaurante.BuscarId(id); //llama el metodo BuscarId para confirmar si está o no en la lista de reservas
		if (reservaEncontrada==true) { //Si el id se encuentra
			//...
			System.out.println("idendificacion encontrada "+id);
			break;
			
		} else { //Si el id no se encuentra
			System.out.println("No se ha encontrado el numero de identificacion ingresado en la lista de reservas\nRedirigiendo al menú principal...");
			 try {
		         Thread.sleep(1500);
		     } catch (InterruptedException e) {
		         e.printStackTrace();
		     }
			Main.menuPrincipal(restaurante); //Para volver al menu principal
			return;
		}
	 }
	
	else {
		System.out.println("La identificacion ingresa no es valida, intentelo de nuevo");
	    try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
	
	
}
}

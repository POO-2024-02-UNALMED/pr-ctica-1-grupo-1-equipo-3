 package uiMain;
import java.util.Scanner;
import gestorAplicacion.*;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main implements Utilidad {

    public static void main(String[] args) {
        //Creacion del restaurante
        Restaurante restaurante = new Restaurante("Aura Gourmet",LocalTime.of(7, 0));

        //Creación de las mesas
        Mesa mesa1 = new Mesa(1, 2, "basic", restaurante);
        Mesa mesa2 = new Mesa(2, 4, "deluxe", restaurante);
        Mesa mesa3 = new Mesa(3, 6, "basic", restaurante);
        Mesa mesa4 = new Mesa(4, 4, "basic", restaurante);
        Mesa mesa5 = new Mesa(5, 6, "deluxe", restaurante);
        Mesa mesa6 = new Mesa(6, 2, "deluxe", restaurante);
        Mesa mesa7 = new Mesa(7, 4, "basic", restaurante);
        Mesa mesa8 = new Mesa(8, 6, "basic", restaurante);
        Mesa mesa9 = new Mesa(9, 4, "deluxe", restaurante);
        Mesa mesa10 = new Mesa(10, 2, "deluxe", restaurante);
        
        //Creación de los meseros
        Mesero mesero1 = new Mesero(1, "Carlos Martínez", 4, 20, restaurante);
        Mesero mesero2 = new Mesero(2, "Juan Pérez", 3, 15, restaurante);
        Mesero mesero3 = new Mesero(3, "Andrés Gómez", 5, 25, restaurante);
        Mesero mesero4 = new Mesero(4, "Laura Rodríguez", 4, 18, restaurante);
        Mesero mesero5 = new Mesero(5, "Ana María Sánchez", 2, 10, restaurante);
        Mesero mesero6 = new Mesero(6, "Luis Fernández", 3, 14, restaurante);
        Mesero mesero7 = new Mesero(7, "Valentina Díaz", 4, 22, restaurante);
        Mesero mesero8 = new Mesero(8, "Santiago Ramírez", 5, 30, restaurante);
        Mesero mesero9 = new Mesero(9, "Mariana Gómez", 3, 16, restaurante);
        Mesero mesero10 = new Mesero(10, "Felipe Morales", 4, 19, restaurante);
        
        menuPrincipal(restaurante);
    }

    static void menuPrincipal(Restaurante restaurante){
        boolean encendido = true;
        do {
            System.out.print("¿Qué desea hacer?" + "\n" +
                                "1. Realizar una reserva." + "\n" +
                                "2. Realizar un domicilio." + "\n" +
                                "3. Realizar un pedido." + "\n" +
                                "4. Realizar un pago." + "\n" +
                                "5. Calificar el servicio." + "\n" +
                                "6. Salir." + "\n" +
                                "Ingrese un número para elegir una opción: ");
            Scanner valorEntrada = new Scanner(System.in);
            int eleccion = 0;
            eleccion = valorEntrada.nextInt();
            switch (eleccion) {
                case 1:
                	reservar(restaurante);
                    encendido = false;
                    break;
                case 2:
                    //encadenar la funcionalidad 2
                    encendido = false;
                    break;
                case 3:
                    //encadenar la funcionalidad 3
                    encendido = false;
                    break;
                case 4:
                    //encadenar la funcionalidad 4
                    encendido = false;
                    break;
                case 5:
                    calificar(restaurante);
                    encendido = false;
                    break;
                case 6:
                    //cerrar el prograama
                    encendido = false;
                    break;
            }
        } while(encendido);


    }

    //FUNCIONALIDAD 1
    public static void reservar(Restaurante restaurante) {
    	
    	//Recopilación de información
    	Scanner scannerFuncionalidad1 = new Scanner(System.in);
    	System.out.println("Bienvenido/a al restaurante "+ restaurante.getNombre()+", por favor digite la siguiente información para realizar su reserva");
    	System.out.println("Ingrese su nombre: ");
    	String nombre = scannerFuncionalidad1.nextLine();
    	
    	System.out.println("Ingrese su número de identificacion(Sin espacios, puntos o comas, sólo el número: ");
    	int identificacion = scannerFuncionalidad1.nextInt();
    	scannerFuncionalidad1.nextLine();
    	
    	System.out.println("Número de personas: ");
    	int numeroPersonas = scannerFuncionalidad1.nextInt();
    	scannerFuncionalidad1.nextLine();
    	
    	System.out.println("Mesa deluxe o normal (escriba el tipo de mesa en minúsculas, por favor): ");
    	System.out.println("Mesa deluxe con costo adicional de $30.000");
    	String tipoMesa = scannerFuncionalidad1.nextLine();
    	  
    	boolean reservaExitosa = false;
		do {
			String fecha;
			String hora;
			boolean fechaHoraValida;

			do {
				System.out.println("Fecha de la reserva (Por favor, ingrese la fecha en formato dia/mes/año, incluyendo el '/'): ");
				fecha = scannerFuncionalidad1.nextLine();

				System.out.println("Hora de la reserva (Por favor, ingrese la hora en formato militar así; hora:minutos, incluyendo el ':'): ");
				System.out.println("Solo tenemos servicio de 7:00 a 23:00");
				hora = scannerFuncionalidad1.nextLine();

				fechaHoraValida = restaurante.validarFechaHora(fecha, hora);
				if (!fechaHoraValida) {
					System.out.println(
							"Fecha u hora mal ingresadas, inténtelo de nuevo (tenga en cuenta las recomendaciones).");
				}
			} while (!fechaHoraValida);

			LocalDateTime fechaReserva = restaurante.convertirFechaHora(fecha, hora);

			// Pruebas
			System.out.println(nombre);
			System.out.println(identificacion);
			System.out.println(numeroPersonas);
			System.out.println(tipoMesa);
			System.out.println(fechaReserva);

			ArrayList<Mesa> mesasDisponibles = restaurante.hacerReserva(fechaReserva, numeroPersonas, tipoMesa);

			if (mesasDisponibles.isEmpty()) {
				System.out.println("No hay mesas disponibles para las especificaciones dadas.");
			} else {
				System.out.println("Mesas disponibles: (ingrese el numero de la mesa que desea escoger)");
				ArrayList<Integer> numeroMesas = new ArrayList<>();

				for (Mesa i : mesasDisponibles) {
					System.out.println("Mesa " + i.getNumero());
					numeroMesas.add(i.getNumero());
				}

				int numeroMesaEscogida = scannerFuncionalidad1.nextInt();
				scannerFuncionalidad1.nextLine();

				while (!numeroMesas.contains(numeroMesaEscogida)) {
					System.out.println("Numero de mesa incorrecto, escoja una de las mesas disponibles");
					numeroMesaEscogida = scannerFuncionalidad1.nextInt();
				}

				Mesa mesaEscogida = null;

				for (Mesa i : mesasDisponibles) {
					if (i.getNumero() == numeroMesaEscogida) {
						mesaEscogida = i;
					}
				}

				LocalDateTime fechaActual = LocalDateTime.now();
				Reserva reserva = new Reserva(mesaEscogida, fechaReserva, numeroPersonas, fechaActual);
				boolean meseroAsignado = mesaEscogida.reservar(reserva);

				if (meseroAsignado == false) {
					System.out.println("Lo sentimos, no hay meseros que puedan atender su reserva a la hora indicada");
					reservaExitosa = false;
				} else {

					Cliente cliente = new Cliente(nombre, identificacion, reserva, restaurante);

					// Asignación de clases
					reserva.setCliente(cliente);

					if (reserva.getMesa().getTipo()=="deluxe") {
						System.out.println("Ingrese el tipo de decoracion que desea: elegante, rústico o moderno");
						String estilo = scannerFuncionalidad1.nextLine();
						reserva.getMesa().setDecoracion(estilo);
						
						System.out.println("Desea agregar 1 hora a su reserva?(S/N)");
						String respuesta = scannerFuncionalidad1.nextLine();
						
						if (respuesta == "S") {
							boolean validacion = mesaEscogida.estaDisponible(fechaReserva.plusHours(1));
							if (validacion == true) {
								System.out.println("Hora añadida con éxito");
								int recargoReserva = reserva.getRecargo();
								reserva.setRecargo(recargoReserva + 30000);
							}else {
								System.out.println("Lo sentimos, la hora adicional interrumpe otra reserva, la decoración será cortesía de la casa");
							}
						}
					}
					
					//Interaccion si el usuario tiene restricciones alimentarias
					System.out.println("¿Desea agregar restricciones alimentarias a ingredientes específicos?(S/N)");
					String respuesta2 = scannerFuncionalidad1.nextLine();
					
					if(respuesta2 == "S") {
						// Muestra los ingredientes únicos.
			            System.out.println("Estos son los ingredientes disponibles en nuestros platos:");
			            String[] todosIngredientes = Menu.obtenerTodosLosIngredientes();
			            for (String ingrediente : todosIngredientes) {
			                if (!Menu.ingredienteEstaDuplicado(ingrediente, todosIngredientes)) {
			                    System.out.println(ingrediente);
			                }
			            }

			            // Solicitar restricciones del usuario.
			            System.out.println("Por favor, ingrese los ingredientes a los que es alérgico, separados por comas:");
			            String[] alergias = scannerFuncionalidad1.nextLine().split(",");

			            // Elimina espacios en los ingredientes ingresados.
			            for (int i = 0; i < alergias.length; i++) {
			                alergias[i] = alergias[i].trim();
			            }

			            // Recomendaciones.
			            System.out.println("\nRecomendaciones de platos que no contienen los ingredientes restringidos:");
			            for (Menu plato : Menu.values()) {
			                if (!Menu.platoContieneAlergia(plato, alergias)) {
			                    System.out.println("- " + plato.getNombre() + " ($" + plato.getPrecio() + ")");
			                }
			            }
			        }else {
			        	// Recomendación de platos más caros.
			            System.out.println("\nNo ha indicado alergias. Aquí están los platos más caros de nuestro menú:");
			            Menu[] platosOrdenados = Menu.values();

			            // Ordenar manualmente los platos por precio de mayor a menor.
			            for (int i = 0; i < platosOrdenados.length - 1; i++) {
			                for (int j = i + 1; j < platosOrdenados.length; j++) {
			                    if (platosOrdenados[i].getPrecio() < platosOrdenados[j].getPrecio()) {
			                        // Intercambiar los elementos
			                        Menu temp = platosOrdenados[i];
			                        platosOrdenados[i] = platosOrdenados[j];
			                        platosOrdenados[j] = temp;
			                    }
			                }
			            }

			            // Imprimir los platos más caros.
			            for (Menu plato : platosOrdenados) {
			                System.out.println("- " + plato.getNombre() + " ($" + plato.getPrecio() + ")");
			            }
			        }
					
					System.out.println("Reserva realizada con éxito");
					reservaExitosa = true;
				}
			}
		} while (!reservaExitosa);
    }

    //FUNCIONALIDAD 5
    public static void calificar(Restaurante restaurante) {
        Scanner valorEntrada1 = new Scanner(System.in);
        boolean encendido1 = true;
        int idCliente = 0;
        int calidadComida = 0;
        int calidadMesero = 0;
        int tiempoEspera = 0;
        String comentario = "";
        do {
            System.out.print("¿Desea relizar una calificación?" + "\n" +
                                "1. Sí." + "\n" +
                                "2. No." + "\n" +
                                "Ingrese un número para elegir una opción: ");
            int eleccion1 = 0;
            eleccion1 = valorEntrada1.nextInt();
            switch (eleccion1) {
                case 1:
                    boolean encendido2 = true;
                    do {
                        System.out.print("Ingrese su identificación para realizar la calificación: ");
                        idCliente = valorEntrada1.nextInt();
                        boolean validarCliente = restaurante.validarCliente(idCliente, restaurante);
                        if (validarCliente){   //validar que la id sea de un cliente
                            encendido2 = false;
                        }else{
                            System.out.print("La identificación no está asociada a ningún cliente" + "\n" +
                                    "1. Deseo ingresar una identificación valida." + "\n" +
                                    "2. Salir." + "\n" +
                                    "Ingrese un número para elegir una opción: ");
                            int eleccion2 = valorEntrada1.nextInt();
                            switch (eleccion2){
                                case 1:
                                    encendido2 = true;
                                    break;
                                case 2:
                                    menuPrincipal(restaurante);
                                    encendido2 = false;
                                    encendido1 = false;
                                    break;
                            }
                        }
                    }while(encendido2);
                    System.out.println("Para realizar la calificación porfavor conteste la siguiente encuesta:" + "\n" +
                                        "1. Para puntuar la calidad de la comida: ");
                    calidadComida = Utilidad.solicitarEntero();

                    System.out.println("2. Para puntuar la calidad del mesero");
                    calidadMesero = Utilidad.solicitarEntero();

                    System.out.println("3. Para puntuar el tiempo de espera: ");
                    tiempoEspera = Utilidad.solicitarEntero();

                    System.out.print("Por ultimo, ¿desea dejar un comentario?" + "\n" +
                                    "1. Sí." + "\n" +
                                    "2. No." + "\n" +
                                    "Ingrese un número para elegir una opción: ");
                    int eleccion3 = valorEntrada1.nextInt();
                    switch (eleccion3) {
                        case 1:
                            System.out.print("Deje su comentario:");
                            comentario = valorEntrada1.nextLine();
                            encendido1 = false;
                            break;
                        case 2:
                            encendido1 = false;
                            break;
                        }
                    break;

                case 2:
                    menuPrincipal(restaurante);
                    encendido1 = false;
                    break;
            }
        }
        while (encendido1);

        Cliente cliente = restaurante.indicarCliente(idCliente, restaurante);

        Calificacion calificacion = cliente.calificar((cliente.getReserva().getMesa().getPedido()), calidadComida, calidadMesero, tiempoEspera, comentario);

        cliente.getReserva().getMesa().getPedido().tiempoEsperaRestaurante(calificacion);

        cliente.getReserva().getMesa().getPedido().getFactura().aplicarDescuento(calificacion);

        System.out.println(cliente.getReserva().getMesa().getPedido().getFactura());

        Mesero.organizarMeserosPorCalificacion();


    }
}

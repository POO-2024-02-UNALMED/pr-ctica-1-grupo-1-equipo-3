 package uiMain;
import gestorAplicacion.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        	System.out.println();
            System.out.print("¿Qué desea hacer?" + "\n" +
                                "1. Realizar una reserva." + "\n" +
                                "2. Realizar un domicilio." + "\n" +
                                "3. Realizar el pedido de mi reserva." + "\n" +
                                "4. Gestionar recompensas" + "\n" +
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
                    domicilio(restaurante);
                    encendido = false;
                    break;
                case 3:
                    Restaurante.crearPedido(restaurante);
                    encendido = false;
                    break;
                case 4:
                gestionarRecompensas(restaurante); 
                    encendido = false;
                    break;
                case 5:
                    calificar(restaurante);
                    encendido = false;
                    break;
                case 6:
                    //cerrar el programa
                    System.exit(0);
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
    	
    	int identificacion = 0;
    	while (true) {
    	System.out.println("Ingrese su número de identificacion(Sin espacios, puntos o comas, sólo el número): ");
    	String input = scannerFuncionalidad1.nextLine();
    	//verifica que la entrada sea solo digitos
    	if (input.matches("\\d+")) {
    		identificacion = Integer.parseInt(input);
    		break; }
    	else {
    		System.out.println("La identificacion ingresa no es valida, intentelo de nuevo");
    	    try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    	}	
    }
    	
    	System.out.println("Número de personas: ");
    	int numeroPersonas = scannerFuncionalidad1.nextInt();
    	scannerFuncionalidad1.nextLine();
    	
    	System.out.println("Mesa deluxe o basic (escriba el tipo de mesa en minúsculas, por favor): ");
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
						
						System.out.println("Desea agregar 1 hora a su reserva?(Incluida en el pago de la mesa deluxe(S/N)");
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
					
					if(respuesta2.equals("S")) {
						// Muestra los ingredientes únicos.
			            System.out.println("Estos son los ingredientes disponibles en nuestros platos:");
			            String[] todosIngredientes = Menu.obtenerTodosLosIngredientes();
			            for (String ingrediente : todosIngredientes) {
			                if (!Menu.ingredienteEstaDuplicado(ingrediente, todosIngredientes)) {
			                    System.out.println(ingrediente);
			                }
			            }

			            // Solicitar restricciones del usuario.
			            System.out.println("Por favor, ingrese los ingredientes a los que es alérgico, separados por comas, escriba tal cual como aparecen en la lista:");
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
			        	// Recomendación de platos más caros :).
			            System.out.println("\nNo ha indicado alergias. Como recomendación le dejamos los platos más sabrosos de nuestro menú:");
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
					restaurante.addIdConReserva(identificacion); //agregar id a la lista de reservas
				}
			}
		} while (!reservaExitosa);
    }
   
    //FUNCIONALIDAD 4
public static void gestionarRecompensas(Restaurante restaurante) {
    Scanner scanner = new Scanner(System.in);
    boolean continuar = true;
    int idCliente = -1;
    
    // Solicitar la identificación del cliente
    System.out.print("Ingrese su número de identificación para gestionar sus recompensas: ");
    idCliente = scanner.nextInt();

    // Validar si el cliente existe
    Cliente cliente = restaurante.indicarCliente(idCliente, restaurante);
    if (cliente == null) {
        System.out.println("Cliente no encontrado. Asegúrese de ingresar una identificación válida.");
        return;
    }

    // Mostrar puntos disponibles del cliente
    int puntosTotales = cliente.getPuntosGenerales();
    System.out.println("Usted tiene " + puntosTotales + " puntos disponibles.");

    // Menú de recompensas
    do {
        System.out.println("\n¿Qué desea hacer con sus puntos?");
        System.out.println("1. Redimir puntos para una reserva.");
        System.out.println("2. Redimir puntos para productos.");
        System.out.println("3. Redimir puntos para servicios exclusivos.");
        System.out.println("4. Salir.");
        System.out.print("Ingrese una opción: ");
        
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                // Redimir puntos para reserva
                System.out.print("¿Cuántos puntos desea redimir para una reserva? ");
                int puntosReserva = scanner.nextInt();
                if (restaurante.redimirPuntosParaReserva(cliente, puntosReserva)) {
                    System.out.println("¡Reserva realizada con éxito utilizando " + puntosReserva + " puntos!");
                } else {
                    System.out.println("No tiene suficientes puntos para redimir una reserva.");
                }
                break;

            case 2:
                // Redimir puntos para productos
                System.out.print("¿Cuántos puntos desea redimir para productos? ");
                int puntosProducto = scanner.nextInt();
                if (restaurante.redimirPuntosParaProducto(cliente, puntosProducto)) {
                    System.out.println("¡Productos adquiridos con éxito utilizando " + puntosProducto + " puntos!");
                } else {
                    System.out.println("No tiene suficientes puntos para redimir productos.");
                }
                break;

            case 3:
                // Redimir puntos para servicios exclusivos
                System.out.print("¿Cuántos puntos desea redimir para servicios exclusivos? ");
                int puntosServicio = scanner.nextInt();
                if (restaurante.redimirPuntosParaServicio(cliente, puntosServicio)) {
                    System.out.println("¡Servicio exclusivo obtenido con éxito utilizando " + puntosServicio + " puntos!");
                } else {
                    System.out.println("No tiene suficientes puntos para redimir servicios exclusivos.");
                }
                break;

            case 4:
                // Salir
                continuar = false;
                break;

            default:
                System.out.println("Opción no válida. Por favor, elija una opción correcta.");
                break;
        }

    } while (continuar);
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
    
    public static void domicilio(Restaurante restaurante) {
    	Scanner scanner = new Scanner(System.in);
        Almacen almacen = new Almacen();

        // Solicitar datos del cliente
        System.out.println("Bienvenido al sistema de pedidos a domicilio.");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese su dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Ingrese su identificación: ");
        long identificacion = scanner.nextLong();
        scanner.nextLine(); // Consumir salto de línea

        // Crear el cliente
        Cliente cliente = new Cliente(nombre, identificacion, restaurante);

        // Solicitar el pedido del cliente
        Map<String, Integer> pedidoDomicilio = new HashMap<>();
        boolean continuar = true;

        while (continuar) {
            System.out.println("Menú disponible:");
            int index = 1; // Índice para numerar los alimentos
            Menu[] menuItems = Menu.values(); // Obtener los elementos del menú

            // Mostrar los alimentos con números
            for (int i = 0; i < menuItems.length; i++) {
                System.out.printf("%d. %s - Precio: %.2f%n", i + 1, menuItems[i].name(), menuItems[i].getPrecio());
            }

            System.out.print("Seleccione el número del alimento que desea pedir: ");
            int seleccion = scanner.nextInt();

            if (seleccion < 1 || seleccion > menuItems.length) {
                System.out.println("Selección inválida. Inténtelo nuevamente.");
                continue;
            }

            Menu menuSeleccionado = menuItems[seleccion - 1];

            // Verificar disponibilidad en el almacén
            if (almacen.verificarDisponibilidad(menuSeleccionado)) {
                System.out.print("Ingrese la cantidad que desea pedir: ");
                int cantidad = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea

                // Agregar al pedido y actualizar el inventario
                pedidoDomicilio.put(menuSeleccionado.nombre, cantidad);
                almacen.actualizarInventario(menuSeleccionado, cantidad);
                System.out.println("El alimento fue agregado a su pedido.");
            } else {
                System.out.println("Lo sentimos, no hay suficientes ingredientes disponibles para este alimento.");
            }

            System.out.print("¿Desea agregar otro alimento a su pedido? (si/no): ");
            String respuesta = scanner.nextLine();
            continuar = respuesta.equalsIgnoreCase("si");
        }

        // Calcular el costo total
        int costoTotal = 0;
        for (Map.Entry<String, Integer> entry : pedidoDomicilio.entrySet()) {
            String alimento = entry.getKey();
            int cantidad = entry.getValue();
            for (Menu menu : Menu.values()) {
                if (menu.nombre.equals(alimento)) {
                    costoTotal += menu.getPrecio() * cantidad;
                }
            }
        }

        // Crear el pedido a domicilio
        Domicilio domicilio = new Domicilio(cliente, pedidoDomicilio, direccion, false, costoTotal);

        // Mostrar el resumen del pedido
        System.out.println("\nResumen del pedido:");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Dirección: " + domicilio.getDireccion());
        System.out.println("Pedido:");
        for (Map.Entry<String, Integer> entry : pedidoDomicilio.entrySet()) {
            System.out.printf("- %s: %d unidad(es)%n", entry.getKey(), entry.getValue());
        }
        System.out.println("Costo total: " + domicilio.getCosto());
    }
}

 package uiMain;
import baseDatos.Deserializador;
import baseDatos.Serializador;
import gestorAplicacion.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main implements Utilidad {

    public static void main(String[] args) {
        Deserializador.deserializarListas();

        menuPrincipal(Restaurante.getRestaurante().get(0));
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
                    Serializador.serializarListas();
                    System.exit(0);
            }
        } while(encendido);
    }
 
        
    //FUNCIONALIDAD 1
	public static void reservar(Restaurante restaurante) {

		// Recopilación de información
		Scanner scannerFuncionalidad1 = new Scanner(System.in);
		System.out.println("Bienvenido/a al restaurante " + restaurante.getNombre()+ ", por favor digite la siguiente información para realizar su reserva");
		System.out.println("Ingrese su nombre: ");
		String nombre = scannerFuncionalidad1.nextLine();

		int identificacion = 0;
		while (true) {
			System.out.println("Ingrese su número de identificacion(Sin espacios, puntos o comas, sólo el número): ");
			String input = scannerFuncionalidad1.nextLine();
			
			// verifica que la entrada sea solo digitos
			if (input.matches("\\d+")) {
				identificacion = Integer.parseInt(input);
				break;
			} else {
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

			//Validación fecha y hora con formato correcto.
			do {
				System.out.println("Fecha de la reserva (Por favor, ingrese la fecha en formato dia/mes/año, incluyendo el '/'): ");
				System.out.println("Si su fecha supera el mes a partir de hoy, tendrá un costo adicional de $50.000");
				fecha = scannerFuncionalidad1.nextLine();

				System.out.println("Hora de la reserva (Por favor, ingrese la hora en formato militar así; hora:minutos, incluyendo el ':'): ");
				System.out.println("Solo tenemos servicio de 7:00 a 23:00");
				hora = scannerFuncionalidad1.nextLine();

				fechaHoraValida = restaurante.validarFechaHora(fecha, hora);
				if (!fechaHoraValida) {
					System.out.println("Fecha u hora mal ingresadas, inténtelo de nuevo (tenga en cuenta las recomendaciones).");
				}
			} while (!fechaHoraValida);

			//Conversión de la fecha y hora a un LocalDateTime
			LocalDateTime fechaReserva = restaurante.convertirFechaHora(fecha, hora);

			//Obtención de las mesas disponibles
			ArrayList<Mesa> mesasDisponibles = restaurante.hacerReserva(fechaReserva, numeroPersonas, tipoMesa);
			
			//Impresión de las mesas disponibles.
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

				// Validacion que la mesa esté entre las opciones
				while (!numeroMesas.contains(numeroMesaEscogida)) {
					System.out.println("Numero de mesa incorrecto, escoja una de las mesas disponibles");
					numeroMesaEscogida = scannerFuncionalidad1.nextInt();
				}

				Mesa mesaEscogida = null;

				//Asignacion de la mesa escogida.
				for (Mesa i : mesasDisponibles) {
					if (i.getNumero() == numeroMesaEscogida) {
						mesaEscogida = i;
					}
				}

				LocalDateTime fechaActual = LocalDateTime.now();
				//Creación Reserva
				Reserva reserva = new Reserva(mesaEscogida, fechaReserva, numeroPersonas, fechaActual);
				boolean meseroAsignado = mesaEscogida.reservar(reserva);

				//Asignación de mesero a la mesa y creación del Cliente 
				if (meseroAsignado == false) {
					System.out.println("Lo sentimos, no hay meseros que puedan atender su reserva a la hora indicada");
					reservaExitosa = false;
				} else {

					Cliente cliente = restaurante.obtenerOcrearCliente(nombre, identificacion);

					// Asignación de clases
					reserva.setCliente(cliente);
					cliente.setReserva(reserva);

					//Datos adicionales para mesa "deluxe"
					System.out.println(reserva.getMesa().getTipo());
					if (reserva.getMesa().getTipo().equals("deluxe")) {
						System.out.println("Ingrese el tipo de decoracion que desea: elegante, rústico o moderno");
						String estilo = scannerFuncionalidad1.nextLine();
						reserva.getMesa().setDecoracion(estilo);

						System.out.println("Desea agregar 1 hora a su reserva?(Incluida en el pago de la mesa deluxe(S/N)");
						String respuesta = scannerFuncionalidad1.nextLine();

						//Validación adición de tiempo(1 hora)
						if (respuesta == "S") {
							boolean validacion = mesaEscogida.estaDisponible(fechaReserva.plusHours(1));
							if (validacion == true) {
								System.out.println("Hora añadida con éxito");
								int recargoReserva = 30000;
								reserva.sumarPrecio(recargoReserva);
							} else {
								System.out.println("Lo sentimos, la hora adicional interrumpe otra reserva, la decoración será cortesía de la casa");
							}
						}
					}

					// Interaccion si el usuario tiene restricciones alimentarias
					System.out.println("¿Desea agregar restricciones alimentarias a ingredientes específicos?(S/N)");
					String respuesta2 = scannerFuncionalidad1.nextLine();

					if (respuesta2.equals("S")) {
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

						// Recomendaciones platos restringidos
						System.out.println("\nRecomendaciones de platos que no contienen los ingredientes restringidos:");
						for (Menu plato : Menu.values()) {
							if (!Menu.platoContieneAlergia(plato, alergias)) {
								System.out.println("- " + plato.getNombre() + " ($" + plato.getPrecio() + ")");
							}
						}
					} else {
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

					//Resumen de la Reserva
					System.out.println("===== Resumen de Reserva =====");
					System.out.println("Nombre del Cliente: " + reserva.getCliente().getNombre());
					System.out.println("Número de Reserva: " + reserva.getId());
					System.out.println("Fecha: " + reserva.getFechaHora());
					System.out.println("Numero de Mesa: " + reserva.getMesa().getNumero());
					System.out.println("Mesero Asignado: $" + reserva.getMesa().getMesero().getNombre());
					System.out.println("Total: $" + reserva.getPrecioTotal());
					System.out.println("==============================");
					System.out.println("¿Desea confirmar su reserva(S)");
					System.out.println("¿Desea volver a ingresar los datos?(N)");
					String confirmacion = scannerFuncionalidad1.nextLine();

					if (confirmacion.equals("S")) {
						System.out.println("Reserva realizada con éxito, gracias por Reservar en "+restaurante.getNombre());
						reserva.getFactura().sumarValor(reserva.getPrecioTotal()); //Suma el valor de la reserva a la factura
						reservaExitosa = true;
						restaurante.addIdConReserva(identificacion); // agregar id a la lista de reservas
					} else {
						reservaExitosa = false;
					}
				}
			}
		} while (!reservaExitosa);
		Serializador.serializarListas();
        System.exit(0);
	}
   
    //FUNCIONALIDAD 4
public static void gestionarRecompensas(Restaurante restaurante) {
    Scanner scanner = new Scanner(System.in);
        
        // Deserializar la lista de clientes para cargarla al iniciar
        
        
        // Solicitar la identificación del cliente
        System.out.print("Ingrese la identificación del cliente que desea buscar: ");
        long identificacionBuscada = scanner.nextLong();
        
        // Variables para calcular puntos
        Domicilio ultimoDomicilio = null;
        int visitas = 0;
        double totalGastado = 0;
    
        // Recorrer la lista de domicilios para encontrar los datos del cliente
        for (Domicilio d : Domicilio.getDomicilios()) {
            if (d.getCliente().getIdentificacion() == identificacionBuscada) {
                visitas++; // Contamos la cantidad de veces que ha pedido un domicilio
                totalGastado += d.getCosto(); // Sumamos el costo total de sus domicilios
                ultimoDomicilio = d; // Guardamos el último domicilio encontrado
            }
        }
    
        // Si se encuentra un domicilio, preguntar confirmación antes de mostrar el costo
        if (ultimoDomicilio != null) {
            System.out.println("\nÚltimo domicilio del cliente con ID " + identificacionBuscada + ":");
            System.out.println("Cliente: " + ultimoDomicilio.getCliente().getNombre() +
                               " | Dirección: " + ultimoDomicilio.getDireccion() +
                               " | Costo total: $" + ultimoDomicilio.getCosto());
    
            // Preguntar si confirma el domicilio
            System.out.print("\n¿Desea confirmar su domicilio? (si/no): ");
            String confirmacion = scanner.next();
    
            if (confirmacion.equalsIgnoreCase("si")) {
                System.out.println("\nEl costo total de su pedido es: $" + ultimoDomicilio.getCosto());
    
                // Preguntar método de pago
                System.out.println("Seleccione su método de pago:");
                System.out.println("1. Tarjeta");
                System.out.println("2. Efectivo");
                System.out.print("Ingrese la opción (1 o 2): ");
                int opcionPago = scanner.nextInt();
    
                if (opcionPago == 1) {
                    System.out.println("\nHa seleccionado pagar con tarjeta. ¡Gracias por su compra!");
                } else if (opcionPago == 2) {
                    System.out.println("\nHa seleccionado pagar en efectivo. ¡Gracias por su compra!");
                } else {
                    System.out.println("\nOpción no válida. Pago no procesado.");
                }
    
                // Calcular los puntos por costo (1 punto por cada 10 unidades gastadas)
                int puntosPorCosto = (int) (totalGastado / 10);
    
                // Calcular los puntos por visitas (1 punto por cada visita)
                int puntosPorVisitas = visitas;
    
                // Calcular el total de puntos acumulados
                int puntosTotales = puntosPorCosto + puntosPorVisitas;
    
                // Mostrar desglose de puntos
                System.out.println("\nResumen de puntos acumulados:");
                System.out.println("- Puntos por visitas: " + puntosPorVisitas + " puntos");
                System.out.println("- Puntos por costo total: " + puntosPorCosto + " puntos");
                System.out.println("=> Sus puntos acumulados hasta el momento son: " + puntosTotales + " puntos");
    
            } else {
                System.out.println("\nPedido no confirmado. Regresando al menú principal...");
            }
        } else {
            System.out.println("\nNo se encontraron domicilios para la identificación ingresada.");
        }
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
                    valorEntrada1.nextLine();
                    switch (eleccion3) {
                        case 1:
                            System.out.print("Deje su comentario: ");
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

        Cliente cliente = restaurante.indicarCliente(idCliente);

        //Estadisticas antes de la calificación
        double calificionActualMesero = cliente.getReserva().getMesero().getPromCalificaciones();
        double reputacionActualRestaurante = restaurante.getReputacion();


        //Calificacion y actualización de estadisticas
        Calificacion calificacion = cliente.calificarPorReserva((cliente.getReserva().getMesa().getPedido()), calidadComida, calidadMesero, tiempoEspera, comentario);

        cliente.getReserva().getMesa().getPedido().tiempoEsperaRestaurante(calificacion);

        cliente.getReserva().getMesa().getPedido().getFactura().aplicarDescuento(calificacion);

        restaurante.actualizarReputacion(calificacion);

        System.out.println(cliente.getReserva().getMesa().getPedido().getFactura());

        cliente.getReserva().getMesa().getPedido().getFactura().prioridadMeseros();

        if (cliente.getReserva().getMesa().tipoMesa()) {  //Valida si la mesa es deluxe
            System.out.printf("¡Hola %s! Bienvenido al apartado exclusivo para clientes premium.%n", cliente.getNombre());
            System.out.print("Por los beneficios de haber estado en una de nuestras mesas deluxe," + "\n" +
                                "se le proporciona la posibilidad de visualizar el impacto de su califiacación." + "\n" +
                                "¿Desea verlo?" + "\n" +
                                "1. Sí." + "\n" +
                                "2. No." + "\n" +
                                "Ingrese un número para elegir una opción: ");
                                int eleccion4 = valorEntrada1.nextInt();
                                switch (eleccion4) {
                                    case 1:
                                        System.out.println("                  ANTES DE SU CALIFICACIÓN     DESPUES DE SU CALIFICACIÓN");
                                        System.out.printf("Restaurante:              %.1f                          %.1f%n",reputacionActualRestaurante,restaurante.getReputacion());
                                        System.out.printf("Mesero:                   %.1f                          %.1f%n",calificionActualMesero,cliente.getReserva().getMesero().getPromCalificaciones());

                                    case 2:
                                        break;
                                }
        } else {
            System.out.println("Consulta con nuestro equipo cómo acceder a una mesa deluxe en tu próxima visita y obtener tu apartado exclusivo.");
        }


    }
    
    //FUNCIONALIDAD 2
    public static void domicilio(Restaurante restaurante) {
        Scanner scanner = new Scanner(System.in);
        Almacen almacen = new Almacen();

        System.out.println("Bienvenido al sistema de pedidos a domicilio.");

        // Validar el nombre
        String nombre;
        while (true) {
            System.out.print("Ingrese su nombre: ");
            nombre = scanner.nextLine();
            if (nombre.trim().isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Intente nuevamente.");
            } else {
                break;
            }
        }

        // Validar la dirección
        String direccion;
        while (true) {
            System.out.print("Ingrese su dirección (formato: Calle/Carrera/cll/cr seguido de un número, # y otro número): ");
            direccion = scanner.nextLine();
            if (direccion.matches("(?i)^(calle|carrera|cll|cr|CLL|CR)\\s\\d+\\s#\\s\\d+$")) {
                break;
            } else {
                System.out.println("Formato de dirección inválido. Ejemplo válido: Calle 45 # 32.");
            }
        }

        // Validar la identificación
        long identificacion;
        while (true) {
            System.out.print("Ingrese su identificación: ");
            try {
                identificacion = scanner.nextLong();
                scanner.nextLine(); // Consumir el salto de línea
                if (identificacion > 0) {
                    break;
                } else {
                    System.out.println("La identificación debe ser un número positivo. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. La identificación debe ser un número. Intente nuevamente.");
                scanner.nextLine(); // Consumir la entrada inválida
            }
        }

        // Solicitar si el pedido es prioritario
        boolean esPrioritario;
        while (true) {
            System.out.print("¿Desea que su pedido sea prioritario? (si/no): ");
            String prioridadInput = scanner.nextLine().trim().toLowerCase();
            if (prioridadInput.equals("si")) {
                esPrioritario = true;
                break;
            } else if (prioridadInput.equals("no")) {
                esPrioritario = false;
                break;
            } else {
                System.out.println("Respuesta inválida. Por favor, responda con 'si' o 'no'.");
            }
        }

        // Crear el cliente
        Cliente cliente = new Cliente(nombre, identificacion, restaurante);
        ArrayList<Cliente> clientes = Cliente.getClientes();
        clientes.add(cliente);
        Serializador.serializar(clientes, "clientes");
    

     // Solicitar el pedido del cliente
        ArrayList<PedidoItem> pedidoItems = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.println("Menú disponible:");
            Menu[] menuItems = Menu.values();

            for (int i = 0; i < menuItems.length; i++) {
                System.out.printf("%d. %s - Precio: %s%n", 
                    i + 1, 
                    menuItems[i].name(), 
                    Utilidad.formatoPrecio(menuItems[i].getPrecio()));
            }

            int seleccion;
            while (true) {
                System.out.print("Seleccione el número del alimento que desea pedir: ");
                try {
                    seleccion = scanner.nextInt();
                    if (seleccion >= 1 && seleccion <= menuItems.length) {
                        break;
                    } else {
                        System.out.println("Selección inválida. Elija un número dentro del rango mostrado.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Ingrese un número válido.");
                    scanner.nextLine(); // Consumir la entrada inválida
                }
            }

            Menu menuSeleccionado = menuItems[seleccion - 1];

            if (almacen.verificarDisponibilidad(menuSeleccionado)) {
                int cantidad;
                while (true) {
                    System.out.print("Ingrese la cantidad que desea pedir: ");
                    try {
                        cantidad = scanner.nextInt();
                        scanner.nextLine(); // Consumir salto de línea
                        if (cantidad > 0) {
                            break;
                        } else {
                            System.out.println("La cantidad debe ser mayor a cero. Intente nuevamente.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida. La cantidad debe ser un número entero. Intente nuevamente.");
                        scanner.nextLine(); // Consumir la entrada inválida
                    }
                }

                // Crear y agregar un nuevo PedidoItem
                PedidoItem item = new PedidoItem(menuSeleccionado.getNombre(), cantidad);
                pedidoItems.add(item);

                // Actualizar el inventario del almacén
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

        try {
            // Calcular el costo total del pedido
            for (PedidoItem item : pedidoItems) {
                String alimento = item.getProducto();
                int cantidad = item.getCantidad();

                boolean alimentoEncontrado = false;
                for (Menu menu : Menu.values()) {
                    if (menu.getNombre().equals(alimento)) {
                        costoTotal += menu.getPrecio() * cantidad;
                        alimentoEncontrado = true;
                        break;
                    }
                }

                if (!alimentoEncontrado) {
                    System.out.println("Advertencia: El alimento '" + alimento + "' no se encuentra en el menú.");
                }
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al calcular el costo total del pedido.");
        }

        // Mostrar el costo total del pedido
        System.out.println("El costo total del pedido es: " + Utilidad.formatoPrecio(costoTotal));


            // Incrementar el costo total en un 5% si el pedido es prioritario
            if (esPrioritario) {
                int recargo = (int) (costoTotal * 0.05); // Calcular el 5% del costo total
                costoTotal += recargo;
                System.out.println("Se ha aplicado un recargo del 5% por ser un pedido prioritario: " + Utilidad.formatoPrecio(recargo));
            }

            // Validar que el costo total no sea cero
            if (costoTotal <= 0) {
                System.out.println("El costo total del pedido es inválido. No se puede procesar el pedido.");
                return;
            }
            
            Domiciliario.inicializarDomiciliarios();
         // Seleccionar el domiciliario adecuado
         List<Domiciliario> listaDomiciliarios = Domiciliario.getListaDomiciliarios();
         if (listaDomiciliarios.isEmpty()) {
             System.out.println("No hay domiciliarios disponibles para asignar.");
             return;
         }

         // Declarar e inicializar la variable domiciliario
         Domiciliario domiciliario;

         if (esPrioritario && listaDomiciliarios.size() > 0) {
             domiciliario = listaDomiciliarios.get(0); // Domiciliario 1
         } else if (!esPrioritario && listaDomiciliarios.size() > 1) {
             domiciliario = listaDomiciliarios.get(1); // Domiciliario 2
         } else {
             System.out.println("No hay suficientes domiciliarios disponibles para asignar.");
             return;
         }
         
         // Crear el pedido a domicilio después de asignar el domiciliario
         Domicilio domicilio = new Domicilio(cliente, pedidoItems, direccion, esPrioritario, costoTotal, domiciliario);
         ArrayList<Domicilio> domicilios = Domicilio.getDomicilios();
        domicilios.add(domicilio);
        Serializador.serializarListas();
            
            

            // Mostrar el resumen del pedido
            System.out.println("\nResumen del pedido:");
            System.out.println("Cliente: " + cliente.getNombre());
            System.out.println("Dirección: " + domicilio.getDireccion());
            System.out.println("Prioridad: " + (domicilio.isDomicilioPrioritario() ? "Prioritario" : "Normal"));
            System.out.println("Pedido:");
            
            for (PedidoItem item : pedidoItems) {
                System.out.printf("- %s: %d unidad(es)%n", item.getProducto(), item.getCantidad());
            }
            System.out.println("Costo total: " + Utilidad.formatoPrecio(domicilio.getCosto()));

            // Validar el pago
            int pago = 0;
            do {
                System.out.print("Ingrese el monto con el que desea pagar: ");
                try {
                    pago = scanner.nextInt();
                    if (pago < costoTotal) {
                        System.out.println("El monto ingresado es insuficiente. Intente nuevamente.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, ingrese un número entero válido.");
                    scanner.nextLine(); // Consumir entrada inválida
                }
            } while (pago < costoTotal);
            
            int cambio = pago - costoTotal;

            // Entregar el cambio
            List<Integer> billetesEntregados = domiciliario.entregarCambio(cambio);

            // Mostrar los billetes entregados
            System.out.println("Cambio entregado:");
            for (int billete : billetesEntregados) {
                System.out.println("- Billete de " + Utilidad.formatoPrecio(billete));
            }

            // Confirmar la entrega
            System.out.println("Gracias por su pedido. El domiciliario " + domiciliario.getNombre() + 
                               " (" + (domicilio.isDomicilioPrioritario() ? "Prioritario" : "No prioritario") + ")" + 
                               " se encargará de la entrega.");
        } 
}

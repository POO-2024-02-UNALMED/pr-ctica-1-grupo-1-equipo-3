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
                                "4. Gestionar recompensas." + "\n" +
                                "5. Calificar el servicio." + "\n" +
                                "6. Salir." + "\n");
            int eleccion = Utilidad.solicitarEntero(1,6);
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
					System.out.println("Mesero Asignado:  " + reserva.getMesa().getMesero().getNombre());
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
						reserva.eliminarReserva();
						mesaEscogida.eliminarReserva();
						reserva = null;
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
    
        System.out.print("Ingrese la identificación del cliente que desea buscar: ");
        long identificacionBuscada = scanner.nextLong();
    
        Domicilio ultimoDomicilio = null;
        int visitas = 0;
        double totalGastado = 0;
        
        for (Domicilio d : Domicilio.getDomicilios()) {
            if (d.getCliente().getIdentificacion() == identificacionBuscada) {
                visitas++;
                totalGastado += d.getCosto();
                ultimoDomicilio = d;
            }
        }
    
        if (ultimoDomicilio != null) {
            System.out.println("\nÚltimo domicilio del cliente con ID " + identificacionBuscada + ":");
            System.out.println("Cliente: " + ultimoDomicilio.getCliente().getNombre() +
                               " | Dirección: " + ultimoDomicilio.getDireccion() +
                               " | Costo total: $" + ultimoDomicilio.getCosto());
    
            int puntosPorCosto = (int) (totalGastado / 100);
            int puntosPorVisitas =  (visitas > 5) ? (visitas - 5) * 20 : 0;
            int puntosTotales = puntosPorCosto + puntosPorVisitas;
    
            System.out.println("\nTotal de puntos acumulados: " + puntosTotales + " puntos");
    
            int opcionUso = -1;
            while (opcionUso != 0) {
                System.out.println("\n¿Para qué desea utilizar los puntos?");
                System.out.println("1. Reserva");
                System.out.println("2. Servicios exclusivos");
                System.out.println("3. Productos del menú");
                System.out.println("4. Devolución del producto");
                System.out.println("5. Mal servicio");
                System.out.println("0. Salir");
                System.out.print("Ingrese la opción (1, 2, 3, 4, 5 o 0 para salir): ");
                opcionUso = scanner.nextInt();
    
                if (opcionUso == 1) {
                    if (puntosTotales > 1000) {
                        System.out.println("\nHa seleccionado utilizarlo para una reserva.");
                        System.out.print("Ingrese la cantidad de puntos que desea usar para la reserva: ");
                        int puntosReserva = scanner.nextInt();
                        if (puntosPorVisitas >= puntosReserva) {
                            puntosTotales -= puntosReserva;
                            System.out.println("\n ¡Reserva realizada con éxito! ");
                        } else {
                            System.out.println("\n No tiene suficientes puntos para la reserva.");
                        }
                    } else {
                        System.out.println("\n No tiene suficientes puntos para realizar una reserva. Se requieren más de 10000 puntos.");
                    }
                } else if (opcionUso == 2) {
                    System.out.println("\nHa seleccionado utilizarlo para servicios exclusivos.");
                    System.out.println("\nMesas deluxe disponibles:");
                    Mesa mesaMayorCapacidad = null;
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (mesa.getTipo().equalsIgnoreCase("deluxe")) {
                            int puntosRequeridos = Math.max(2000, mesa.getCapacidad() * 500);
                            System.out.println("Mesa " + mesa.getNumero() + " | Capacidad: " + mesa.getCapacidad() + " | Puntos requeridos: " + puntosRequeridos);
                            if (mesaMayorCapacidad == null || mesa.getCapacidad() > mesaMayorCapacidad.getCapacidad()) {
                                mesaMayorCapacidad = mesa;
                            }
                        }
                    }
                    System.out.println("\n¿Desea elegir una de estas mesas especiales para su próxima reserva?");
                    System.out.println("1. Sí, elegir una mesa deluxe");
                    System.out.println("2. No, prefiero usar los puntos para otra cosa");
                    System.out.print("Seleccione una opción: ");
                    int opcionMesa = scanner.nextInt();
    
                    if (opcionMesa == 1) {
                        System.out.print("\nIngrese el número de la mesa que desea reservar: ");
                        int numeroMesa = scanner.nextInt();
    
                        boolean mesaEncontrada = false;
                        for (Mesa mesa : restaurante.getMesas()) {
                            if (mesa.getTipo().equalsIgnoreCase("deluxe") && mesa.getNumero() == numeroMesa) {
                                int puntosRequeridos = Math.max(2000, mesa.getCapacidad() * 500);
                                if (puntosPorCosto >= puntosRequeridos) {
                                    puntosTotales -= puntosRequeridos;
                                    System.out.println("\n ¡Mesa deluxe " + numeroMesa + " reservada con éxito! ");
                                } else {
                                    System.out.println("\n No tiene suficientes puntos para esta mesa.");
                                }
                                mesaEncontrada = true;
                                break;
                            }
                        }
    
                        if (!mesaEncontrada) {
                            System.out.println("\n Número de mesa no válido o no disponible.");
                        }
                    } else {
                        System.out.println("\nPuede seguir usando sus puntos en otras opciones.");
                    }
                } else if (opcionUso == 3) {
                    System.out.println("\nHa seleccionado utilizarlo para productos del menú.");
                    System.out.println("\nMenú disponible (puntos requeridos):");
                    
                    for (Menu plato : Menu.values()) {
                        int puntosRequeridos = (int) (plato.getPrecio() / 1000);
                        puntosTotales -= puntosRequeridos;
                        System.out.println("- " + plato.getNombre() + " | Puntos: " + puntosRequeridos);
                    }
    
                    System.out.print("Ingrese el nombre del producto que desea canjear: ");
                    scanner.nextLine();
                    String productoElegido = scanner.nextLine();
    
                    Menu productoSeleccionado = null;
                    for (Menu plato : Menu.values()) {
                        if (plato.getNombre().equalsIgnoreCase(productoElegido)) {
                            productoSeleccionado = plato;
                            break;
                        }
                    }
    
                    if (productoSeleccionado != null) {
                        int puntosRequeridos = (int) (productoSeleccionado.getPrecio() / 1000);
                        if (puntosTotales >= puntosRequeridos) {
                            puntosTotales -= puntosRequeridos;
                            System.out.println("\n ¡Ha canjeado " + productoSeleccionado.getNombre() + " con éxito! ");
                        } else {
                            System.out.println("\n No tiene suficientes puntos para canjear este producto.");
                        }
                    } else {
                        System.out.println("\n El producto ingresado no es válido.");
                    }
                }else if (opcionUso == 4) {
                    System.out.println("\nHa seleccionado la opción de devolución del producto.");
                    System.out.print("Ingrese la cantidad de puntos que desea recuperar por devolución: ");
                    int puntosDevolucion = scanner.nextInt();

                   if (puntosDevolucion > 0) {
                        puntosTotales += puntosDevolucion; // Se suman los puntos devueltos
                       System.out.println("\nSe han devuelto " + puntosDevolucion + " puntos a su saldo.");
                    } else {
                         System.out.println("\nNo puede devolver 0 o una cantidad negativa de puntos.");
                }
                }else if (opcionUso==5){
                    System.out.println("\nHa seleccionado la opción de compensación por mal servicio.");
    System.out.print("Ingrese la cantidad de puntos que desea recibir como compensación: ");
    int puntosCompensacion = scanner.nextInt();

    if (puntosCompensacion > 0) {
        puntosTotales += puntosCompensacion; // Se suman los puntos por compensación
        System.out.println("\nSe han agregado " + puntosCompensacion + " puntos a su saldo como compensación.");
    } else {
        System.out.println("\nNo puede solicitar 0 o una cantidad negativa de puntos.");
    }
      
                } else if (opcionUso == 0) {
                    System.out.println("\nVolviendo al menú principal...");
                    break;
                } else {
                    System.out.println("\n Opción no válida. Por favor, elija una opción correcta.");
                }
    
                System.out.println("\n Resumen de puntos acumulados ");
                System.out.println(" Puntos restantes: " + puntosTotales + " puntos");
            }
            
            
        } else {
            System.out.println("\n No se encontraron compras para la identificación ingresada.");
            
        }
        
    
        menuPrincipal(restaurante);
    }
    
    
    
    
    
    
    
    
    
    
    
    

   


    //FUNCIONALIDAD 5
    public static void calificar(Restaurante restaurante) {
        Mesero.organizarMeserosPorCalificacion(); //Ordena los meseros iniciales
        Scanner valorEntrada1 = new Scanner(System.in);
        boolean encendido1 = true;
        long idCliente = 0;
        int calidadComida = 0;
        int calidadMesero = 0;
        int tiempoEspera = 0;
        String comentario = "";
        do {
            System.out.print("¿Desea realizar una calificación?" + "\n" +
                                "1. Sí." + "\n" +
                                "2. No." + "\n");
            int eleccion1 = Utilidad.solicitarEntero(1,2);
            switch (eleccion1) {
                case 1:
                    boolean encendido2 = true;
                    do {
                        idCliente = Utilidad.solicitarId();
                        boolean validarCliente = restaurante.validarCliente(idCliente, restaurante);
                        if (validarCliente){   //validar que la id sea de un cliente
                            encendido2 = false;
                        }else{
                            System.out.print("La identificación no está asociada a ningún cliente" + "\n" +
                                    "1. Deseo ingresar una identificación válida." + "\n" +
                                    "2. Salir." + "\n");

                            int eleccion2 = Utilidad.solicitarEntero(1,2);
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
                    if (restaurante.indicarCliente(idCliente).tipoCliente()) { //Valida si es consumo local
                        System.out.println("Usted ha entrado en el sistema de calificación para clientes con consumo en el local." + "\n" +
                                "Para realizar la calificación porfavor conteste la siguiente encuesta:" + "\n" +
                                "1. Para puntuar la calidad de la comida: ");
                        calidadComida = Utilidad.solicitarEntero(1,5);

                        System.out.println("2. Para puntuar la calidad del mesero");
                        calidadMesero = Utilidad.solicitarEntero(1,5);

                        System.out.println("3. Para puntuar el tiempo de espera: ");
                        tiempoEspera = Utilidad.solicitarEntero(1,5);

                        System.out.print("Por ultimo, ¿desea dejar un comentario?" + "\n" +
                                "1. Sí." + "\n" +
                                "2. No." + "\n");
                        int eleccion3 = Utilidad.solicitarEntero(1,2);
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
                    }else {System.out.println("Usted ha entrado en el sistema de calificación para clientes con consumo a domicilio." + "\n" +
                            "Para realizar la calificación porfavor conteste la siguiente encuesta:" + "\n" +
                            "1. Para puntuar la calidad de la comida: ");
                        calidadComida = Utilidad.solicitarEntero(1,5);

                        System.out.println("2. Para puntuar el tiempo de espera: ");
                        tiempoEspera = Utilidad.solicitarEntero(1,5);

                        System.out.print("Por ultimo, ¿desea dejar un comentario?" + "\n" +
                                "1. Sí." + "\n" +
                                "2. No." + "\n");
                        int eleccion3 = Utilidad.solicitarEntero(1,2);
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
                    }

                case 2:
                    menuPrincipal(restaurante);
                    encendido1 = false;
                    break;
            }
        }
        while (encendido1);

        Cliente cliente = restaurante.indicarCliente(idCliente);  //para consumo local
        Cliente cliente1 = Domicilio.indicarCliente(idCliente); //Para domicilio


        double reputacionActualRestaurante = restaurante.getReputacion(); //Dato para cualquier tipo de calificacion

        if(restaurante.indicarCliente(idCliente).tipoCliente()){ //valida si es consumo local
            //Datos antes de la califiacion por consumo local
            double calificionActualMesero = cliente.getReserva().getMesero().getPromCalificaciones();
            int posicionActualPrioridadMesero = Mesero.posicionPrioridadMesero(cliente.getReserva().getMesero());


            Calificacion calificacion = cliente.calificarPorReserva((cliente.getReserva().getMesa().getPedido()), calidadComida, calidadMesero, tiempoEspera, comentario);  //Se hace la calificacion

            cliente.getReserva().getMesa().getPedido().tiempoEsperaRestaurante(calificacion); //Se encarga de actualizar la reputacion del restaurante por el tiempo de espera

            cliente.getReserva().getMesa().getPedido().getFactura().aplicarDescuento(calificacion);  //Se aplica el descuento si el promdecalificaion es menor que 3

            restaurante.actualizarReputacion(calificacion);  //Se actualiza la reputacion del restaurante

            System.out.println(cliente.getReserva().getMesa().getPedido().getFactura());   //Imprime factura con los detalles de calificiación

            Mesero.revisionMeseros(); //se encarga de revisar las 3 ultimas calificaciones de los meseros, y si uno tiene las 3 ultimas calificaciones por debajo o igual de 2, sera despedido

            int posicionNuevaPrioridadMesero = Mesero.posicionPrioridadMesero(cliente.getReserva().getMesero());



            Mesero.organizarMeserosPorCalificacion();
            if (cliente.getReserva().getMesa().tipoMesa()) {  //Valida si la mesa es deluxe
                System.out.printf("¡Hola %s! Bienvenido al apartado exclusivo para clientes premium.%n", cliente.getNombre());
                System.out.print("Por los beneficios de haber estado en una de nuestras mesas deluxe," + "\n" +
                        "se le proporciona la posibilidad de visualizar el impacto de su califiacación." + "\n" +
                        "¿Desea verlo?" + "\n" +
                        "1. Sí." + "\n" +
                        "2. No." + "\n");
                int eleccion4 = Utilidad.solicitarEntero(1,2);
                switch (eleccion4) {
                    case 1:
                        System.out.println("                  ANTES DE SU CALIFICACIÓN     DESPUES DE SU CALIFICACIÓN");
                        System.out.printf("Restaurante:              %.1f                          %.1f%n",reputacionActualRestaurante,restaurante.getReputacion());
                        System.out.printf("Mesero:                   %.1f                          %.1f%n",calificionActualMesero,cliente.getReserva().getMesero().getPromCalificaciones());
                        System.out.printf("Prioridad del mesero:     %d                            %d%n", posicionActualPrioridadMesero,posicionNuevaPrioridadMesero);
                        if (Mesero.posicionPrioridadMesero(cliente.getReserva().getMesero()) == -1){
                            System.out.printf("El mesero %s, ha sido despedido", cliente.getReserva().getMesero().getNombre());
                        }else{
                            System.out.printf("El mesero %s, sigue activo", cliente.getReserva().getMesero().getNombre());
                        }
                        break;
                    case 2:
                        break;
                }
            } else {
                System.out.println("Consulta con nuestro equipo cómo acceder a una mesa deluxe en tu próxima visita y obtener un apartado exclusivo.");
            }
        }
        if (!restaurante.indicarCliente(idCliente).tipoCliente()){  //Valida si es domicilio

            //Dato antes de la califiación
            double calificacionActualDomiciliario = Domicilio.indicarDomicilio(idCliente).getDomiciliario().getPromCalificaciones();


            Calificacion calificacion = cliente1.calificarPorDomicilio((Domicilio.indicarDomicilio(idCliente)),calidadComida, tiempoEspera, comentario);  //Se hace calificacion

            if (tiempoEspera < 3){  //Se encarga de actualizar la reputacion del restaurante por el tiempo de espera
                restaurante.setReputacion(restaurante.getReputacion()-0.1);
            }

            restaurante.actualizarReputacion(calificacion); //Se actualiza la reputacion del restaurante

            System.out.println("\n=====================================" + "\n" +
                            "         FACTURA DE CONSUMO AURA GOURMET         " + "\n" +
                   "Restaurante: " + restaurante.getNombre() + "\n" +
                   "Cliente: " + cliente1.getNombre() + "\n" +
                   "Domiciliario encargado: " + Domicilio.indicarDomicilio(idCliente).getDomiciliario().getNombre() + "\n" +
                   "-------------------------------------" + "\n" +
                    Utilidad.aplicarDescuento(calificacion, (Domicilio.indicarDomicilio(idCliente).getCosto())) + "\n" +
                   "Calificación del servicio: " + calificacion.getPromedioCalificacion() + "\n" +
                   "\n=====================================" + "\n" +
                   "Gracias por visitarnos. ¡Esperamos verlo pronto!"  + "\n" +
                   "=====================================");
            Mesero.organizarMeserosPorCalificacion();
            if((Domicilio.indicarDomicilio(idCliente).isDomicilioPrioritario())){ //Valida si el domicilio es prioriatario
                System.out.printf("¡Hola %s! Bienvenido al apartado exclusivo para clientes premium.%n", cliente.getNombre());
                System.out.print("Por el beneficio de que su pedido es prioritario," + "\n" +
                        "se le proporciona la posibilidad de visualizar el impacto de su califiacación." + "\n" +
                        "¿Desea verlo?" + "\n" +
                        "1. Sí." + "\n" +
                        "2. No." + "\n");
                int eleccion4 = Utilidad.solicitarEntero(1,2);
                switch (eleccion4) {
                    case 1:
                        System.out.println("                  ANTES DE SU CALIFICACIÓN     DESPUES DE SU CALIFICACIÓN");
                        System.out.printf("Restaurante:              %.1f                          %.1f%n",reputacionActualRestaurante,restaurante.getReputacion());
                        System.out.printf("Domiciliario:             %.1f                          %.1f%n",calificacionActualDomiciliario, Domicilio.indicarDomicilio(idCliente).getDomiciliario().getPromCalificaciones());
                        break;
                    case 2:
                        break;
                }

            }else{
                System.out.println("Consulta con nuestro equipo para acceder a un apartado exclusivo si tu proximo pedido es prioritario.");
            }
        }
        Serializador.serializarListas();

        menuPrincipal(restaurante);

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
            
            
            //Volver al menu principal
            menuPrincipal(restaurante);
        } 
}

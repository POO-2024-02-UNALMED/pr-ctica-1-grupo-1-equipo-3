 package uiMain;
import java.util.Scanner;
import gestorAplicacion.*;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //Creacion del restaurante
        Restaurante restaurante = new Restaurante("Aura Gourmet",LocalTime.of(7, 0));

        //Creación de las mesas
        Mesa mesa1 = new Mesa(1, 2, "Basic", restaurante);
        Mesa mesa2 = new Mesa(2, 4, "Deluxe", restaurante);
        Mesa mesa3 = new Mesa(3, 6, "Basic", restaurante);
        Mesa mesa4 = new Mesa(4, 4, "Basic", restaurante);
        Mesa mesa5 = new Mesa(5, 6, "Deluxe", restaurante);
        Mesa mesa6 = new Mesa(6, 2, "Deluxe", restaurante);
        Mesa mesa7 = new Mesa(7, 4, "Basic", restaurante);
        Mesa mesa8 = new Mesa(8, 6, "Basic", restaurante);
        Mesa mesa9 = new Mesa(9, 4, "Deluxe", restaurante);
        Mesa mesa10 = new Mesa(10, 2, "Deluxe", restaurante);
        
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
                    //encadenar la funcionalidad 1
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
                	String tipoMesa = scannerFuncionalidad1.nextLine();
                	
                	String fecha;
                    String hora;
                    boolean fechaHoraValida;

                    do {
                        System.out.println("Fecha de la reserva (Por favor, ingrese la fecha en formato dia/mes/año, incluyendo el '/'): ");
                        fecha = scannerFuncionalidad1.nextLine();

                        System.out.println("Hora de la reserva (Por favor, ingrese la hora en formato militar hora:minutos, incluyendo el ':'): ");
                        hora = scannerFuncionalidad1.nextLine();
                        
                        fechaHoraValida = restaurante.validarFechaHora(fecha, hora);
                        if (!fechaHoraValida) {
                            System.out.println("Fecha u hora mal ingresadas, inténtelo de nuevo (tenga en cuenta las recomendaciones).");
                        }
                    } while (!fechaHoraValida);
                    
                    LocalDateTime fechaReserva = restaurante.convertirFechaHora(fecha,hora);
                    ArrayList<String> mostrarMesas = restaurante.mesasDisponibles(numeroPersonas,tipoMesa,fechaReserva);
                    
                    if (mostrarMesas.isEmpty()) {
                        System.out.println("No hay mesas disponibles para las especificaciones dadas.");
                    } else {
                        System.out.println("Mesas disponibles:");
                        for (String i : mostrarMesas) {
                            System.out.println(i);
                        }
                    }
                	
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

    public static void calificar(Restaurante restaurante) {
        boolean encendido1 = true;
        do {
            System.out.print("¿Desea relizar una calificación?" + "\n" +
                                "1. Sí." + "\n" +
                                "2. No." + "\n" +
                                "Ingrese un número para elegir una opción: ");
            Scanner valorEntrada1 = new Scanner(System.in);
            int eleccion1 = 0;
            eleccion1 = valorEntrada1.nextInt();
            switch (eleccion1) {
                case 1:
                    System.out.print("Para realizar la calificación porfavor conteste la siguiente encuesta:" + "\n" +
                                        "1. Del 1 al 5 puntee la calidad de la comida: ");
                    Scanner eleccionComida = new Scanner(System.in);
                    int calidadComida = eleccionComida.nextInt();

                    System.out.print("2. Del 1 al 5 puntee la calidad del mesero: ");
                    Scanner eleccionMesero = new Scanner(System.in);
                    int calidadMesero = eleccionMesero.nextInt();

                    System.out.print("3. Del 1 al 5 puntee el tiempo de espera: ");
                    Scanner eleccionEspera = new Scanner(System.in);
                    int tiempoEspera = eleccionEspera.nextInt();

                    System.out.print("Por ultimo, ¿desea dejar un comentario?" + "\n" +
                                        "1. Sí." + "\n" +
                                        "2. No" + "\n" +
                                        "Ingrese un número para elegir una opción: ");
                    Scanner valorEntrada2 = new Scanner(System.in);
                    int eleccion2 = valorEntrada2.nextInt();
                    switch (eleccion2) {
                        case 1:
                            System.out.print("Deje su comentario:");
                            Scanner eleccionComentario = new Scanner(System.in);
                            String comentario = eleccionComentario.nextLine();
                            break;
                        case 2:
                            break;
                    }
                    encendido1 = false;
                    break;
                case 2:
                    menuPrincipal(restaurante);
                    encendido1 = false;
                    break;
            }
        }
        while (encendido1);
    }
}
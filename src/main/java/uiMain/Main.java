package uiMain;
import java.util.Scanner;
import gestorAplicacion.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
    	//Creación de las mesas
    	ArrayList<Mesa> mesas = new ArrayList<>();
        mesas.add(new Mesa(1, 2, "Basic"));
        mesas.add(new Mesa(2, 4, "Deluxe"));
        mesas.add(new Mesa(3, 6, "Basic"));
        mesas.add(new Mesa(4, 4, "Basic"));
        mesas.add(new Mesa(5, 6, "Deluxe"));
        mesas.add(new Mesa(6, 2, "Deluxe"));
        mesas.add(new Mesa(7, 4, "Basic"));
        mesas.add(new Mesa(8, 6, "Basic"));
        mesas.add(new Mesa(9, 4, "Deluxe"));
        mesas.add(new Mesa(10, 2, "Deluxe"));
        
        //Creación de los meseros
        ArrayList<Mesero> meseros = new ArrayList<>();
        meseros.add(new Mesero(1, "Carlos Martínez", 4, 20));
        meseros.add(new Mesero(2, "Juan Pérez", 3, 15));
        meseros.add(new Mesero(3, "Andrés Gómez", 5, 25));
        meseros.add(new Mesero(4, "Laura Rodríguez", 4, 18));
        meseros.add(new Mesero(5, "Ana María Sánchez", 2, 10));
        meseros.add(new Mesero(6, "Luis Fernández", 3, 14));
        meseros.add(new Mesero(7, "Valentina Díaz", 4, 22));
        meseros.add(new Mesero(8, "Santiago Ramírez", 5, 30));
        meseros.add(new Mesero(9, "Mariana Gómez", 3, 16));
        meseros.add(new Mesero(10, "Felipe Morales", 4, 19));
        
      //Creacion del restaurante
    	Restaurante restaurante = new Restaurante("Aura Gourmet",LocalTime.of(7, 0));
    	restaurante.setMesas(mesas);
    	restaurante.setMeseros(meseros);
        
        menu(restaurante);
    }

    static void menu(Restaurante restaurante){
        boolean encendido = true;
        do {
            System.out.println("¿Qué desea hacer?" + "\n" +
                    "1. Realizar una reserva." + "\n" +
                    "2. Realizar un domicilio." + "\n" +
                    "3. Realizar un pedido." + "\n" +
                    "4. Realizar un pago." + "\n" +
                    "5. Calificar el servicio." + "\n" +
                    "6. Salir." + "\n" +
                    "Ingrese un número para elegir una opción.");
            Scanner valorEntrada = new Scanner(System. in);
            int eleccion = 0;
            eleccion = valorEntrada.nextInt();
            switch (eleccion) {
                case 1:
                    //encadenar la funcionalidad 1
                	System.out.println("Bienvenido/a al restaurante "+ restaurante.getNombre()+", por favor digite la siguiente información para realizar su reserva");
                	
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
                    //encadenar la funcionalidad 5
                    encendido = false;
                    break;
                case 6:
                    //cerrar el prograama
                    encendido = false;
                    break;
            }
        } while(encendido);
    }
}
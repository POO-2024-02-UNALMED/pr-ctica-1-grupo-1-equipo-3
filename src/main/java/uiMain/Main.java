package uiMain;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    static void menu(){
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
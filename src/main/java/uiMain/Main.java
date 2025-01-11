package uiMain;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    static void menu(){
        boolean encendido = true;
        do {
            System.out.println(" Que desea realizar?" + "\n" +
                    "1. Realizar Reserva." + "\n" +
                    "2. Realizar Domicilio." + "\n" +
                    "3. Realizar Pedido." + "\n" +
                    "4. Realizar pago." + "\n" +
                    "5.Califcación del servicio." + "\n" +
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
                    encendido = false;
                    break;
            }
        } while(encendido);
    }
}

//La intención de esta interface es poder reutilizar funciones en cualquiera de las interacciones


package uiMain;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public interface Utilidad {

    //Este metodo se encarga de solicitar un entero entre 1 y 5 y maneja la excepcion en caso de que se ingrese algo fuera de ese rango
    public static int solicitarEntero() {
        Scanner scanner = new Scanner(System.in);
        int numero = 0;

        while (true) {
            try {
                System.out.print("Ingresa un número entre 1 y 5: ");
                numero = Integer.parseInt(scanner.nextLine());

                if (numero >= 1 && numero <= 5) {
                    return numero; // Retorna el número si es válido
                } else {
                    System.out.println("El número no está en el rango permitido. Intenta de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingresa un número entero en el rango establecido.");
            }
        }
    }
    
    public static String formatoPrecio(double precio) {
        NumberFormat formato = NumberFormat.getNumberInstance(Locale.getDefault());
        return formato.format(precio);
    }
}


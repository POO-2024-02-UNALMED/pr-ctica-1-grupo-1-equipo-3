
//La intención de esta interface es poder reutilizar funciones en cualquiera de las interacciones


package uiMain;
import gestorAplicacion.Calificacion;

import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public interface Utilidad {

    //Este metodo se encarga de solicitar un entero entre los parametros de entrada y maneja la excepcion en caso de que se ingrese algo fuera de ese rango
    public static int solicitarEntero(int x, int y) {
        Scanner scanner = new Scanner(System.in);
        int numero = 0;

        while (true) {

            try {
                System.out.print("Ingresa un número entre " + x + " y " + y + ": ");
                numero = Integer.parseInt(scanner.nextLine());

                if (numero >= x && numero <= y) {
                    return numero; // Retorna el número si es válido
                } else {
                    System.out.println("El número no está en el rango permitido. Intenta de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingresa un número entero en el rango establecido.");
            }
        }
    }

    //Este metodo se encarga de solicitar una identificación, en caso de que se ingrese un dato no númerico, maneja la excepcion
    public static Long solicitarId() {
        Scanner scanner = new Scanner(System.in);
        long numero = 0;

        while (true) {
            try {
                System.out.print("Ingrese su identificación para realizar la calificación: ");
                numero = Long.parseLong(scanner.nextLine());
                return numero; // Retorna el número si es válido

            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingresa un número válido.");
            }
        }
    }
    
    public static String formatoPrecio(double precio) {
        NumberFormat formato = NumberFormat.getNumberInstance(Locale.getDefault());
        return formato.format(precio);
    }

    // Metodo encargado de aplicar un descuento respecto a una calificación
    public static String aplicarDescuento(Calificacion calificacion, int totalFactura) {
        if (calificacion != null) {
            double promedio  = calificacion.getPromedioCalificacion();
            int descuento;

            if (promedio <= 2) {
                descuento = 10;  // 10% de descuento
                totalFactura -= (totalFactura * descuento) / 100;
                return "Total: " + totalFactura + "\n" +
                        "Descuento aplicado: 10% de descuento";
            } else if (promedio <=3) {
                descuento = 5;   // 5% de descuento
                totalFactura -= (totalFactura * descuento) / 100;
                return "Total: " + totalFactura + "\n" +
                        "Descuento aplicado: 5% de descuento";
            } else {
                descuento = 0;   // sin descuento
                totalFactura -= (totalFactura * descuento) / 100;
                return "Total: " + totalFactura + "\n" +
                        "Descuento aplicado: 5% de descuento";
            }

        }
        return null;
    }
}


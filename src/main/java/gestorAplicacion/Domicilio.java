package gestorAplicacion;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.Serializable;

public class Domicilio implements Serializable {

    // Atributos
    private static ArrayList<Domicilio> domicilios = new ArrayList<Domicilio>();
    private Cliente cliente;
    private Map<String, Integer> pedidoDomicilio;
    private String direccion;
    private boolean domicilioPrioritario;
    private int costoEnvio;

    // Constructor
    public Domicilio(Cliente cliente, Map<String, Integer> pedidoDomicilio, String direccion, boolean domicilioPrioritario, int costoEnvio) {
        this.cliente = cliente;
        this.pedidoDomicilio = pedidoDomicilio;
        this.direccion = direccion;
        this.domicilioPrioritario = domicilioPrioritario;
        this.costoEnvio = costoEnvio;
        domicilios.add(this);
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Map<String, Integer> getPedidoDomicilio() {
        return pedidoDomicilio;
    }

    public void setPedidoDomicilio(Map<String, Integer> pedidoDomicilio) {
        this.pedidoDomicilio = pedidoDomicilio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isDomicilioPrioritario() {
        return domicilioPrioritario;
    }

    public void setDomicilioPrioritario(boolean domicilioPrioritario) {
        this.domicilioPrioritario = domicilioPrioritario;
    }

    public int getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(int costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public static ArrayList<Domicilio> getDomicilios() {
        return domicilios;
    }

    // Método para ingresar datos del cliente y del domicilio
    public void ingresarDatosDomicilio() {
        Scanner scanner = new Scanner(System.in);

        // Crear un nuevo cliente y permitir al usuario ingresar sus datos
        Cliente nuevoCliente = new Cliente();
        System.out.println("Ingrese los datos del cliente:");
        nuevoCliente.ingresarDatos();
        this.cliente = nuevoCliente;

        // Ingresar la dirección del domicilio
        System.out.print("Ingrese la dirección del domicilio: ");
        this.direccion = scanner.nextLine();

        // Ingresar si es un domicilio prioritario
        System.out.print("¿El domicilio es prioritario? (true/false): ");
        this.domicilioPrioritario = scanner.nextBoolean();

        System.out.println("Datos del domicilio ingresados correctamente.");
    }
    
    public void seleccionarAlimentos(Almacen almacen) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> pedido = new TreeMap<>(); // Utilizamos Map con TreeMap como implementación
        boolean continuarSeleccion = true;
        double costoTotal = 0; // Variable para acumular el costo total

        System.out.println("\nSeleccione los alimentos del menú:");

        // Mostrar el menú con números asociados
        Menu[] menuItems = Menu.values();
        while (continuarSeleccion) {
            System.out.println("\nMenú disponible:");
            for (int i = 0; i < menuItems.length; i++) {
                System.out.println((i + 1) + ". " + menuItems[i].name() + " (Precio: " + menuItems[i].getPrecio() + ")");
            }

            System.out.print("\nIngrese el número del alimento que desea (o escriba 0 para finalizar): ");
            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                if (opcion == 0) { // Salir si el usuario ingresa 0
                    continuarSeleccion = false;
                    break;
                }

                if (opcion < 1 || opcion > menuItems.length) {
                    System.out.println("Opción inválida. Intente nuevamente.");
                    continue;
                }

                // Obtener el alimento seleccionado
                Menu alimentoSeleccionado = menuItems[opcion - 1];

                // Verificar disponibilidad en el almacén
                if (!almacen.verificarDisponibilidad(alimentoSeleccionado)) {
                    System.out.println("Lo sentimos, el plato " + alimentoSeleccionado.name() + " no está disponible actualmente. Por favor, seleccione otra opción.");
                    continue;
                }

                // Solicitar la cantidad
                System.out.print("Ingrese la cantidad que desea de " + alimentoSeleccionado.name() + ": ");
                int cantidad;
                try {
                    cantidad = Integer.parseInt(scanner.nextLine());
                    if (cantidad <= 0) {
                        System.out.println("La cantidad debe ser un número mayor a 0. Intente nuevamente.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: La cantidad debe ser un número. Intente nuevamente.");
                    continue;
                }

                // Agregar al pedido
                pedido.put(alimentoSeleccionado.name(), pedido.getOrDefault(alimentoSeleccionado.name(), 0) + cantidad);
                costoTotal += alimentoSeleccionado.getPrecio() * cantidad; // Sumar al costo total
                System.out.println("Agregado: " + cantidad + " de " + alimentoSeleccionado.name());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }

        // Mostrar el resumen del pedido
        System.out.println("\nPedido finalizado. Resumen:");
        pedido.forEach((alimento, cantidad) -> {
            System.out.println("- " + alimento + ": " + cantidad + " unidades");
        });

        // Mostrar el costo total del pedido
        System.out.printf("\nCosto total del pedido: $%.2f\n", costoTotal);
        System.out.println("Gracias por su pedido.");
    }
}

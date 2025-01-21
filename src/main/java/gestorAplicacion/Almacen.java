package gestorAplicacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Almacen implements Serializable {

    private List<String> nombres;
    private List<Integer> cantidades;

    // Constructor para inicializar el inventario
    public Almacen() {
        this.nombres = new ArrayList<>();
        this.cantidades = new ArrayList<>();
        inicializarInventario();
    }

    // Método para inicializar el inventario con cantidades predeterminadas
    private void inicializarInventario() {
    	agregarProducto("Salmón", 5);
    	agregarProducto("Cordero", 5);
        agregarProducto("Langosta", 5);
        agregarProducto("Mantequilla", 10);
        agregarProducto("Limón", 8);
        agregarProducto("Pasta", 15);
        agregarProducto("Tomate", 20);
        agregarProducto("Albahaca", 10);
        agregarProducto("Hierbas Finas", 12);
        agregarProducto("Aceite de Oliva", 18);
        agregarProducto("Cordero", 6);
        agregarProducto("Ajo", 14);
        agregarProducto("Pulpo", 4);
        agregarProducto("Pimentón", 7);
        agregarProducto("Lomo de Buey", 5);
        agregarProducto("Sal", 25);
        agregarProducto("Pimienta", 20);
        agregarProducto("Atún", 9);
        agregarProducto("Aguacate", 8);
        agregarProducto("Salsa de Soya", 10);
        agregarProducto("Ravioli", 6);
        agregarProducto("Trufa", 5);
        agregarProducto("Queso Parmesano", 15);
    }

    // Método para agregar productos al inventario
    public void agregarProducto(String nombre, int cantidad) {
        int index = nombres.indexOf(nombre);
        if (index != -1) {
            cantidades.set(index, cantidades.get(index) + cantidad);
        } else {
            nombres.add(nombre);
            cantidades.add(cantidad);
        }
    }

    // Método para verificar disponibilidad de un menú
    public boolean verificarDisponibilidad(Menu menu) {
        for (String ingrediente : menu.ingredientes) {
            int index = nombres.indexOf(ingrediente);
            if (index == -1 || cantidades.get(index) <= 0) {
                return false;
            }
        }
        return true;
    }

    // Método para sugerir menús disponibles
    public List<String> sugerirMenus() {
        List<String> menusSugeridos = new ArrayList<>();
        for (Menu menu : Menu.values()) {
            if (verificarDisponibilidad(menu)) {
                menusSugeridos.add(menu.nombre);
            }
        }
        return menusSugeridos;
    }

    // Método para mostrar el inventario actual
    public void mostrarInventario() {
        System.out.println("Inventario actual:");
        for (int i = 0; i < nombres.size(); i++) {
            System.out.println("Producto: " + nombres.get(i) + ", Cantidad: " + cantidades.get(i));
        }
    }

    // Método para preparar un menú (reducir ingredientes del inventario)
    public boolean prepararMenu(Menu menu) {
        if (verificarDisponibilidad(menu)) {
            for (String ingrediente : menu.ingredientes) {
                int index = nombres.indexOf(ingrediente);
                cantidades.set(index, cantidades.get(index) - 1);
            }
            return true;
        } else {
            System.out.println("No hay suficientes ingredientes para preparar " + menu.nombre);
            return false;
        }
    }
}
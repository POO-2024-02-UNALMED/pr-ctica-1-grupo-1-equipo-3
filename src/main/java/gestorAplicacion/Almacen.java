package gestorAplicacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Almacen implements Serializable {
    private static ArrayList<Almacen> almacen = new ArrayList<Almacen>();
    private List<String> nombres;
    private List<Integer> cantidades;

    // Constructor para inicializar el inventario
    public Almacen() {
        this.nombres = new ArrayList<>();
        this.cantidades = new ArrayList<>();
        inicializarInventario();
        almacen.add(this);
    }

    // Método para inicializar el inventario con cantidades predeterminadas
    private void inicializarInventario() {
    	agregarProducto("salmon", 5);
    	agregarProducto("cordero", 5);
        agregarProducto("langosta", 5);
        agregarProducto("mantequilla", 10);
        agregarProducto("limon", 8);
        agregarProducto("pasta", 15);
        agregarProducto("tomate", 20);
        agregarProducto("albahaca", 10);
        agregarProducto("hierbas finas", 12);
        agregarProducto("aceite de oliva", 18);
        agregarProducto("ajo", 14);
        agregarProducto("pulpo", 4);
        agregarProducto("pimentón", 7);
        agregarProducto("lomo de buey", 5);
        agregarProducto("sal", 25);
        agregarProducto("pimienta", 20);
        agregarProducto("atun", 9);
        agregarProducto("aguacate", 8);
        agregarProducto("aalsa de soya", 10);
        agregarProducto("ravioli", 6);
        agregarProducto("trufa", 5);
        agregarProducto("queso parmesano", 15);
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

    public static ArrayList<Almacen> getAlmacen() {
        return almacen;
    }
    
    public void actualizarInventario(Menu menu, int cantidad) {
        for (String ingrediente : menu.ingredientes) {
            int index = nombres.indexOf(ingrediente);
            if (index != -1) {
                cantidades.set(index, cantidades.get(index) - cantidad);
            }
        }
    }
}
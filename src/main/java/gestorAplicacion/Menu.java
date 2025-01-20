package gestorAplicacion;
import java.io.Serializable;

public enum Menu implements Serializable{
    LANGOSTA("Langosta", 120000, new String[]{"Langosta", "Mantequilla", "Limón"}),
    SALMON_AHUMADO("Salmón Ahumado", 85000, new String[]{"Salmón", "Hierbas Finas", "Aceite de Oliva"}),
    CORDERO("Cordero", 95000, new String[]{"Cordero", "Hierbas Finas", "Ajo"}),
    PULPO_A_LA_GALLEGA("Pulpo a la Gallega", 110000, new String[]{"Pulpo", "Pimentón", "Aceite de Oliva"}),
    PASTA_FRESCA("Pasta Fresca", 60000, new String[]{"Pasta", "Tomate", "Albahaca"}),
    LOMO_DE_BUEY("Lomo de Buey", 135000, new String[]{"Lomo de Buey", "Sal", "Pimienta"}),
    TARTAR_DE_ATUN("Tartar de Atún", 98000, new String[]{"Atún", "Aguacate", "Salsa de Soya"}),
    RAVIOLI_DE_TRUFA("Ravioli de Trufa", 115000, new String[]{"Ravioli", "Trufa", "Queso Parmesano"});
	
    final String nombre;
    private final double precio;
    final String[] ingredientes;
	
	Menu(String nombre, double precio, String[] ingredientes) {
        this.nombre = nombre;
        this.precio = precio;
        this.ingredientes = ingredientes;
    }
	
	public static String[] obtenerTodosLosIngredientes() {
        // Obtener todos los ingredientes de los platos.
        int totalIngredientes = 0;
        for (Menu plato : Menu.values()) {
            totalIngredientes += plato.getIngredientes().length;
        }

        String[] ingredientes = new String[totalIngredientes];
        int index = 0;
        for (Menu plato : Menu.values()) {
            for (String ingrediente : plato.getIngredientes()) {
                ingredientes[index++] = ingrediente;
            }
        }
        return ingredientes;
    }
	
	public static boolean ingredienteEstaDuplicado(String ingrediente, String[] todosIngredientes) {
        int contador = 0;
        for (String ing : todosIngredientes) {
            if (ing.equals(ingrediente)) {
                contador++;
                if (contador > 1) {
                    return true;
                }
            }
        }
        return false;
    }
	
	public static boolean platoContieneAlergia(Menu plato, String[] alergias) {
        for (String ingrediente : plato.getIngredientes()) {
            for (String alergia : alergias) {
                if (ingrediente.equalsIgnoreCase(alergia)) {
                    return true;
                }
            }
        }
        return false;
	}
	
	public String[] getIngredientes() {
        return ingredientes;
    }
	
	public double getPrecio() {
		return precio;
	}
	
	public String getNombre() {
		return nombre;
	}
}

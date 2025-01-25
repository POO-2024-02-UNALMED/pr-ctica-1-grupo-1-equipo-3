package gestorAplicacion;
import java.io.Serializable;

public enum Menu implements Serializable{
    LANGOSTA("Langosta", 120000, new String[]{"langosta", "mantequilla", "limon"}),
    SALMON_AHUMADO("Salmón Ahumado", 85000, new String[]{"salmon", "hierbas finas", "aceite de oliva"}),
    CORDERO("Cordero", 95000, new String[]{"cordero", "hierbas finas", "ajo"}),
    PULPO_A_LA_GALLEGA("Pulpo a la Gallega", 110000, new String[]{"pulpo", "pimenton", "aceite de oliva"}),
    PASTA_FRESCA("Pasta Fresca", 60000, new String[]{"pasta", "tomate", "albahaca"}),
    LOMO_DE_BUEY("Lomo de Buey", 135000, new String[]{"lomo de buey", "sal", "pimienta"}),
    TARTAR_DE_ATUN("Tartar de Atún", 98000, new String[]{"atun", "aguacate", "salsa de soya"}),
    RAVIOLI_DE_TRUFA("Ravioli de Trufa", 115000, new String[]{"ravioli", "trufa", "queso parmesano"});
	
    public final String nombre;
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

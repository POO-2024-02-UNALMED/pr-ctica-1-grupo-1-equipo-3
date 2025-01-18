package gestorAplicacion;
import java.io.Serializable;

public enum Menu implements Serializable{
    LANGOSTA("Langosta", 120000, new String[]{"Langosta", "Mantequilla", "Limón"}),
    SALMON_AHUMADO("Salmón Ahumado", 85000, new String[]{"Salmón", "Hierbas finas", "Aceite de Oliva"}),
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
}
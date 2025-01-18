package gestorAplicacion;
import java.util.Map;
import java.io.Serializable;
public class Domicilio implements Serializable{
	
	// Atributos
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

}

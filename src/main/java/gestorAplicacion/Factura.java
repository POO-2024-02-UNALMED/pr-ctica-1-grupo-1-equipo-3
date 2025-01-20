package gestorAplicacion;
import java.io.Serializable;
import java.util.Comparator;

public class Factura implements Serializable{
    private int descuento;
    private Calificacion calificacion;
    private Mesero mesero;
    private Restaurante restaurante;
    private Cliente cliente;
    private double totalFactura;
    private double propina;

    public Factura(int descuento, Restaurante restaurante, Cliente cliente, double totalFactura, double propina) {
        this.descuento = descuento;
        this.restaurante = restaurante;
        this.cliente = cliente;
        this.totalFactura = totalFactura;
        this.propina = propina;
    }

    @Override
    public String toString(){
        return "\n=====================================" + "\n" +
                "         FACTURA DE CONSUMO          " + "\n" +
                "Restaurante: " + this.restaurante.getNombre() + "\n" +
                "Cliente: " + this.cliente.getNombre() + "\n" +
                "Mesero encargado: " + this.cliente.getReserva().getMesero() + "\n" +
                "-------------------------------------" + "\n" +
                "Total: " + this.totalFactura + "\n" +
                "Descuento aplicado: " + "\n" +
                "Calificación del servicio: " + this.calificacion.getPromedioCalificacion() + "\n" +
                "\n=====================================" + "\n" +
                "Gracias por visitarnos. ¡Esperamos verlo pronto!"  + "\n" +
                "=====================================";
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public void calcularPuntosPorGasto() {
        restaurante.calcularPuntosPorGasto(cliente, totalFactura);
    }

    public void asignarPuntosPorPropina() {
        restaurante.calcularPuntosPorGasto(cliente, propina);
    }

    // La factura queda con su calificacion
    public void asociarCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    //Descuento basado en la calificacion
    public void aplicarDescuento(Calificacion calificacion) {
        if (calificacion != null) {
            double promedio  = calificacion.getPromedioCalificacion();

            if (promedio <= 2) {
                this.descuento = 10;  // 10% de descuento
            } else if (promedio <=3) {
                this.descuento = 5;   // 5% de descuentp
            } else {
                this.descuento = 0;   // sin descuento
            }

            totalFactura -= (totalFactura * descuento) / 100;

        }
    }

    //Este metodo se encarga de organizar la lista de meseros respecto a su califacion en la factura
    public void prioridadMeseros() {
        restaurante.getMeseros().sort(new Comparator<Mesero>() {
            @Override
            public int compare(Mesero o1, Mesero o2) {
                return Double.compare(o2.getPromCalificaciones(), o1.getPromCalificaciones());
                }
        });

    }
}

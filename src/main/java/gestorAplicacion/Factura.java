package gestorAplicacion;

public class Factura {
    private int totalPagar;
    private int descuento;
    private Calificacion calificacion;
    private Mesero mesero;

    public Factura(int totalPagar, int descuento) {
        this.totalPagar = totalPagar;
        this.descuento = descuento;
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

            totalPagar -= (totalPagar * descuento) / 100;

        }
    }

    //Despues del pago se retroalimenta al mesero
    public void notificarMesero() {
        if (calificacion != null && mesero != null) {

        }
    }
}

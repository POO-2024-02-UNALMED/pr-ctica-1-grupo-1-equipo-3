package gestorAplicacion;

import java.util.Comparator;

public class Factura {
    private int totalPagar;
    private int descuento;
    private Calificacion calificacion;
    private Mesero mesero;
    private Restaurante restaurante;

    public Factura(int totalPagar, int descuento, Restaurante restaurante) {
        this.totalPagar = totalPagar;
        this.descuento = descuento;
        this.restaurante = restaurante;
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

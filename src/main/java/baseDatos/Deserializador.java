package baseDatos;

import gestorAplicacion.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.*;

public class Deserializador {

    public static <T extends Serializable> void deserializar(ArrayList<T> lista, String nombre){
        File archivo = new File("");
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            File ruta = new File(archivo.getAbsolutePath() + "/src/baseDatos/temp/" + nombre + ".txt");
            fis = new FileInputStream(ruta);
            ois = new ObjectInputStream(fis);
            lista.addAll((ArrayList<T>) ois.readObject()) ;
            ois.close();
            fis.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void deserializarListas(){
        Deserializador.deserializar(Almacen.getAlmacen(), "almacen");
        Deserializador.deserializar(Calificacion.getCalificaciones(), "calificaciones");
        Deserializador.deserializar(Cliente.getClientes(),"clientes");
        Deserializador.deserializar(Domicilio.getDomicilios(),"domicilios");
        Deserializador.deserializar(Factura.getFacturas(),"facturas");
        Deserializador.deserializar(Mesa.getMesas(),"mesas");
        Deserializador.deserializar(Mesero.getMeseros(),"meseros");
        Deserializador.deserializar(Pedido.getPedidos(),"pedidos");
        Deserializador.deserializar(Reserva.getReservas(),"reservas");
        Deserializador.deserializar(Restaurante.getRestaurante(),"restaurante");
    }
}

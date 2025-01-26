package baseDatos;
import gestorAplicacion.*;
import java.io.*;
import java.util.ArrayList;

public class Serializador {

    public static void serializar(ArrayList<?extends Serializable> lista, String nombre){
        File archivo = new File("");
        try {
            File ruta = new File(archivo.getAbsolutePath()+"/src/main/java/baseDatos/temp/"+nombre+".txt");
            FileOutputStream fileOut = new FileOutputStream(ruta);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(lista);
            objectOut.close();
            fileOut.close();
            System.out.println("El objeto ha sido serializado");
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no se encontró");
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error al serializar el objeto");
        }
    }

    public static void serializarListas(){
        Serializador.serializar(Almacen.getAlmacen(), "almacen");
        Serializador.serializar(Calificacion.getCalificaciones(), "calificaciones");
        Serializador.serializar(Cliente.getClientes(),"clientes");
        Serializador.serializar(Domiciliario.getListaDomiciliarios(), "listaDomiciliarios");
        Serializador.serializar(Domicilio.getDomicilios(),"domicilios");
        Serializador.serializar(Factura.getFacturas(),"facturas");
        Serializador.serializar(Mesa.getMesas(),"mesas");
        Serializador.serializar(Mesero.getMeseros(),"meseros");
        Serializador.serializar(Persona.getPersonas(), "personas");
        Serializador.serializar(Pedido.getPedidos(),"pedidos");
        Serializador.serializar(Reserva.getReservas(),"reservas");
        Serializador.serializar(Restaurante.getRestaurante(),"restaurante");
        Serializador.serializar(Restaurante.getIdConReservas(), "ListaIdConReservas");
        Serializador.serializar(Restaurante.getListaClientes(), "ListaClientes");
    }
}


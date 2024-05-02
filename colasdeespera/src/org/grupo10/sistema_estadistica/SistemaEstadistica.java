package org.grupo10.sistema_estadistica;

import org.grupo10.modelo.dto.EstadisticaDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SistemaEstadistica {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String tipo = "Estadistica";
    private static  ObjectOutputStream outputStream;
    private static  ObjectInputStream inputStream;
    private Socket socket;


    public SistemaEstadistica(){
        Socket socket = null;
        try {
            socket = new Socket(HOST, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void ejecucion(){
        try {
            System.out.println(tipo);
            outputStream.writeObject(tipo);
            outputStream.flush();
            while (true) {

                //Object response = inputStream.readObject();
                //System.out.println("Respuesta del servidor: " + response);
                //Aca se supone que el servidor me envio un turno y un box
                //Hay que agregar la logica para que maneje todo

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EstadisticaDTO pidoEstadisticas() throws IOException, ClassNotFoundException {
        outputStream.writeObject("Pido estadistica");
        outputStream.flush();

//        if (inputStream.available() == 0) {
//            throw new IOException("No hay respuesta del servidor");
//        }

        Object res = inputStream.readObject();

        if (res == null) {
            throw new IOException("La respuesta del servidor es nula");
        }

        if (!(res instanceof EstadisticaDTO)) {
            throw new ClassNotFoundException("La respuesta del servidor no es una instancia de EstadisticaDTO");
        }

        EstadisticaDTO estadisticaDTO = (EstadisticaDTO) res;
        return estadisticaDTO;
    }

}
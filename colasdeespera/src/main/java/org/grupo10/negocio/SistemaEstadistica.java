package org.grupo10.negocio;

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

                Object response = inputStream.readObject();
                System.out.println("Respuesta del servidor: " + response);
                //Aca se supone que el servidor me envio un turno y un box
                //Hay que agregar la logica para que maneje todo
                Thread.sleep(5000);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
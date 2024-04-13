package org.grupo10.negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SistemaEstadistica {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String tipo = "Estadistica";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println(tipo);
            outputStream.writeObject(tipo);
            outputStream.flush();
            while (true) {
                Object response = inputStream.readObject();
                System.out.println("Respuesta del servidor: " + response);
                outputStream.writeObject("Hola desde: " + tipo);
                outputStream.flush();
                Thread.sleep(2000);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
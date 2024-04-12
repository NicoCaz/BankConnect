package org.grupo10.negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SistemaPantalla {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String tipo = "Pantalla";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Ingresa un mensaje: ");
                String message = scanner.nextLine();
                outputStream.writeObject(message);
                outputStream.flush();

                Object response = inputStream.readObject();
                System.out.println("Respuesta del servidor: " + response);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
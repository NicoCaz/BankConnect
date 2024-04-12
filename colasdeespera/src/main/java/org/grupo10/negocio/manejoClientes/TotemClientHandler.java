package org.grupo10.negocio.manejoClientes;
import org.grupo10.negocio.SocketServer;

import java.io.*;
import java.net.Socket;

public class TotemClientHandler extends BasicClientHandler {
    private Socket socket;
    private SocketServer server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running = true;



    public TotemClientHandler(Socket socket, SocketServer server, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.server = server;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try {
            while (running) {
                sendObject("Hola desde el server (a Totem)");
                Object received = inputStream.readObject();
                System.out.println("Mensaje recibido de cliente Totem: " + received);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            disconnectClient();
        }
    }

    @Override
    public void handleMessage(Object message) {
        System.out.println("Mensaje recibido de cliente Totem: " + message);
        server.respuesta(message,this);
    }

    @Override
    public void sendObject(Object message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnectClient() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.removeClient(this);
    }
}
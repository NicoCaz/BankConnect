package org.grupo10.sistema_servidor.manejoClientes;

import org.grupo10.exception.ClienteRepetidoException;
import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.StateSocketServerPrimario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TotemClientHandler extends BasicClientHandler {
    private Socket socket;
    private StateSocketServerPrimario server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running = true;
    private int id;



    public TotemClientHandler(Socket socket, StateSocketServerPrimario server, ObjectInputStream inputStream, ObjectOutputStream outputStream, int id) {
        this.socket = socket;
        this.server = server;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Object received = inputStream.readObject();
                handleMessage(received);
            }
        } catch (IOException | ClassNotFoundException | ClienteRepetidoException e) {
            e.printStackTrace();
        } finally {
            disconnectClient();
        }
    }

    @Override
    public void handleMessage(Object message) throws ClienteRepetidoException {
        System.out.println("Mensaje recibido de cliente Totem: " + message);
        if(message instanceof Turno){
            server.addTurnosEnEspera((Turno) message);
        }

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
    }

    private boolean validoDNI(Object turno){
        return true;
    }

    public int getID() {
        return this.id;
    }
}
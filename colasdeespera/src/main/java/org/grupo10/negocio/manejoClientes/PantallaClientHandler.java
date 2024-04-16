package org.grupo10.negocio.manejoClientes;

import org.grupo10.modelo.ITurno;
import org.grupo10.modelo.Turno;
import org.grupo10.negocio.SocketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PantallaClientHandler extends BasicClientHandler {
    private Socket socket;
    private SocketServer server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running = true;
    private int id;

    public PantallaClientHandler(Socket socket, SocketServer server, ObjectInputStream inputStream, ObjectOutputStream outputStream,int id) {
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
                System.out.println("Mensaje recibido de Pantalla: " + received);
                //Aca mandale el turno que se tiene que mostrar en la pantalla con su box
                Turno turnoATender=new Turno("5461231441");
                turnoATender.setBox(1);
                envioTurnoAPantalla(turnoATender);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            disconnectClient();
        }
    }

    @Override
    public void handleMessage(Object message) {
        System.out.println("Mensaje recibido de cliente Box: " + message);
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
    }

    public void envioTurnoAPantalla(ITurno turnoLlamado){
        try {
            outputStream.writeObject(turnoLlamado);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getID() {
        return this.id;
    }
}
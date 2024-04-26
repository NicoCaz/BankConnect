package org.grupo10.sistema_servidor.manejoClientes;

import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.SocketServer;

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

    public PantallaClientHandler(Socket socket, SocketServer server, ObjectInputStream inputStream, ObjectOutputStream outputStream, int id) {
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
                //sendObject("Hola desde el server (a Box)");
                Object received = inputStream.readObject();
                //System.out.println("Mensaje recibido de cliente Box: " + received);
                handleMessage(received);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            disconnectClient();
        }
    }

    @Override
    public void handleMessage(Object message) {


        if(message instanceof Turno){
            System.out.println("Mensaje recibido de cliente Pantalla: " + message);
            //sendObject(message);
        }else{
            server.respuesta(message,this);
        }

    }

    @Override
    public void sendObject(Object message) {

        try {
            System.out.println("Envio a pantalla: "+message );
            outputStream.writeObject(message);
            outputStream.flush();
            System.out.println("Mensaje enviado: "+message);
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
    public int getID() {
        return this.id;
    }
}

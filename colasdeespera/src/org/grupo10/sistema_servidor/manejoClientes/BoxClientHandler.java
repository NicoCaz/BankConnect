package org.grupo10.sistema_servidor.manejoClientes;

import org.grupo10.modelo.TurnoFinalizado;
import org.grupo10.sistema_servidor.StateSocketServerPrimario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BoxClientHandler extends BasicClientHandler {
    private Socket socket;
    private StateSocketServerPrimario server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running = true;
    private int id;

    public BoxClientHandler(Socket socket, StateSocketServerPrimario server, ObjectInputStream inputStream, ObjectOutputStream outputStream, int  id) {
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
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void mandoPersonasEnEspera(int cant){
        try {
            outputStream.writeObject(cant);
            outputStream.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void handleMessage(Object message) {
        System.out.println("Mensaje recibido de cliente Box: " + message);


        if(message instanceof String){
            if(message.equals("Pido siguiente")) {

                server.siguienteTurno(server.getUltimoTurno(this),this);


            } if(message.equals("Finalizar Turno")) {
                server.cantidadEnEspera();
            }

        }else {
            if (message instanceof TurnoFinalizado)
                server.finalizarTurno((TurnoFinalizado) message);
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
    public int getID() {
        return this.id;
    }


}
package org.grupo10.negocio.manejoClientes;

import org.grupo10.negocio.SocketServer;
import org.grupo10.negocio.manejoClientes.BasicClientHandler;

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
}

package org.grupo10.main;

import org.grupo10.controlador.ControladorServidor;
import org.grupo10.negocio.SocketServer;

public class MainServidor {
    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.start();

    }
}


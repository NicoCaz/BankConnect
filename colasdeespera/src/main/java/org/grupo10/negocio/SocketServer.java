package org.grupo10.negocio;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    private static final int PORT = 8080;
    private List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.start();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en el puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nueva conexión entrante");

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(Object message) {
        for (ClientHandler client : clients) {
            client.sendObject(message);
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
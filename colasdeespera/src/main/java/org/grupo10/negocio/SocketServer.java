package org.grupo10.negocio;
import org.grupo10.negocio.manejoClientes.BasicClientHandler;
import org.grupo10.negocio.manejoClientes.TotemClientHandler;
import org.grupo10.negocio.manejoClientes.BoxClientHandler;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    private static final int PORT = 8080;
    private List<TotemClientHandler> clients = new ArrayList<>();
    private List<BoxClientHandler> premiumClients = new ArrayList<>();

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
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                Object var =inputStream.readObject();
                BasicClientHandler clientHandler;
                System.out.println(var);
                if ("Box".equals(var)) {
                    clientHandler = new BoxClientHandler(clientSocket, this,inputStream, outputStream);
                    premiumClients.add((BoxClientHandler) clientHandler);
                } else {
                    clientHandler = new TotemClientHandler(clientSocket, this,inputStream, outputStream);
                    clients.add((TotemClientHandler) clientHandler);
                }
                clientHandler.start();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void respuesta(Object res,BasicClientHandler clientHandler) {
        clientHandler.sendObject(res);
    }


    public void removeClient(BasicClientHandler clientHandler) {
        clients.remove(clientHandler);
        if (clientHandler instanceof BoxClientHandler) {
            premiumClients.remove((BoxClientHandler) clientHandler);
        }
    }


}
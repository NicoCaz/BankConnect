package org.grupo10.negocio;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor implements Runnable {
    private static final int PUERTO = 8080;
    private static Servidor instancia;
    private ServerSocket serverSocket;

    private ArrayList<Socket> clientesTotem = new ArrayList<>();
    private ArrayList<Socket> clientesBox = new ArrayList<>();
    private ArrayList<Socket> clientesMonitor = new ArrayList<>();
    private ArrayList<Socket> clientesEstadisticas = new ArrayList<>();
    private boolean ejecutando;

    private Servidor() {

        ejecutando = false;
    }

    public static Servidor getInstancia() {
        if (instancia == null) {
            instancia = new Servidor();
        }
        return instancia;
    }

    public static void main(String[] args) {
        Servidor servidor = Servidor.getInstancia();
        servidor.iniciarServidor();
    }

    public void iniciarServidor() {
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PUERTO);
            System.out.println("Servidor Central iniciado en el puerto " + PUERTO);
            ejecutando = true;

            while (ejecutando) {
                Socket socket = serverSocket.accept();
                System.out.println("Nueva conexión entrante: " + socket.getInetAddress().getHostAddress());

                //Hilo de cada socket que se conecta al sevidor
                Thread hiloServidor = new Thread(new HiloServidor(socket));
                hiloServidor.start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el Servidor Central: " + e.getMessage());
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el servidor: " + e.getMessage());
            }
        }
    }

    public void detenerServidor() {
        ejecutando = false;
    }

    private class HiloServidor implements Runnable {
        private Socket socket;

        public HiloServidor(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());


                String tipoServidor = entrada.readUTF();

                switch (tipoServidor) {
                    case "box":
                        clientesBox.add(socket);
                    case "totem":
                        clientesTotem.add(socket);
                    case "monitor":
                        clientesMonitor.add(socket);
                    case "estadisticas":
                        clientesEstadisticas.add(socket);
                    default:
                        System.out.println("Tipo de cliente invalido");
                }

                System.out.println("Nuevo servidor conectado: " + tipoServidor);

                // Enviar confirmación de registro
                salida.writeUTF("Registro exitoso");
                salida.flush();

                while (true) {
                    String mensaje = entrada.readUTF();
                    System.out.println("Mensaje recibido del servidor: " + mensaje);

                    // Procesar el mensaje y enviar la respuesta
                    String respuesta = procesarMensaje(tipoServidor, mensaje);
                    salida.writeUTF(respuesta);
                    salida.flush();
                }
            } catch (IOException e) {
                System.err.println("Error en la comunicación con el servidor: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket: " + e.getMessage());
                }
            }
        }

        private String procesarMensaje(String tipoServidor, String mensaje) {
            // Implementar la lógica de procesamiento de mensajes según el tipo de servidor
            switch (tipoServidor) {
                case "box":
                    return "Respuesta para servidor Box";
                case "totem":
                    return "Respuesta para servidor Totem";
                case "pantalla":
                    return "Respuesta para servidor Pantalla";
                default:
                    return "Respuesta genérica";
            }
        }
    }
}
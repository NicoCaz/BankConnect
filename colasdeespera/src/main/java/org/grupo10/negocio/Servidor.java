package org.grupo10.negocio;

import org.grupo10.modelo.Turno;

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

                // Hilo de cada socket que se conecta al servidor
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
            ObjectInputStream entrada = null;
            ObjectOutputStream salida = null;
            System.out.println("entre a run");
            try {
                entrada = new ObjectInputStream(socket.getInputStream());

                salida = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Hilo aca 2");
                String tipoServidor = (String)entrada.readObject(); // Leer tipo de servidor como String
/*
                switch (tipoServidor) { // Utilizar String directamente en el switch
                    case "box":
                        clientesBox.add(socket);
                        break;
                    case "totem":
                        clientesTotem.add(socket);
                        break;
                    case "monitor":
                        clientesMonitor.add(socket);
                        break;
                    case "estadisticas":
                        clientesEstadisticas.add(socket);
                        break;
                    default:
                        System.out.println("Tipo de cliente invalido");
                }
*/
                System.out.println("Nuevo servidor conectado: " + tipoServidor);

                // Enviar confirmación de registro
                salida.writeUTF("Registro exitoso");
                salida.flush();

                while (true) {
                    Object mensaje = entrada.readObject();
                    System.out.println("Mensaje recibido del servidor: " + mensaje);

                    // Procesar el mensaje y enviar la respuesta
                    Object respuesta = procesarMensaje("exitossss", mensaje.toString());
                    salida.writeObject(respuesta);
                    salida.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error en la comunicación con el servidor: " + e.getMessage());
            } finally {
                try {
                    System.out.println("Hilo cerrado");
                    if (entrada != null) {
                        entrada.close();
                    }
                    if (salida != null) {
                        salida.close();
                    }
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket: " + e.getMessage());
                }
            }

            System.out.println("run?");

        }

        private Object procesarMensaje(String tipoServidor, String mensaje) {
            // Implementar la lógica de procesamiento de mensajes según el tipo de servidor
           return new Object();

        }
    }
}

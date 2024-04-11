package org.grupo10.negocio;

import java.io.*;
import java.net.Socket;
import java.util.List;




public class SistemaBox implements Runnable {
    private static final String SERVIDOR_CENTRAL_HOST = "127.0.0.1";
    private static final int SERVIDOR_CENTRAL_PUERTO = 8080;
    private Socket socketServidor;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private static final String TIPO_SERVIDOR = "box"; // O "totem", "pantalla", etc.
    private boolean ejecutando;

    public static void main(String[] args) {
        SistemaBox servidor = new SistemaBox();
        Thread hilo = new Thread(servidor);
        hilo.start();
    }

    @Override
    public void run() {
        ejecutando = true;
        while (ejecutando) {
            try {
                conectarAlServidorCentral();
                registrarseEnServidorCentral();
                System.out.println("Conexión establecida con el Servidor Central");


                while (ejecutando) {
                    enviarMensajeAlServidorCentral("Hola, Servidor Central");
                    recibirRespuestaDelServidorCentral();
                    Thread.sleep(5000); // Esperar 5 segundos antes de enviar otro mensaje
                }

            } catch (IOException e) {
                System.err.println("Error en la comunicación con el Servidor Central: " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Error de interrupción: " + e.getMessage());
            }
        }
    }

    public void detenerServidor() {
        ejecutando = false;
        try {
            socketServidor.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    private void conectarAlServidorCentral() throws IOException {
        socketServidor = new Socket(SERVIDOR_CENTRAL_HOST, SERVIDOR_CENTRAL_PUERTO);
        System.out.println("Conectado al Servidor Central");
        entrada = new ObjectInputStream(socketServidor.getInputStream());
        salida = new ObjectOutputStream(socketServidor.getOutputStream());
    }

    private void registrarseEnServidorCentral() throws IOException {
        salida.writeObject(TIPO_SERVIDOR);
        salida.flush();
        String confirmacion = entrada.readUTF();
        System.out.println("Respuesta del Servidor Central: " + confirmacion);
    }

    private void enviarMensajeAlServidorCentral(Object mensaje) throws IOException {
        salida.writeObject(mensaje);
        salida.flush();
        System.out.println("Mensaje enviado al Servidor Central: " + mensaje);
    }

    private void recibirRespuestaDelServidorCentral() throws IOException {

        Object respuesta = null;
        try {
            respuesta = entrada.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Respuesta del Servidor Central: " + respuesta);



    }

}

package org.grupo10.negocio;

import org.grupo10.modelo.Turno;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SistemaTotem {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String tipo = "Totem";
    private static  ObjectOutputStream outputStream;
    private static  ObjectInputStream inputStream;
    private Socket socket;

    public SistemaTotem(){
        Socket socket = null;
        try {
            socket = new Socket(HOST, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void ejecucion(){
        try {
            System.out.println(tipo);
            outputStream.writeObject(tipo);
            outputStream.flush();
            while (true) {
                // Manda el dni y espera a que el servidor logro captar el dni
                esperandoRespuestaServer();
                Object response = inputStream.readObject();
                System.out.println("Respuesta del servidor: " + response);
                Thread.sleep(5000);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void esperandoRespuestaServer(){
        try {
            outputStream.writeObject("Totem a la espera");
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Turno crearTurno(String dni) throws Exception {
        Turno t;
        if (dni.length() < 6) {
            throw new Exception("ERROR: Ingrese un DNI Valido");
        } else {
            t = new Turno(dni);
        }
        mandaDNI(t);
        return t;
    }

    public void cerrarTurno(Turno t){

    }

    public void mandaDNI(Object turno){
        try {
            outputStream.writeObject(turno);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
package org.grupo10.negocio;

import org.grupo10.controlador.ControladorPantalla;
import org.grupo10.modelo.Turno;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

public class SistemaPantalla extends Observable {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String tipo = "Pantalla";
    private static  ObjectOutputStream outputStream;
    private static  ObjectInputStream inputStream;
    private ControladorPantalla observador;
    private Socket socket;

    public SistemaPantalla(){

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
                esperandoRespuestaServer();
                Object response = inputStream.readObject();
                //System.out.println("Respuesta del servidor: " + response);

                if(response instanceof Turno){
                    reciveTurno(response);
                }

                //Aca se supone que el servidor me envio un turno y un box
                //Hay que agregar la logica para que maneje todo
                Thread.sleep(1000);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void esperandoRespuestaServer(){
        try {
            outputStream.writeObject("Pantalla a la espera");
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reciveTurno(Object turno) throws IOException, ClassNotFoundException {
        setChanged();
        notifyObservers(turno);
    }

}
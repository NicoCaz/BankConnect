package org.grupo10.negocio;

import org.grupo10.modelo.Turno;
import org.grupo10.negocio.manejoClientes.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer extends Thread{
    private static final int PORT = 8080;
    private List<TotemClientHandler> Totems = new ArrayList<>();
    private List<BoxClientHandler> boxClients = new ArrayList<>();
    private List<EstadisticaClientHandler> EstadisticaClients = new ArrayList<>();
    private List<PantallaClientHandler> PantallasClients = new ArrayList<>();
    private int idBoxs=0;
    private int idPantallas=0;
    private int idEstadisticas=0;
    private int idTotems=0;
    //////////////////////////////////////////////////////////////////////////////////////////
    private List<Turno> turnosEnEspera = new ArrayList<>();
    private List<Turno> turnosDisponibles = new ArrayList<>();

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en el puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nueva conexión entrante");
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                Object var =inputStream.readObject();
                BasicClientHandler clientHandler = null;
                System.out.println(var);
                if ("Box".equals(var)) {
                    clientHandler = new BoxClientHandler(clientSocket, this, inputStream, outputStream,++this.idBoxs);
                    System.out.println("cantidad de box "+this.idBoxs);
                    boxClients.add((BoxClientHandler) clientHandler);
                } else if("Totem".equals(var)) {
                    clientHandler = new TotemClientHandler(clientSocket, this, inputStream, outputStream,++this.idTotems);
                    Totems.add((TotemClientHandler) clientHandler);
                } else if("Estadistica".equals(var)) {
                    clientHandler = new EstadisticaClientHandler(clientSocket, this, inputStream, outputStream,++this.idEstadisticas);
                    EstadisticaClients.add((EstadisticaClientHandler) clientHandler);
                } else if("Pantalla".equals(var)) {
                    clientHandler = new PantallaClientHandler(clientSocket, this, inputStream, outputStream,++this.idPantallas);
                    PantallasClients.add((PantallaClientHandler) clientHandler);
                }
                clientHandler.start();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void iniciarServer(){
        Thread hilo = new Thread(this);
        hilo.start();
    }

    public void respuesta(Object res,BasicClientHandler clientHandler) {
        clientHandler.sendObject(res);
    }

    public String getUltimoTurno(){

        if(!turnosEnEspera.isEmpty()){
            Turno ultimoturno = this.turnosEnEspera.get(this.turnosEnEspera.size()-1);
            this.turnosEnEspera.remove(ultimoturno);
            for (Turno e : this.turnosEnEspera) {
                System.out.println(e.getDni());
            }
            return ultimoturno.getDni();
        }else{
            return null;
        }

    }

    public Turno getTurnosEnEspera() {
        return turnosEnEspera.get(1);
    }

    public void addTurnosEnEspera(Turno turnosEnEspera) {

        this.turnosEnEspera.add(turnosEnEspera) ;
        for (Turno e : this.turnosEnEspera) {
            System.out.println(e.getDni());
        }
    }

    public Turno getTurnosDisponibles() {
        return turnosDisponibles.get(1);
    }

    public void addTurnosDisponibles(Turno turnosDisponibles) {
        this.turnosDisponibles.add(turnosDisponibles);
    }
}
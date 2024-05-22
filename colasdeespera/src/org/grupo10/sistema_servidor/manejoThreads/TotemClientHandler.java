package org.grupo10.sistema_servidor.manejoThreads;

import org.grupo10.exception.ClienteRepetidoException;
import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.ServidorPrincipalState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TotemClientHandler extends Thread{

    private ServidorPrincipalState servidor;

    private boolean running;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;


    public TotemClientHandler(Socket socket, ServidorPrincipalState servidor) {
        this.servidor = servidor;
        try {
            System.out.println("Conectando a " + socket.getInetAddress().getHostAddress());
            this.ip = socket.getInetAddress().getHostAddress();
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Se conectó una terminal de registro con IP " + this.ip);
            this.running = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg, dni;
        while (running) {
            try {
                dni = in.readLine();
                System.out.println("DNI recibido: "+dni);
                synchronized (this.servidor.getTurnosEnEspera()) {
                    try {
                        Turno t= new Turno(dni);
                        this.servidor.getTurnosEnEspera().agregarTurno(t);
                        synchronized(this.servidor.getLogCreator()){
                            this.servidor.getLogCreator().logClientRegistro(t, new Date());
                        }
                        this.servidor.setCambios(true);
                        msg = "ACEPTADO";
                        this.servidor.enviarEstadisticas();
                    } catch (ClienteRepetidoException e) {
                        msg = "REPETIDO";
                    }
                }
                out.println(msg);
            } catch (IOException e1) {
                running = false;
                System.out.println("Se desconectó la terminal de registro con IP " + this.ip);
            }
        }
    }
}
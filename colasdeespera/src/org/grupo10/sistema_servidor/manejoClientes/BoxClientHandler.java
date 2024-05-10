package org.grupo10.sistema_servidor.manejoClientes;

import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.ControladorServidor;

import java.io.*;
import java.net.Socket;

public class BoxClientHandler extends Thread  {
    private int nroBox;
    private boolean running;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;

    public BoxClientHandler(Socket socket) {
        try {
            System.out.println("Conectando a " + socket.getInetAddress().getHostAddress());
            this.ip = socket.getInetAddress().getHostAddress();
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.nroBox = Integer.parseInt(this.in.readLine());
            System.out.println("Conectado a box " + nroBox);
            if (ControladorServidor.getInstance().boxesOcupados.add(this.nroBox)) {
                System.out.println("BOX ACEPTADO");
                this.out.println("ACEPTADO");
                System.out.println("Se conectó el box " + this.nroBox + " con IP " + this.ip);
                this.running = true;
            } else {
                this.out.println("OCUPADO");
                System.out.println("Se rechazó el box " + this.nroBox + " duplicado con IP " + this.ip);
                this.running = false;
            }
        } catch (IOException e) {
            System.out.println("ERROR HILO BOX: " + e.getMessage());
        }
    }
    public void run() {
        Object msg;
        while (running) {
            try {
                in.readLine();
                synchronized (ControladorServidor.getInstance().getTurnosEnEspera().getTurnos()) {
                    msg = ControladorServidor.getInstance().getTurnosEnEspera().sacarTurno();
                }
                if (msg instanceof Turno) {
                    ControladorServidor.getInstance().setCambios(true);
                    ((Turno) msg).setBox(this.nroBox);
                    ControladorServidor.getInstance().enviarActualizacion((Turno)msg);
                    this.out.println(((Turno) msg).getDni());
                }
            } catch (IOException e1) {
                running = false;
                System.out.println("Se desconectó el box " + this.nroBox + " con IP " + this.ip);
            }
        }
    }
}
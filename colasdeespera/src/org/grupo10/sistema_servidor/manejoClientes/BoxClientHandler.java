package org.grupo10.sistema_servidor.manejoClientes;

import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.ControladorServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BoxClientHandler extends Thread  {
    private int nroBox;
    private boolean running;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;

    public BoxClientHandler(Socket socket) {
        try {
            this.ip = socket.getInetAddress().getHostAddress();
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nroBox = Integer.parseInt(this.in.readLine());
            // Chequea si el número de box ya está en uso
            this.running = true;
        } catch (IOException e) {
            System.out.println("ERROR HILO BOX: " + e.getMessage());
        }
    }
    public void run() {
        Object msg;
        while (running) {
            try {
                in.readLine();
                synchronized (ControladorServidor.getInstance().getTurnosEnEspera().sacarTurno()) {
                    msg = ControladorServidor.getInstance().getTurnosEnEspera().sacarTurno();
                }
                if (msg instanceof Turno) {
                    ControladorServidor.getInstance().setCambios(true);
                    ControladorServidor.getInstance().enviarActualizacion((Turno)msg);
                }
                out.println(msg);
            } catch (IOException e1) {
                running = false;
                System.out.println("Se desconectó el box " + this.nroBox + " con IP " + this.ip);
            }
        }
    }
}
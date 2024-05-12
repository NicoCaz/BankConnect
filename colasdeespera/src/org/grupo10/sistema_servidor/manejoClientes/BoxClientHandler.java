package org.grupo10.sistema_servidor.manejoClientes;

import org.grupo10.modelo.Turno;
import org.grupo10.modelo.TurnoFinalizado;
import org.grupo10.sistema_servidor.ControladorServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BoxClientHandler extends Thread  {
    private int nroBox;
    private boolean running;
    private Turno turnoAnterior = null;
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
                    if(msg != null){
                        if(turnoAnterior != null){
                            finalizarTurno(this.turnoAnterior);
                        }
                        this.turnoAnterior = (Turno) msg;
                    }
                    ControladorServidor.getInstance().enviarTurnoLlamado((Turno)msg);
                    this.out.println(((Turno) msg).getDni());
                }else {
                    this.out.println("Fila Vacia");
                    if(turnoAnterior != null){
                        finalizarTurno(this.turnoAnterior);
                        turnoAnterior=null;
                    }
                }
                ControladorServidor.getInstance().enviarEstadisticas();
            } catch (IOException e1) {
                running = false;
                System.out.println("Se desconectó el box " + this.nroBox + " con IP " + this.ip);
                ControladorServidor.getInstance().boxesOcupados.remove(this.nroBox);
            }
        }
    }

    void finalizarTurno(Turno t){
        ControladorServidor.getInstance().getTurnosFinalizados().agregarTurno(new TurnoFinalizado(t));
    }
}
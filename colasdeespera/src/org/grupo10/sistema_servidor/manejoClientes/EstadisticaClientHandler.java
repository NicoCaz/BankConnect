package org.grupo10.sistema_servidor.manejoClientes;

import org.grupo10.modelo.Fila;
import org.grupo10.modelo.FilaFinalizada;
import org.grupo10.modelo.TurnoFinalizado;
import org.grupo10.modelo.dto.EstadisticaDTO;
import org.grupo10.sistema_servidor.ControladorServidor;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

public class EstadisticaClientHandler extends Thread {
    private boolean running;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;

    public EstadisticaClientHandler(Socket socket) {
        try {
            this.ip = socket.getInetAddress().getHostAddress();
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.running = true;
        } catch (IOException e) {
            System.out.println("ERROR HILO ESTADISTICA: " + e.getMessage());
        }
    }

    public PrintWriter getOut(){
      return this.out;
    }
    public void run() {
        Fila turnosEnEspera;
        FilaFinalizada turnosFinalizado;
        while (running) {
            try {
                in.readLine();
                synchronized (ControladorServidor.getInstance().getTurnosEnEspera().getTurnos()  ) {
                    turnosEnEspera = ControladorServidor.getInstance().getTurnosEnEspera();
                }
                synchronized (ControladorServidor.getInstance().getTurnosFinalizados().getTurnos()){
                    turnosFinalizado=ControladorServidor.getInstance().getTurnosFinalizados();
                }
                int cantEspera=turnosEnEspera.cantidadEspera();
                int cantAtendidos=turnosFinalizado.cantidadFinalizada();
                double  tiempoPromedio = 0;

                Iterator<TurnoFinalizado> iterator= turnosFinalizado.getTurnos().iterator();
                while(iterator.hasNext()) {
                    TurnoFinalizado turno= iterator.next();
                    tiempoPromedio+=Math.abs(turno.getHorarioSalida().getTime()-turno.getT().getHorarioEntrada().getTime());
                }

                tiempoPromedio=(tiempoPromedio/cantAtendidos);
                //tiempoPromedio= TimeUnit.SECONDS.convert(tiempoPromedio, TimeUnit.MILLISECONDS) % 60;

                EstadisticaDTO res = new EstadisticaDTO(cantEspera,cantAtendidos,tiempoPromedio);

                    ControladorServidor.getInstance().setCambios(true);
                    ControladorServidor.getInstance().enviarEstadisticas((EstadisticaDTO)res);

                this.out.println(res);
            } catch (IOException e1) {
                running = false;
                System.out.println("Se desconectó el panel de estadistica con IP " + this.ip);
            }
        }
    }
}
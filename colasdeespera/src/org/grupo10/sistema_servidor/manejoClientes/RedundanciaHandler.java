package org.grupo10.sistema_servidor.manejoClientes;

import org.grupo10.modelo.Fila;
import org.grupo10.sistema_servidor.IRedundancia;
import org.grupo10.sistema_servidor.StateSocketServerPrimario;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RedundanciaHandler extends Thread implements IRedundancia {
    private StateSocketServerPrimario servidor;
    private boolean running;
    private ObjectOutputStream out;
    private String ipOtro;

    public RedundanciaHandler(String ip,ObjectOutputStream out, StateSocketServerPrimario servidor) {
        this.ipOtro = ip;
        this.out = out;
        this.servidor = servidor;
        System.out.println("Se conectó un servidor con IP " + this.ipOtro + " como backup.");
        this.servidor.setCambios(true); // Asegura resincronización de estado
        this.running = true;
    }

    @Override
    public void run() {
        long maxTime = 5000; // Heartbeat de 5 segundos.
        long before = System.currentTimeMillis();
        // MEJORABLE???
        while (running) {
            try {
                if (this.servidor.getCambios()) {
                    // Envía la fila actualizada
                    this.enviarEstado();
                    before = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - before >= maxTime) {
                    // Implementación de Heartbeat
                    this.enviarPulso();
                    before = System.currentTimeMillis();
                }
            } catch (IOException e1) {
                running = false;
                System.out.println("Se desconectó el servidor de backup con IP " + this.ipOtro);
            }
        }
    }

    @Override
    public void enviarPulso() throws IOException{
        this.out.writeObject("PULSO");
    }

    @Override
    public void enviarEstado() throws IOException{
        Object clon = ((Fila) this.servidor.getTurnosEnEspera().clone());
        this.out.writeObject((Fila) clon);
        this.servidor.setCambios(false);
    }
}
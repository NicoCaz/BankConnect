package org.grupo10.sistema_servidor.manejoClientes;

import org.grupo10.modelo.Fila;
import org.grupo10.modelo.FilaFinalizada;
import org.grupo10.modelo.dto.FilasDTO;
import org.grupo10.sistema_servidor.ControladorServidor;
import org.grupo10.sistema_servidor.IRedundancia;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RedundanciaHandler extends Thread implements IRedundancia {
    private boolean running;
    private ObjectOutputStream out;
    private String ipOtro;

    public RedundanciaHandler(Socket socket) {
        try {
            this.ipOtro = socket.getInetAddress().getHostAddress();
            this.out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Se conectó un servidor con IP " + this.ipOtro + " como backup.");
            ControladorServidor.getInstance().setCambios(true); // Asegura resincronización de estado
            this.running = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long maxTime = 5000; // Heartbeat de 5 segundos.
        long before = System.currentTimeMillis();
        while (running) {
            try {
                if (ControladorServidor.getInstance().hayCambios()) {
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
        Fila clonFila = (Fila) ControladorServidor.getInstance().getTurnosEnEspera().clone();
        FilaFinalizada clonFilaFinalizada =(FilaFinalizada) ControladorServidor.getInstance().getTurnosFinalizados().clone();
        FilasDTO filasDTO= new FilasDTO(clonFila,clonFilaFinalizada);
        this.out.writeObject((FilasDTO) filasDTO);
        ControladorServidor.getInstance().setCambios(false);
    }
}
package org.grupo10.sistema_estadistica.conexion;

import org.grupo10.exception.EstadisticaException;
import org.grupo10.modelo.dto.EstadisticaDTO;
import org.grupo10.sistema_estadistica.controlador.ControladorEstadistica;
import org.grupo10.sistema_estadistica.controlador.IEstadisticas;

import java.io.*;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class SistemaEstadistica implements IEstadisticas {
    private Socket socket;
    private PrintWriter out;
    private ObjectInputStream  in;
    private ArrayList<Map.Entry<String, Integer>> servers = new ArrayList<>();
    private int serverActivo, nroEstadisticas;


    public SistemaEstadistica(String ip1, int port1, String ip2, int port2) throws IOException, EstadisticaException {
        this.nroEstadisticas = nroEstadisticas;
        servers.add(new AbstractMap.SimpleEntry<>(ip1, port1));
        servers.add(new AbstractMap.SimpleEntry<>(ip2, port2));

        // Conexión a servidor
        this.serverActivo = 0;
        try {
            ControladorEstadistica.getInstance().abrirMensajeConectando();
            this.conectar(servers.get(this.serverActivo));
            ControladorEstadistica.getInstance().cerrarMensajeConectando();
        } catch (IOException e) {
            this.reconectar();
        }
    }

    @Override
    public EstadisticaDTO resiboEstadistica() throws IOException, ClassNotFoundException, EstadisticaException {
        this.out.println("Estadistica");
        try {
            return (EstadisticaDTO) this.in.readObject(); // Recibe DNI del servidor
        } catch (IOException e) { // Hubo una falla. Reintenta / cambia de servidor.
            this.reconectar();
            this.out.println("SIGUIENTE");
            return (EstadisticaDTO) this.in.readObject();
        }
    }

    private void conectar(Map.Entry<String, Integer> entry) throws IOException, EstadisticaException {
        this.socket = new Socket(entry.getKey(), entry.getValue());
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in =  new ObjectInputStream(socket.getInputStream());
        this.out.println("Estadistica");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Chequea si el número de box ya está en uso
        this.out.println(this.nroEstadisticas);
        String msg = this.in.readLine();
        if (msg.equals("OCUPADO"))
            throw new EstadisticaException();
    }

    // Maneja el reintento y el cambio de servidor
    public void reconectar() throws IOException, EstadisticaException{
        ControladorEstadistica.getInstance().abrirMensajeConectando();
        try {
            // RETRY: Intenta conectar al actual
            this.conectar(servers.get(this.serverActivo));
        } catch (IOException e1) {
            // Cambia de serverActivo
            this.serverActivo = 1 - this.serverActivo;
            try {
                // Intenta conectar al otro server
                this.conectar(servers.get(this.serverActivo));
            } catch (IOException e2) {
                // RETRY: Intenta conectar al otro server
                this.conectar(servers.get(this.serverActivo));
            }
        }
        ControladorEstadistica.getInstance().cerrarMensajeConectando();
    }
}
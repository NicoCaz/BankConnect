package org.grupo10.sistema_box.conexion;

import org.grupo10.exception.BoxException;
import org.grupo10.sistema_box.controlador.ControladorBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class SistemaBox implements I_Box{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<Map.Entry<String, Integer>> servers = new ArrayList<>();
    private int serverActivo, nroBox;

    public SistemaBox(int nroBox, String ip1, int port1, String ip2, int port2) throws IOException , BoxException {
        this.nroBox = nroBox;
        servers.add(new AbstractMap.SimpleEntry<>(ip1, port1));
        servers.add(new AbstractMap.SimpleEntry<>(ip2, port2));

        // Conexión a servidor
        this.serverActivo = 0;
        try {
            ControladorBox.getInstance().abrirMensajeConectando();
            this.conectar(servers.get(this.serverActivo));
            System.out.println("CERRAR MENSAJE CONECTADO");
            ControladorBox.getInstance().cerrarMensajeConectando();

        } catch (IOException e) {
            this.reconectar();
        }
    }

    public String llamarSiguente() throws IOException, BoxException  {
        this.out.println("SIGUIENTE");
        try {
            return this.in.readLine(); // Recibe DNI del servidor
        } catch (IOException e) { // Hubo una falla. Reintenta / cambia de servidor.
            this.reconectar();
            this.out.println("SIGUIENTE");
            return this.in.readLine();
        }
    }

    public void conectar(Map.Entry<String, Integer> entry) throws IOException, BoxException {
        this.socket = new Socket(entry.getKey(), entry.getValue());
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out.println("Box");

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Chequea si el número de box ya está en uso
        this.out.println(this.nroBox);
        String msg = this.in.readLine();

        if (msg.equals("OCUPADO"))
            throw new BoxException();
    }

    // Maneja el reintento y el cambio de servidor
    public void reconectar() throws IOException , BoxException {
        ControladorBox.getInstance().abrirMensajeConectando();
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
        ControladorBox.getInstance().cerrarMensajeConectando();
    }
}
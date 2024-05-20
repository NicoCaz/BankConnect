package org.grupo10.sistema_totem.conexion;

import org.grupo10.interfaces.Conexion;
import org.grupo10.sistema_totem.controlador.ControladorTotem;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class SistemaTotem extends Conexion implements I_DNI {

    public SistemaTotem() throws IOException, FileNotFoundException {

        super("/totemconfig.txt");
    }

    @Override
    public String enviarDNIRegistro(String dni) throws IOException {
        this.out.println(dni); // Envía DNI al servidor
        try {
            return (String) this.in.readLine(); // Recibe confirmación del servidor
        } catch (IOException e) { // Hubo una falla. Reintenta / cambia de servidor.
            this.reconectar();
            this.out.println(dni);
            return (String) this.in.readLine(); // Recibe confirmación del servidor
        }
    }

    public void conectar(Map.Entry<String, Integer> entry) throws IOException {
        this.socket = new Socket(entry.getKey(), entry.getValue());
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.out.println("Totem");

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void abrirMensajeConectando() {
        ControladorTotem.getInstance().abrirMensajeConectando();
    }

    @Override
    protected void cerrarMensajeConectando() {
        ControladorTotem.getInstance().cerrarMensajeConectando();
    }

}
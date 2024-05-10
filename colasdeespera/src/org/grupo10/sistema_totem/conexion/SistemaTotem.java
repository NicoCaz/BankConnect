package org.grupo10.sistema_totem.conexion;

import org.grupo10.sistema_totem.I_DNI;
import org.grupo10.sistema_totem.controlador.ControladorTotem;

import java.io.*;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class SistemaTotem implements I_DNI {
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<Map.Entry<String, Integer>> servers = new ArrayList<>();
    private int serverActivo;


    public SistemaTotem() throws IOException, FileNotFoundException {
        String ip;
        int port;

        String currentDir = System.getProperty("user.dir");

        String archivoTxt = currentDir + "/colasdeespera/src/org/grupo10/sistema_totem/totemconfig.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoTxt))) {
            String linea;

            //Leo el Servidor Principal
            linea = br.readLine();
            String[] partes = linea.split(":");
            ip = partes[0];
            port = Integer.parseInt(partes[1]);
            servers.add(new AbstractMap.SimpleEntry<>(ip, port));
            //Leo el Servidor de Respaldo
            linea = br.readLine();
            partes = linea.split(":");
            String ipOtro = partes[0];
            int portOtro = Integer.parseInt(partes[1]);
            servers.add(new AbstractMap.SimpleEntry<>(ipOtro, portOtro));

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        // Conexión a servidor
        this.serverActivo = 0;
        try {
            ControladorTotem.getInstance().abrirMensajeConectando();
            this.conectar(servers.get(this.serverActivo));
            ControladorTotem.getInstance().cerrarMensajeConectando();
        } catch (IOException e) {
            this.reconectar();
        }
    }

    @Override
    public String enviarDNIRegistro(String dni) throws IOException {
        this.out.println(dni); // Envía DNI al servidor
        try {
            return this.in.readLine(); // Recibe confirmación del servidor
        } catch (IOException e) { // Hubo una falla. Reintenta / cambia de servidor.
            this.reconectar();
            this.out.println(dni);
            return this.in.readLine();
        }
    }

    private void conectar(Map.Entry<String, Integer> entry) throws IOException {
        Socket socket = new Socket(entry.getKey(), entry.getValue());
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out.println("Totem");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Maneja el reintento y el cambio de servidor
    public void reconectar() throws IOException {
        ControladorTotem.getInstance().abrirMensajeConectando();
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
        ControladorTotem.getInstance().cerrarMensajeConectando();
    }
}
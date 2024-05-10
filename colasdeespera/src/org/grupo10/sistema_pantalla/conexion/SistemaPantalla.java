package org.grupo10.sistema_pantalla.conexion;

import org.grupo10.modelo.Turno;
import org.grupo10.sistema_pantalla.controlador.ControladorPantalla;
import org.grupo10.sistema_pantalla.controlador.IPantalla;

import java.io.*;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class SistemaPantalla {
    private IPantalla pantalla;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ArrayList<Map.Entry<String, Integer>> servers = new ArrayList<>();
    private int serverActivo;

    public SistemaPantalla(IPantalla pantalla) throws IOException, FileNotFoundException {
        String ip;
        int port;
        this.pantalla = pantalla;

        String currentDir = System.getProperty("user.dir");

        String archivoTxt = currentDir + "/colasdeespera/src/org/grupo10/sistema_pantalla/pantallaconfig.txt";

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
            ControladorPantalla.getInstance().abrirMensajeConectando();
            this.conectar(servers.get(this.serverActivo));
            ControladorPantalla.getInstance().cerrarMensajeConectando();
        } catch (IOException e) {
            this.reconectar();
        }

        this.esperarActualizaciones();
    }

    // Espera novedades del servidor
    public void esperarActualizaciones() throws IOException {
        while (true) {
            Turno turno;
            try {
                turno = (Turno) in.readObject(); // Recibe DNI del servidor
                int nroBox = turno.getBox();
                this.pantalla.agregarLlamado(nroBox, turno.getDni());
            } catch (IOException | ClassNotFoundException e) { // Hubo una falla. Reintenta / cambia de servidor.
                this.reconectar();
            }
        }
    }

    private void conectar(Map.Entry<String, Integer> entry) throws IOException {
        this.socket = new Socket(entry.getKey(), entry.getValue());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out.writeObject("Pantalla");
    }

    // Maneja el reintento y el pantalla de servidor
    public void reconectar() throws IOException {
        ControladorPantalla.getInstance().abrirMensajeConectando();
        try {
            //RETRY: Intenta conectar al actual
            this.conectar(servers.get(this.serverActivo));
        } catch (IOException e) {
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
        ControladorPantalla.getInstance().cerrarMensajeConectando();
    }
}
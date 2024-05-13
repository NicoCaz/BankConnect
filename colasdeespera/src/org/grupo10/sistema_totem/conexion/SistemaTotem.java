package org.grupo10.sistema_totem.conexion;

<<<<<<< Updated upstream
=======
import org.grupo10.interfaces.Conexion;
>>>>>>> Stashed changes
import org.grupo10.sistema_totem.controlador.ControladorTotem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class SistemaTotem extends Conexion implements I_DNI {

    public SistemaTotem() throws IOException, FileNotFoundException {
<<<<<<< Updated upstream
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
=======
        super("/totemconfig.txt");
>>>>>>> Stashed changes
    }

    @Override
    public String enviarDNIRegistro(String dni) throws IOException {
        this.out.println(dni); // Envía DNI al servidor
        try {
            return (String) this.in.readObject(); // Recibe confirmación del servidor
        } catch (IOException e) { // Hubo una falla. Reintenta / cambia de servidor.
            this.reconectar();
            this.out.println(dni);
            try {
                return (String) this.in.readObject(); // Recibe confirmación del servidor
            } catch (ClassNotFoundException e2){
                return null;
            }
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public void conectar(Map.Entry<String, Integer> entry) throws IOException {
        this.socket = new Socket(entry.getKey(), entry.getValue());
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new ObjectInputStream(socket.getInputStream());

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
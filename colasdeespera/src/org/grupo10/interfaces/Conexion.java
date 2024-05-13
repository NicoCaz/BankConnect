package org.grupo10.interfaces;

import org.grupo10.exception.BoxException;

import java.io.*;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public abstract class Conexion {
    protected Socket socket;
    protected PrintWriter out;
    protected BufferedReader in;
    protected ArrayList<Map.Entry<String, Integer>> servers;
    protected String ip, ipOtro,filename;
    protected int port, portOtro,serverActivo;

    protected Conexion(String filename) throws IOException, FileNotFoundException {
        this.filename = filename;
        this.leerArchivoCFG();

        // Guarda referencias a ambos servidores.
        this.servers = new ArrayList<>();
        this.servers.add(new AbstractMap.SimpleEntry<>(this.ip, this.port));
        this.servers.add(new AbstractMap.SimpleEntry<>(this.ipOtro, this.portOtro));

        // Conexión a servidor
        this.serverActivo = 0;
        try {
            this.abrirMensajeConectando();
            this.conectar(this.servers.get(this.serverActivo));
            this.cerrarMensajeConectando();
        } catch (IOException e) {
            this.reconectar();
        }

        // Hook
        this.hookEsperar();
    }

    protected abstract void conectar(Map.Entry<String, Integer> entry) throws IOException, BoxException;

    protected void leerArchivoCFG() throws FileNotFoundException {
        //esto sirve para cuando hacemos el codigo
        String currentDir = System.getProperty("user.dir");
        String archivoTxt = currentDir + "/colasdeespera/src/org/grupo10/sistema_totem" + this.filename;

        //esto sirve cuando hacemos los ejecutables
        //String jarPath = new File(ControladorServidor.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getPath();

        // Lectura de archivo de configuración
        try (BufferedReader br = new BufferedReader(new FileReader(archivoTxt))){
            // Hook
            this.hookPrimerasLineas(br);

            String linea;

            //Leo el Servidor Principal
            linea = br.readLine();
            String[] partes = linea.split(":");
            this.ip = partes[0];
            this.port = Integer.parseInt(partes[1]);

            //Leo el Servidor de Respaldo
            linea = br.readLine();
            partes = linea.split(":");
            this.ipOtro = partes[0];
            this.portOtro = Integer.parseInt(partes[1]);


        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Hook
    // Si la conexión específica necesita información de las primeras lineas del archivo .cfg,
    // debe sobreescribir este método.
    protected void hookPrimerasLineas(BufferedReader br) throws IOException {
    }

    protected abstract void abrirMensajeConectando();

    protected abstract void cerrarMensajeConectando();

    // Hook
    // Si la conexión específica necesita quedarse esperando un mensaje,
    // debe sobreescribir este método.
    protected void hookEsperar() throws IOException{
    }

    // Maneja el reintento y el cambio de servidor
    protected void reconectar() throws IOException {
        this.abrirMensajeConectando();
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
        this.cerrarMensajeConectando();
    }
}

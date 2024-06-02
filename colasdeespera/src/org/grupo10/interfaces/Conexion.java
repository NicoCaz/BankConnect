package org.grupo10.interfaces;

import java.io.*;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        } catch (IOException  e) {
            String input = e.getMessage();
            String regex = "El box (\\d+) esta ocupado";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                throw new IOException(e.getMessage());
            }else{
                this.reconectar();
            }

        }

        // Hook
        this.hookEsperar();
    }

    protected abstract void conectar(Map.Entry<String, Integer> entry) throws IOException;

    protected void leerArchivoCFG() throws FileNotFoundException {
        //esto sirve para cuando hacemos el codigo
        String currentDir = System.getProperty("user.dir");
        String archivoTxt = currentDir + "/colasdeespera/src/org/grupo10/" + this.filename;

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

        boolean conectado= false;
        while(!conectado){
            try{
                this.conectar(servers.get(this.serverActivo));
                conectado=true;

            }catch (IOException e) {
                if(serverActivo==1){
                    this.serverActivo=0;
                }else {
                    this.serverActivo=1;
                }
            }

        }

        if (!conectado) {
            throw new IOException("No se pudo conectar a ninguno de los servidores disponibles.");
        }
            this.cerrarMensajeConectando();

    }
}

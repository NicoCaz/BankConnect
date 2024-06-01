package org.grupo10.sistema_servidor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class ControladorServidor extends Thread {
    private ServidorState estado;
    private String tipoLog,estrategiaFila;
    // Parámetros del archivo de configuración
    private String ip,ipOtro;
    private int port, portOtro;
    public static void main(String[] args) {
        new ControladorServidor();
    }

    private ControladorServidor(){

        // Lectura de archivo de configuración
        String currentDir = System.getProperty("user.dir");

        String archivoTxt = currentDir + "/colasdeespera/src/org/grupo10/sistema_servidor/serverconfig.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoTxt))) {
            String linea;
            //leo el tipo de logs
            linea = br.readLine();
            this.tipoLog = linea;
            //leo la estrategia de filas
            linea = br.readLine();
            this.estrategiaFila = linea;
            //Leo el Servidor Principal
            linea = br.readLine();
            String[] partes = linea.split(":");
            ip = partes[0];
            port = Integer.parseInt(partes[1]);

            //Leo el Servidor de Respaldo
            linea = br.readLine();
            partes = linea.split(":");
            ipOtro = partes[0];
            portOtro = Integer.parseInt(partes[1]);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        // Trata de conectarse al otro servidor para ver si es primario o secundario
        try {
            System.out.println("Intentando conectar al servidor " + ipOtro + ":" + portOtro + ".");
            this.estado = new ServidorSecundarioState(this);
            // Encontró servidor primario
        } catch (IOException e) { // No encontró servidor primario
            System.out.println("No se encontró el servidor en " + this.ipOtro + ":" + this.portOtro + ".");
            try {
                System.out.println("Intentando conectar al servidor " + ip + ":" + port + ".");
                this.estado = new ServidorPrincipalState(this);
            }catch(IOException e1){
                System.out.println("No se pudo abrir el servidor principal: " + this.ipOtro + ":" + this.portOtro + ".");
                System.exit(404);
            }
        }

        this.start();
    }

    @Override
    public void run() {
        while (true) {
            this.estado.esperarConexiones();
        }
    }

    public void setEstado(ServidorState estadoNuevo) {
        this.estado = estadoNuevo;
    }
    public String getIpOtro() {
        return ipOtro;
    }
    public int getPort() {
        return port;
    }
    public int getPortOtro() {
        return portOtro;
    }
    public String getTipoLog(){
        return tipoLog;
    }
    public String getEstrategiaFila() {
        return estrategiaFila;
    }
}

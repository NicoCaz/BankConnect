package org.grupo10.sistema_servidor;

import java.io.*;



public class ControladorServidor extends Thread implements IControladorServidor {
    private static ControladorServidor instance = null;
    private ServidorState estado;

    // Parámetros del archivo de configuración
    private String ip,ipOtro;
    private int port, portOtro;
    //

    public static void main(String[] args) {
        new ControladorServidor();
    }
//    public static ControladorServidor getInstance() {
//        if (ControladorServidor.instance == null)
//            ControladorServidor.instance = new ControladorServidor();
//        return ControladorServidor.instance;
//    }

    private ControladorServidor(){

        // Lectura de archivo de configuración
        String currentDir = System.getProperty("user.dir");

        String archivoTxt = currentDir + "/colasdeespera/src/org/grupo10/sistema_servidor/serverconfig.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoTxt))) {
            String linea;

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

    @Override
    public void setEstado(ServidorState estadoNuevo) {
        this.estado = estadoNuevo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpOtro() {
        return ipOtro;
    }

    public void setIpOtro(String ipOtro) {
        this.ipOtro = ipOtro;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPortOtro() {
        return portOtro;
    }

    public void setPortOtro(int portOtro) {
        this.portOtro = portOtro;
    }
}

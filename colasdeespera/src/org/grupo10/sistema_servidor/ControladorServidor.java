package org.grupo10.sistema_servidor;

import org.grupo10.vista.IVista;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ControladorServidor{
    private IVista vista;
    private IStateServidor estado;

    // Parámetros del archivo de configuración
    private String ip,ipOtro;
    private int port, portOtro;

    public ControladorServidor(){
        this.leerArchivo();
        //this.vista = new VistaServidor((ActionListener) this);
        // Trata de conectarse al otro servidor para ver si es primario o secundario
        try {
            System.out.println("Intentando conectar al servidor " + ipOtro + ":" + portOtro + ".");
            this.estado = new StateSocketServerRespaldo(this);
            // Si no tira excepción, encontró servidor primario
        } catch (IOException e) { // No encontró servidor primario
            System.out.println("No se encontró el servidor en " + this.ipOtro + ":" + this.portOtro + ".");
            try {
                this.estado = new StateSocketServerPrimario(this);
            } catch (IOException e1) {
                System.out.println("No se pudo abrir el servidor primario en el puerto " + port + ".");
                System.exit(0);
            }
        }
        this.empezar();

    }

    public void leerArchivo() {
        String currentDir = System.getProperty("user.dir");
        String archivoTxt = currentDir + "\\colasdeespera\\src\\org\\grupo10\\sistema_servidor\\serverconfig.txt";

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
    }
    public void empezar() {
        while (true) {
            this.estado.esperar();
        }
    }


    public void setEstado(IStateServidor estado) {
        this.estado = estado;
    }

    public int getPort() {
        return this.port;
    }

    public String getIpOtro() {
        return this.ipOtro;
    }

    public int getPortOtro() {
        return this.portOtro;
    }


}

package org.grupo10.sistema_servidor;

import org.grupo10.vista.IVista;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class ControladorServidor{
    private IVista vista;

    private IStateServidor estado;


    // Parámetros del archivo de configuración
    private String ipOtro;
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
        try {
            StringTokenizer tokens;
            FileReader fr = new FileReader("configServidor.cfg");
            BufferedReader br = new BufferedReader(fr);
            this.port = Integer.parseInt(br.readLine());
            tokens = new StringTokenizer(br.readLine(), ":");
            this.ipOtro = tokens.nextToken();
            this.portOtro = Integer.parseInt(tokens.nextToken());
            fr.close();
        } catch (IOException e) {
            System.out.println("Hubo un error al leer el archivo de configuración.");
            System.exit(0);
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

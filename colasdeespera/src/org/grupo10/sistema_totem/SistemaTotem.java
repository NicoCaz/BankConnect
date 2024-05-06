package org.grupo10.sistema_totem;

import org.grupo10.modelo.ServerConfig;
import org.grupo10.modelo.Turno;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SistemaTotem implements I_DNI{
    private static final String tipo = "Totem";
    private static  ObjectOutputStream outputStream;
    private static  ObjectInputStream inputStream;
    private ArrayList<ServerConfig> servers = new ArrayList<>(); //aca van a estar las ip con los puertos de los servidores del .txt
    private Socket socket;
    private ServerConfig serverActivo;

    public SistemaTotem(){

        this.configuradorTXT();

        try {
            socket = new Socket(serverActivo.getIp(), serverActivo.getPort());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //configura los servidores leidos en totemconfig.txt
    //El server primario esta configurado en el 0
    private void configuradorTXT(){
        String currentDir = System.getProperty("user.dir");
        String archivoTxt = currentDir + "\\colasdeespera\\src\\org\\grupo10\\sistema_totem\\totemconfig.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoTxt))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(":")) {
                    String[] partes = linea.split(":");
                    String ip = partes[0];
                    int puerto = Integer.parseInt(partes[1]);
                    this.servers.add(new ServerConfig(ip,puerto));
                    this.serverActivo = this.servers.get(0);//El server primario esta configurado en el 0
                } else {
                    System.out.println("La línea '" + linea + "' no contiene el separador ':'");
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }


    public void ejecucion() throws IOException {
        try {
            conectar();
            while (true) {
                esperandoRespuestaServer();
                Object response = inputStream.readObject();
                System.out.println("Respuesta del servidor: " + response);
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            reconectar();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void esperandoRespuestaServer(){
        try {
            outputStream.writeObject("Totem a la espera");
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Turno crearTurno(String dni) throws Exception {
        Turno t;
        if (dni.length() < 6) {
            throw new Exception("ERROR: Ingrese un DNI Valido");
        } else {
            t = new Turno(dni);
        }
        enviarDNI(t);
        return t;
    }

    public void enviarDNI(Object turno){
        try {
            outputStream.writeObject(turno);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void conectar() throws IOException {
        System.out.println(tipo);
        outputStream.writeObject(tipo);
        outputStream.flush();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Maneja el reintento y el cambio de servidor
    @Override
    public void reconectar() throws IOException {

        try {
            // RETRY: Intenta conectar al actual
            this.conectar();
        } catch (IOException e1) {
            // Cambia de serverActivo
            this.serverActivo = this.servers.get(1);
            try {
                // Intenta conectar al otro server
                this.conectar();
            } catch (IOException e2) {
                // RETRY: Intenta conectar al server principal otra vez
                this.serverActivo = this.servers.get(0);
                this.conectar();
            }
        }

    }
}
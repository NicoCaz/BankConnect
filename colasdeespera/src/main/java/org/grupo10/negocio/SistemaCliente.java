package org.grupo10.negocio;

import org.grupo10.modelo.IClienteServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SistemaCliente {
    private static SistemaCliente instancia;
    private Socket socket;
    private ObjectOutputStream flujoSalida;
    private ObjectInputStream flujoEntrada;
    private ArrayList<IClienteServer> pendientes = new ArrayList<IClienteServer>();

    public static SistemaCliente getInstancia() {
        if (instancia == null)
            instancia = new SistemaCliente();
        return instancia;
    }


    public void addCliente(IClienteServer cliente) {
        this.pendientes.add(cliente);
    }

    public boolean validarCadenaNumerica(String cadena) {
        // Patrón para verificar si la cadena es una secuencia de 8 números
        Pattern patron = Pattern.compile("^\\d{8}$");
        Matcher matcher = patron.matcher(cadena);
        return matcher.matches(); //true si cadena son 8 números
    }

/*
    public void conectar(String host, int puerto) throws Exception{
        try {
            this.socket = new Socket(host, puerto);
            System.out.println("Cliente conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void enviarDatos(Turno t) throws Exception{
        try {
            System.out.println("Enviando datos al servidor");
            this.flujoSalida.writeObject(t);
            System.out.println("Datos enviados al servidor");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
*/
}

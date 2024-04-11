package org.grupo10.controlador;

import org.grupo10.modelo.IClienteServer;
import org.grupo10.modelo.Turno;
import org.grupo10.negocio.SistemaPantalla;
import org.grupo10.vista.IVista;
import org.grupo10.vista.VistaTotem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ControladorTotem implements ActionListener, IClienteServer {
    private IVista vista;
    private SistemaPantalla negocioTurno;

    //Para la conexion con el server
    private Socket socket;
    private ObjectOutputStream flujoSalida;
    private ObjectInputStream flujoEntrada;

    public ControladorTotem(){
        this.vista = new VistaTotem(this);
        this.vista.mostrar();

        negocioTurno = new SistemaPantalla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if(comando.equalsIgnoreCase("Aceptar")) {
            String dni = this.vista.getDisplayLabel().getText();

            try{
                Turno t = negocioTurno.crearTurno(dni);

                //Conexion al server para mandar el turno a la cola
                this.conectar("localhost", 1);
                this.enviarDatos(t);


                this.vista.ventanaConfirmacion("Su turno es: " + t.getNumeroTurno());
            } catch (Exception ex) {
                this.vista.ventanaError(ex.getMessage());
            }
        }

    }


/*    @Override
    public void conectarServer() {

    }*/

    @Override
    public void conectar(String host, int puerto) {
        try {
            this.socket = new Socket(host, puerto);
            System.out.println("Cliente conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void enviarDatos(Object o) {
        try {
            System.out.println("Enviando datos al servidor");
            this.flujoSalida.writeObject(o);
            System.out.println("Datos enviados al servidor");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

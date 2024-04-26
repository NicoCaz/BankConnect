package org.grupo10.sistema_servidor;

import org.grupo10.vista.IVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControladorServidor implements ActionListener{
    private IVista vista;
    private SocketServer servidor = new SocketServer();

    public ControladorServidor(){
        this.vista = new VistaServidor(this);
        this.vista.mostrar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String accion = e.getActionCommand();

        if(accion.equalsIgnoreCase("Apagado")){
            //Servidor.getInstancia().iniciarServidor();
            this.vista.actualizar();
            servidor.iniciarServer();
        }

    }
}

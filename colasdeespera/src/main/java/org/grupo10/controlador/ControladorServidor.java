package org.grupo10.controlador;

import org.grupo10.vista.IVista;
import org.grupo10.vista.VistaServidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControladorServidor implements ActionListener{
    private IVista vista;

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
        }else if(accion.equalsIgnoreCase("Encendido")){
          //  Servidor.getInstancia().detenerServidor();
            this.vista.actualizar();
        }

    }
}

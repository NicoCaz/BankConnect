package org.grupo10.sistema_box.vista;

import org.grupo10.sistema_servidor.ControladorServidor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EventosPantalla implements WindowListener {
    private int box;
    public EventosPantalla(int box) {
        this.box= box;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        //ControladorServidor servidor= ControladorServidor.getInstance();
        //servidor.quitarBox(this.box);
        System.out.println("La ventana se está cerrando...");
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

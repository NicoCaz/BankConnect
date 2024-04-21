package org.grupo10.controlador;

import org.grupo10.modelo.Turno;
import org.grupo10.negocio.SistemaPantalla;
import org.grupo10.vista.VistaPantalla;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ControladorPantalla implements ActionListener, Observer {
    private final VistaPantalla vista;
    private SistemaPantalla sistemaPantalla;

    public ControladorPantalla(){
        //Vista
        vista = new VistaPantalla(this);
        vista.mostrar();
        //Hilo / Negocio
        sistemaPantalla = new SistemaPantalla();
        sistemaPantalla.addObserver(this);
        sistemaPantalla.ejecucion();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {
        Turno t = (Turno) arg;
        System.out.println("LLEGO AL UPDATE: " + t.getDni());
        vista.agregarDatos(t.getDni(), String.valueOf(t.getBox()));
    }


}

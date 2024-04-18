package org.grupo10.controlador;

import org.grupo10.negocio.SistemaBox;
import org.grupo10.negocio.SistemaPantalla;
import org.grupo10.vista.IVista;
import org.grupo10.vista.VistaPantalla;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
        String[] turno = (String[]) arg;
        System.out.println("LLEGO AL UPDATE: " + turno[0]);
        vista.agregarDatos(turno[0],turno[1]);
    }


}

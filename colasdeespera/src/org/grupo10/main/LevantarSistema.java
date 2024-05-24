package org.grupo10.main;

import org.grupo10.sistema_box.controlador.ControladorBox;
import org.grupo10.sistema_estadistica.controlador.ControladorEstadistica;
import org.grupo10.sistema_pantalla.controlador.ControladorPantalla;
import org.grupo10.sistema_totem.controlador.ControladorTotem;

public class LevantarSistema {
    public static void main(String[] args){
        ControladorTotem.getInstance().comenzar();
        ControladorBox.getInstance().comenzar();
        ControladorPantalla.getInstance().comenzar();
        ControladorEstadistica.getInstance().comenzar();
    }
}

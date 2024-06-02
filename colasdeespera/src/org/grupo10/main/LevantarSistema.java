package org.grupo10.main;

import org.grupo10.sistema_box.controlador.ControladorBox;
import org.grupo10.sistema_estadistica.controlador.ControladorEstadistica;
import org.grupo10.sistema_pantalla.controlador.ControladorPantalla;
import org.grupo10.sistema_totem.controlador.ControladorTotem;

public class LevantarSistema {
    public static void main(String[] args) throws InterruptedException {
        ControladorTotem.getInstance().comenzar();
        Thread.sleep(100);
        ControladorBox.getInstance().comenzar();
        Thread.sleep(100);
        ControladorPantalla.getInstance().comenzar();
        Thread.sleep(100);
        ControladorEstadistica.getInstance().comenzar();
    }
}

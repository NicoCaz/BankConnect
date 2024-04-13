package org.grupo10.main;

import org.grupo10.negocio.SistemaEstadistica;
import org.grupo10.vista.VistaEstadisticas;

import javax.swing.*;

public class MainEstadisticas {
    public static void main(String[] args) {
        SistemaEstadistica estadistica=new SistemaEstadistica();
        estadistica.ejecucion();
    }
}

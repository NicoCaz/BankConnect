package org.grupo10.main;

import org.grupo10.vista.VistaEstadisticas;

import javax.swing.*;

public class MainEstadisticas {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VistaEstadisticas().setVisible(true);
            }
        });
    }
}

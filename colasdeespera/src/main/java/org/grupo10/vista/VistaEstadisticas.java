package org.grupo10.vista;

import javax.swing.*;
import java.awt.*;

public class VistaEstadisticas extends JFrame {
    private JLabel personasEnEsperaLabel, personasAtendidaLabel, tiempoPromedioLabel;
    private JButton refreshButton;
    private int personasEnEspera = 20;
    private int personasAtendidas = 70;
    private int tiempoPromedio = 15 * 60 + 32; // 15 minutos y 32 segundos

    public VistaEstadisticas() {
        setTitle("Estadisticas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        // Crear los componentes de la interfaz
        personasEnEsperaLabel = new JLabel("Personas en espera: " + personasEnEspera);
        personasAtendidaLabel = new JLabel("Personas atendidas: " + personasAtendidas);
        tiempoPromedioLabel = new JLabel("Tiempo promedio: " + formatTime(tiempoPromedio));
        refreshButton = new JButton("Refrescar");

        // Establecer el tamaño de fuente responsivo
        int fontSize = (int) (getHeight() * 0.08);
        Font font = new Font("Arial", Font.PLAIN, fontSize);
        personasEnEsperaLabel.setFont(font);
        personasAtendidaLabel.setFont(font);
        tiempoPromedioLabel.setFont(font);
        refreshButton.setFont(font);

        // Agregar los componentes al panel principal
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(personasEnEsperaLabel);
        panel.add(personasAtendidaLabel);
        panel.add(tiempoPromedioLabel);
        panel.add(refreshButton);
        add(panel, BorderLayout.CENTER);
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }


}

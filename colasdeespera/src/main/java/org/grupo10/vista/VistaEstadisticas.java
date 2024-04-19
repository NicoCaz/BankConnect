package org.grupo10.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaEstadisticas extends JFrame implements IVista  {
    private JLabel personasEnEsperaLabel, personasAtendidaLabel, tiempoPromedioLabel;
    private JButton refreshButton;
    private ActionListener controlador;
    private int personasEnEspera = 0;
    private int personasAtendidas = 0;
    private int tiempoPromedio = 0; // 15 minutos y 32 segundos

    public VistaEstadisticas(ActionListener controlador) {
        setTitle("Estadisticas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        this.controlador = controlador;

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


    @Override
    public void cerrar() {

    }

    @Override
    public void mostrar() {

    }

    @Override
    public void actualizar() {

    }

    @Override
    public void ventanaConfirmacion(String msg) {

    }

    @Override
    public void ventanaError(String msg) {

    }

    @Override
    public void setActionListener(ActionListener actionListener) {

    }

    @Override
    public JLabel getDisplayLabel() {
        return null;
    }

    @Override
    public StringBuilder getInputBuffer() {
        return null;
    }

    @Override
    public void apagarLlamar() {

    }

    @Override
    public void prenderLlamar() {

    }
}

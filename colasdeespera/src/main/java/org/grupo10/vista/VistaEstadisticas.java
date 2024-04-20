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
        refreshButton.addActionListener(controlador);

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

    public String formatTime(double milisegundos) {

        int segundos = (int) (milisegundos / 1000);
        int minutos = segundos / 60;
        int segundosRestantes = segundos - minutos*60;
        return String.format("%02d:%02d", minutos , segundosRestantes);
        //return String.valueOf(segundos);
    }

    public JLabel getPersonasEnEsperaLabel() {
        return personasEnEsperaLabel;
    }

    public JLabel getPersonasAtendidaLabel() {
        return personasAtendidaLabel;
    }

    public JLabel getTiempoPromedioLabel() {
        return tiempoPromedioLabel;
    }

    @Override
    public void cerrar() {

    }

    @Override
    public void mostrar() {
        setVisible(true);
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void ventanaConfirmacion(String msg) {

    }

    @Override
    public void ventanaError(String msg) {

        JDialog errorDialog = new JDialog(VistaEstadisticas.this, msg, true);
        JPanel errorPanel = new JPanel(new BorderLayout());
        JLabel errorLabel = new JLabel(msg, SwingConstants.CENTER);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        errorPanel.add(errorLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(event -> errorDialog.dispose());
        errorPanel.add(okButton, BorderLayout.SOUTH);

        errorDialog.setContentPane(errorPanel);
        errorDialog.pack();
        errorDialog.setLocationRelativeTo(VistaEstadisticas.this);
        errorDialog.setVisible(true);
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

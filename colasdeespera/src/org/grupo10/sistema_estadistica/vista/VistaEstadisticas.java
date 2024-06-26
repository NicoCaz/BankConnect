package org.grupo10.sistema_estadistica.vista;

import org.grupo10.sistema_estadistica.controlador.ControladorEstadistica;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class VistaEstadisticas extends JFrame {
    private final JOptionPane optionPaneConectando;
    private final JDialog dialogoConectando;
    private JLabel personasEnEsperaLabel, personasAtendidaLabel, tiempoPromedioLabel;
    private ControladorEstadistica controlador;
    private int personasEnEspera = 0;
    private int personasAtendidas = 0;
    private int tiempoPromedio = 0; // 15 minutos y 32 segundos

    public VistaEstadisticas() {
        this.controlador = ControladorEstadistica.getInstance();
        setTitle("Estadisticas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);


        // Crear los componentes de la interfaz
        personasEnEsperaLabel = new JLabel("Personas en espera: " + personasEnEspera);
        personasAtendidaLabel = new JLabel("Personas atendidas: " + personasAtendidas);
        tiempoPromedioLabel = new JLabel("Tiempo promedio: " + formatTime(tiempoPromedio));

        // Establecer el tamaño de fuente responsivo
        int fontSize = (int) (getHeight() * 0.08);
        Font font = new Font("Arial", Font.PLAIN, fontSize);
        personasEnEsperaLabel.setFont(font);
        personasAtendidaLabel.setFont(font);
        tiempoPromedioLabel.setFont(font);

        // Agregar los componentes al panel principal
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(personasEnEsperaLabel);
        panel.add(personasAtendidaLabel);
        panel.add(tiempoPromedioLabel);
        add(panel, BorderLayout.CENTER);
        this.optionPaneConectando = new JOptionPane("Conectando...\nPresione Cancelar para cerrar el programa.",
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.CANCEL_OPTION);
        this.dialogoConectando = new JDialog(this, "Mensaje", true);
        Object[] options = {"Cancelar"};
        this.optionPaneConectando.setOptions(options);
        this.dialogoConectando.setContentPane(this.optionPaneConectando);
        this.dialogoConectando.setResizable(false);
        this.dialogoConectando.setModal(false);
        this.dialogoConectando.pack();
        this.dialogoConectando.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);


        setVisible(true);
    }

    public void abrirMensajeConectando() {
        this.setEnabled(false);
        new Thread(() -> { // En otro thread para no bloquear la ejecución
            this.dialogoConectando.setLocationRelativeTo(this);
            this.dialogoConectando.setVisible(true);
            while (!this.optionPaneConectando.getValue().toString().equals("Cancelar") && this.dialogoConectando.isVisible()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (this.dialogoConectando.isVisible()) {
                System.exit(0);
            }
        }).start();
    }

    public void cerrarMensajeConectando() {
        try {
            Thread.sleep(50); // Para asegurar que el mensaje se cierra aún si la conexión es demasiado rápida
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.dialogoConectando.setVisible(false);
        this.toFront();
        this.setEnabled(true);
    }

    public String formatTime(double milisegundos) {

        int segundos = (int) (milisegundos / 1000);
        int minutos = segundos / 60;
        int segundosRestantes = segundos - minutos*60;
        return String.format("%02d:%02d", minutos , segundosRestantes);
        //return String.valueOf(segundos);
    }

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

    public void agregaDatos(String[] datos){
        DateFormat dateFormat = new SimpleDateFormat("mm:ss");
        Date tiempo = new Date( Long.parseLong(datos[2]) );
        this.tiempoPromedioLabel.setText("Tiempo promedio: "+ dateFormat.format(tiempo));
        this.personasEnEsperaLabel.setText("Personas en espera: "+datos[0]);
        this.personasAtendidaLabel.setText("Personas atendidas: "+datos[1]);

    }
}

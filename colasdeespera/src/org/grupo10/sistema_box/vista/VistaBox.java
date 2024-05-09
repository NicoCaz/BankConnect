package org.grupo10.sistema_box.vista;

import org.grupo10.sistema_box.controlador.ControladorBox;

import javax.swing.*;
import java.awt.*;

public class VistaBox extends JFrame {

    private final JOptionPane optionPaneConectando;
    private final JDialog dialogoConectando;
    private JLabel numeroAtendidoLabel, personasEnEsperaLabel;
    private JButton llamarSiguienteButton, finalizarTurnoButton;
    private int numeroAtendido = 0;
    private ControladorBox controlador=null;

    public VistaBox(int numBox) {
        this.controlador = ControladorBox.getInstance();
        setTitle("Box: " + numBox);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);


        // Crear los componentes de la interfaz
        numeroAtendidoLabel = new JLabel("Numero atendido: " + numeroAtendido);
        llamarSiguienteButton = new JButton("Llamar siguiente");
        //finalizarTurnoButton = new JButton("Finalizar turno");

        // Establecer el tamaño de fuente responsivo
        int fontSize = (int) (getHeight() * 0.08);
        Font font = new Font("Arial", Font.PLAIN, fontSize);
        numeroAtendidoLabel.setFont(font);
        llamarSiguienteButton.setFont(font);
        //finalizarTurnoButton.setFont(font);

        llamarSiguienteButton.addActionListener(controlador);
        //finalizarTurnoButton.addActionListener(controlador);

        // Agregar los componentes al panel principal
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(numeroAtendidoLabel);
        panel.add(llamarSiguienteButton);
        //panel.add(finalizarTurnoButton);

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
        //prenderLlamar();
    }

    public void abrirMensajeConectando() {
        this.setEnabled(false);
        new Thread(() -> { // En otro thread para no bloquear la ejecución
            this.dialogoConectando.setLocationRelativeTo(this);
            this.dialogoConectando.setVisible(true);
            while (!this.optionPaneConectando.getValue().toString().equals("Cancelar") && this.dialogoConectando.isVisible()) {
                try {
                    Thread.sleep(1);
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

    public void ventanaError(String msg) {

        JDialog errorDialog = new JDialog(VistaBox.this, msg, true);
        errorDialog.setTitle("ERROR");
        JPanel errorPanel = new JPanel(new BorderLayout());
        JLabel errorLabel = new JLabel(msg, SwingConstants.CENTER);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        errorPanel.add(errorLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(event -> errorDialog.dispose());
        errorPanel.add(okButton, BorderLayout.SOUTH);

        errorDialog.setContentPane(errorPanel);
        errorDialog.pack();
        errorDialog.setLocationRelativeTo(VistaBox.this);
        errorDialog.setVisible(true);

    }

    public void dniLLamado(String dni){
        this.numeroAtendidoLabel.setText("Numero atendido: " + dni);
    }

    public void apagarLlamar() {

        llamarSiguienteButton.setEnabled(false);
        finalizarTurnoButton.setEnabled(true);
    }


    public void prenderLlamar() {
        llamarSiguienteButton.setEnabled(true);
        finalizarTurnoButton.setEnabled(false);
    }


}
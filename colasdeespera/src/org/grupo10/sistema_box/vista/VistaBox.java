package org.grupo10.sistema_box.vista;

import org.grupo10.sistema_box.controlador.ControladorBox;
import org.grupo10.vista.IVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaBox extends JFrame {

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

        //prenderLlamar();
    }



    @Override
    public void ventanaError(String msg) {

        JDialog errorDialog = new JDialog(VistaBox.this, msg, true);
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
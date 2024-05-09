package org.grupo10.sistema_totem.vista;

import org.grupo10.sistema_totem.controlador.ControladorTotem;
import org.grupo10.vista.IVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaTotem extends JFrame implements IVista {
    private final JOptionPane optionPaneConectando;
    private final JDialog dialogoConectando;
    private JLabel displayLabel;
    private StringBuilder inputBuffer;

    private ControladorTotem controlador;//ControladorTotem


    public VistaTotem() {
        this.controlador = ControladorTotem.getInstance();
        setTitle("Totem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel displayPanel = new JPanel(new BorderLayout(5, 5));
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        displayPanel.setPreferredSize(new Dimension(200, 60));

        displayLabel = new JLabel("Ingrese su DNI", SwingConstants.RIGHT);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        displayLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        displayLabel.setForeground(Color.GRAY);
        displayPanel.add(displayLabel, BorderLayout.CENTER);

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBackground(Color.GREEN);
        acceptButton.setForeground(Color.WHITE);
        acceptButton.addActionListener(this.controlador);

        JButton[] numButtons = new JButton[10];
        for (int i = 0; i < 9; i++) {
            numButtons[i] = new JButton(String.valueOf(i + 1));
            numButtons[i].addActionListener(new NumericButtonListener());
            keypadPanel.add(numButtons[i]);
            setButtonFontSize(numButtons[i]);
        }
        numButtons[9] = new JButton("0");
        numButtons[9].addActionListener(new NumericButtonListener());
        keypadPanel.add(numButtons[9]);
        setButtonFontSize(numButtons[9]);

        inputBuffer = new StringBuilder();

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new CancelButtonListener());
        setButtonFontSize(cancelButton);

        JPanel sideButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        sideButtonPanel.add(acceptButton);
        sideButtonPanel.add(cancelButton);

        JButton backButton = new JButton("<--");
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new BackButtonListener());
        setButtonFontSize(backButton);

        bottomPanel.add(keypadPanel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.EAST);

        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.CENTER);
        mainPanel.add(sideButtonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Inicializa el mensaje "Conectando..."
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
    }

    @Override
    public void mostrar() {
        setVisible(true);
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        this.controlador = (ControladorTotem) actionListener;
    }

    private void setButtonFontSize(JButton button) {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, Math.min(button.getWidth(), button.getHeight()) / 2);
        button.setFont(font);
    }

    private class NumericButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            inputBuffer.append(button.getText());
            displayLabel.setText(inputBuffer.toString());
            displayLabel.setForeground(Color.BLACK);
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!inputBuffer.isEmpty()) {
                inputBuffer.deleteCharAt(inputBuffer.length() - 1);
                displayLabel.setText(inputBuffer.toString());
                if (inputBuffer.length() == 0) {
                    displayLabel.setText("Ingrese su DNI");
                    displayLabel.setForeground(Color.GRAY);
                }
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputBuffer.setLength(0);
            displayLabel.setText("Ingrese su DNI");
            displayLabel.setForeground(Color.GRAY);
        }
    }
    public void ventanaConfirmacion(String msg){
        inputBuffer.setLength(0);
        displayLabel.setText("");

        JDialog ticketDialog = new JDialog(VistaTotem.this, "Ticket", true);
        JPanel ticketPanel = new JPanel(new BorderLayout());
        JLabel ticketLabel = new JLabel(msg, SwingConstants.CENTER);
        ticketLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ticketPanel.add(ticketLabel, BorderLayout.CENTER);
        ticketDialog.setContentPane(ticketPanel);
        ticketDialog.pack();
        ticketDialog.setLocationRelativeTo(VistaTotem.this);

        // Cerrar el diálogo después de 5 segundos
        ActionListener closeListener = e -> ticketDialog.dispose();
        Timer timer = new Timer(5000, closeListener);
        timer.setRepeats(false);
        timer.start();

        ticketDialog.setVisible(true);
    }

    @Override
    public void ventanaError(String msg) {

        JDialog errorDialog = new JDialog(VistaTotem.this, msg, true);
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
        errorDialog.setLocationRelativeTo(VistaTotem.this);
        errorDialog.setVisible(true);

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

    public JLabel getDisplayLabel() {
        return displayLabel;
    }


    public StringBuilder getInputBuffer() {
        return inputBuffer;
    }



    @Override
    public void apagarLlamar() {

    }

    @Override
    public void prenderLlamar() {

    }
    @Override
    public void actualizar() {

    }
    @Override
    public void cerrar() {

    }
}
package org.grupo10.vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaTotem extends JFrame implements IVista{
    private JLabel displayLabel;
    private StringBuilder inputBuffer;

    private ActionListener controlador;//ControladorTotem

    public VistaTotem(ActionListener contr) {
        this.setActionListener(contr);
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

        displayLabel = new JLabel("", SwingConstants.RIGHT);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        displayLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        displayPanel.add(displayLabel, BorderLayout.CENTER);

        /*ESTE ES EL BOTON DE ACEPTAR TRANSACCION
          Es el unico boton que se comunica con el ControladorTotem
          Esta hecho asi para que la vista se comunique con el controlador solo en caso de completar una transaccion
         */
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBackground(Color.GREEN);
        acceptButton.setForeground(Color.WHITE);
        acceptButton.addActionListener(this.controlador);

        //Acá estan todos los otros componentes de la vista
        /*
        Cada componente tiene definido su propia clase controlador que no tiene que ver con Controlador Totem
         */

        JButton[] numButtons = new JButton[10];
        for (int i = 0; i < 9; i++) {
            numButtons[i] = new JButton(String.valueOf(i + 1));
            numButtons[i].addActionListener(new NumericButtonListener());
            keypadPanel.add(numButtons[i]);
        }
        numButtons[9] = new JButton("0");
        numButtons[9].addActionListener(new NumericButtonListener());
        keypadPanel.add(numButtons[9]);

        inputBuffer = new StringBuilder();

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new CancelButtonListener());

        JPanel sideButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        sideButtonPanel.add(acceptButton);
        sideButtonPanel.add(cancelButton);

        JButton backButton = new JButton("<--");
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new BackButtonListener() );

        bottomPanel.add(keypadPanel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.EAST);

        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.CENTER);
        mainPanel.add(sideButtonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    @Override
    public void cerrar() {

    }

    @Override
    public void mostrar() {
        setVisible(true);
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        this.controlador = actionListener;
    }

    private class NumericButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            inputBuffer.append(button.getText());
            displayLabel.setText(inputBuffer.toString());
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!inputBuffer.isEmpty()) {
                inputBuffer.deleteCharAt(inputBuffer.length() - 1);
                displayLabel.setText(inputBuffer.toString());
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Resetea el campo de texto
            inputBuffer.setLength(0);
            displayLabel.setText("");
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
        inputBuffer.setLength(0);
        displayLabel.setText("");
        JDialog errorDialog = new JDialog(VistaTotem.this, "DNI incorrecto", true);
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

    public JLabel getDisplayLabel() {
        return displayLabel;
    }


    public StringBuilder getInputBuffer() {
        return inputBuffer;
    }

/*
    private class AcceptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputBuffer.setLength(0);
            displayLabel.setText("");

            String inputValue = displayLabel.getText();
            boolean isValid = true; // Cambia esta condición según tus criterios de validación

            if (isValid) {
                JDialog ticketDialog = new JDialog(VistaTotem.this, "Ticket", true);
                JPanel ticketPanel = new JPanel(new BorderLayout());
                JLabel ticketLabel = new JLabel("Su número de ticket es:".concat(inputValue), SwingConstants.CENTER);
                ticketLabel.setFont(new Font("Arial", Font.BOLD, 16));
                ticketPanel.add(ticketLabel, BorderLayout.CENTER);

                ticketDialog.setContentPane(ticketPanel);
                ticketDialog.pack();
                ticketDialog.setLocationRelativeTo(VistaTotem.this);
                ticketDialog.setVisible(true);

                // Cerrar el diálogo después de 5 segundos
                Timer timer = new Timer(5000, event -> ticketDialog.dispose());
                timer.setRepeats(false);
                timer.start();
            } else {
                JDialog errorDialog = new JDialog(VistaTotem.this, "DNI incorrecto", true);
                JPanel errorPanel = new JPanel(new BorderLayout());
                JLabel errorLabel = new JLabel("DNI incorrecto", SwingConstants.CENTER);
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
        }
    }
*/

}
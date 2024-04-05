package org.grupo10.vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Totem extends JFrame implements IVista{
    private JLabel displayLabel;
    private StringBuilder inputBuffer;

    public Totem() {
        setTitle("Numeric Keypad");
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

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBackground(Color.GREEN);
        acceptButton.setForeground(Color.WHITE);
        acceptButton.addActionListener(new AcceptButtonListener());

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
        backButton.addActionListener(new BackButtonListener());

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

    }

    @Override
    public void setActionListener(ActionListener actionListener) {

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
            if (inputBuffer.length() > 0) {
                inputBuffer.deleteCharAt(inputBuffer.length() - 1);
                displayLabel.setText(inputBuffer.toString());
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputBuffer.setLength(0);
            displayLabel.setText("");
        }
    }

    private class AcceptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputBuffer.setLength(0);
            displayLabel.setText("");

            String inputValue = displayLabel.getText();
            boolean isValid = true; // Cambia esta condición según tus criterios de validación

            if (isValid) {
                JDialog ticketDialog = new JDialog(Totem.this, "Ticket", true);
                JPanel ticketPanel = new JPanel(new BorderLayout());
                JLabel ticketLabel = new JLabel("Su número de ticket es: 12345", SwingConstants.CENTER);
                ticketLabel.setFont(new Font("Arial", Font.BOLD, 16));
                ticketPanel.add(ticketLabel, BorderLayout.CENTER);

                ticketDialog.setContentPane(ticketPanel);
                ticketDialog.pack();
                ticketDialog.setLocationRelativeTo(Totem.this);
                ticketDialog.setVisible(true);

                // Cerrar el diálogo después de 5 segundos
                Timer timer = new Timer(5000, event -> ticketDialog.dispose());
                timer.setRepeats(false);
                timer.start();
            } else {
                JDialog errorDialog = new JDialog(Totem.this, "DNI incorrecto", true);
                JPanel errorPanel = new JPanel(new BorderLayout());
                JLabel errorLabel = new JLabel("DNI incorrecto", SwingConstants.CENTER);
                errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
                errorPanel.add(errorLabel, BorderLayout.CENTER);

                JButton okButton = new JButton("Aceptar");
                okButton.addActionListener(event -> errorDialog.dispose());
                errorPanel.add(okButton, BorderLayout.SOUTH);

                errorDialog.setContentPane(errorPanel);
                errorDialog.pack();
                errorDialog.setLocationRelativeTo(Totem.this);
                errorDialog.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Totem().setVisible(true));
    }
}
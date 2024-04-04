package org.grupo10.vistas;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TurnosLlamados extends JFrame {
    public TurnosLlamados() {
        // Datos de los turnos atendidos
        Object[][] data = {
                {"DF271", "1"},
                {"DF837", "2"},
                {"HE281", "3"},
                {"L292", "3"},
                {"AH232", "4"}
        };

        String[] columnNames = {"Turno", "BOX"};
        JTable table = new JTable(data, columnNames);

        // Resalta la fila superior
        table.setRowSelectionInterval(0, 0);
        table.setFont(new Font("Arial",Font.PLAIN, 14));

        // Agrega la tabla a un panel con scroll
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Componente para mostrar el siguiente turno
        JLabel lblSiguienteTurno = new JLabel("Siguiente turno: AH763");
        lblSiguienteTurno.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel panelSiguiente = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSiguiente.add(lblSiguienteTurno);
        getContentPane().add(panelSiguiente, BorderLayout.SOUTH);

        // Configura la ventana
        setTitle("Turnos Llamados");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false); // Evita que la ventana se redimensione
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TurnosLlamados().setVisible(true));
    }
}
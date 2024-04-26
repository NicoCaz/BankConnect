package org.grupo10.sistema_pantalla;

import org.grupo10.vista.IVista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaPantalla extends JFrame implements IVista {

    private Object[][] data;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel labelTurno;
    private JLabel labelBox;

    private ControladorPantalla controladorPantalla;

    public VistaPantalla(ControladorPantalla controladorPantalla) {
        setTitle("Monitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        // Crear el modelo de tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Box");

        // Crear la JTable y establecer el modelo
        tabla = new JTable(modeloTabla);
        tabla.setDefaultRenderer(Object.class, new CeldaResaltadaRenderer());
        JScrollPane scrollPane = new JScrollPane(tabla);

        // Crear el panel inferior
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(2, 1));

        labelTurno = new JLabel();
        labelTurno.setHorizontalAlignment(JLabel.CENTER);
        labelTurno.setFont(new Font("Arial", Font.BOLD, 24));

        labelBox = new JLabel();
        labelBox.setHorizontalAlignment(JLabel.CENTER);
        labelBox.setFont(new Font("Arial", Font.BOLD, 24));

        panelInferior.add(labelTurno);
        panelInferior.add(labelBox);

        // Agregar los componentes al frame
        add(scrollPane, "Center");
        add(panelInferior, "South");

            // Agregar datos iniciales a la tabla
//        agregarDatos("239291332","3");
//        agregarDatos("893892389","2");
        this.controladorPantalla = controladorPantalla;
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new VistaPantalla(this).setVisible(true));
    }

    public void agregarDatos(String valor1, String valor2) {
        Object[] nuevaFila = {valor1, valor2};
        modeloTabla.insertRow(0,nuevaFila);
        actualizar();
    }

    private class CeldaResaltadaRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Resaltar en negrita la primera fila (fila 0)
            if (row == 0) {
                label.setFont(label.getFont().deriveFont(Font.BOLD));
            } else {
                label.setFont(label.getFont().deriveFont(Font.PLAIN));
            }

            return label;
        }
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
        // Obtener los valores de la primera fila
        String turno = (String) modeloTabla.getValueAt(0, 0);
        String box = (String) modeloTabla.getValueAt(0, 1);

        // Actualizar los labels del panel inferior
        labelTurno.setText("Turno: " + turno);
        labelBox.setText("Box: " + box);
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

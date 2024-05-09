package org.grupo10.sistema_pantalla.vista;

import org.grupo10.sistema_pantalla.controlador.ControladorPantalla;
import org.grupo10.vista.IVista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaPantalla extends JFrame implements IVista {

    private final JOptionPane optionPaneConectando;
    private final JDialog dialogoConectando;
    private Object[][] data;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel labelTurno;
    private JLabel labelBox;

    private ControladorPantalla controladorPantalla;

    public VistaPantalla() {
        this.controladorPantalla = ControladorPantalla.getInstance();
        setTitle("Monitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        // Crear el modelo de tabla
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que las celdas no sean editables
            }
        };
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Box");
        modeloTabla.setRowCount(5); // Establecer el número máximo de filas a 5

        // Crear la JTable y establecer el modelo
        tabla = new JTable(modeloTabla);
        tabla.setDefaultRenderer(Object.class, new CeldaResaltadaRenderer());
        JScrollPane scrollPane = new JScrollPane(tabla);

        // Crear el panel inferior
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(2, 1));

        labelTurno = new JLabel();
        labelTurno.setHorizontalAlignment(JLabel.CENTER);
        labelTurno.setFont(labelTurno.getFont().deriveFont(Font.BOLD, 18f)); // Usar fuente escalable

        labelBox = new JLabel();
        labelBox.setHorizontalAlignment(JLabel.CENTER);
        labelBox.setFont(labelBox.getFont().deriveFont(Font.BOLD, 18f)); // Usar fuente escalable

        panelInferior.add(labelTurno);
        panelInferior.add(labelBox);

        // Crear un layout que se adapte al tamaño de la ventana
        setLayout(new BorderLayout());

        // Agregar los componentes al frame de manera responsiva
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Hacer que el frame se redimensione automáticamente
        pack();
        setLocationRelativeTo(null);

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

        // Agregar datos iniciales a la tabla
        //agregarDatos("239291332","3");
        //agregarDatos("893892389","2");
        this.controladorPantalla = controladorPantalla;
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new VistaPantalla(this).setVisible(true));
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
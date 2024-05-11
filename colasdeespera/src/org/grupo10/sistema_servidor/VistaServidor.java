package org.grupo10.sistema_servidor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaServidor extends JFrame {
    private JButton botonServidor;
    private boolean servidorEncendido;
    private ActionListener controlador;

    public VistaServidor(ActionListener controlador) {
        setTitle("Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);

        this.controlador = controlador;

        // Crear el botón del servidor
        botonServidor = new JButton();
        botonServidor.setPreferredSize(new Dimension(600, 200));
        botonServidor.setFont(new Font("Arial", Font.BOLD, 36));
        botonServidor.addActionListener(controlador);

        // Establecer el estado inicial del servidor como "Apagado"
        servidorEncendido = false;
        actualizarEstadoServidor();

        // Agregar el botón al contenido de la ventana
        getContentPane().add(botonServidor, BorderLayout.CENTER);

        setVisible(true);
    }

    private void toggleServidor() {
        servidorEncendido = !servidorEncendido;
        actualizarEstadoServidor();

        // Aquí puedes agregar la lógica para encender o apagar el servidor
        if (servidorEncendido) {
            System.out.println("Servidor encendido");
        } else {
            System.out.println("Servidor apagado");
        }
    }

    private void actualizarEstadoServidor() {
        if (servidorEncendido) {
            botonServidor.setText("Encendido");
            botonServidor.setBackground(new Color(0, 102, 0)); // Color verde más apagado
        } else {
            botonServidor.setText("Apagado");
            botonServidor.setBackground(new Color(153, 0, 0)); // Color rojo más oscuro
        }
    }

}
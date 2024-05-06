package org.grupo10.sistema_totem.controlador;


import org.grupo10.sistema_totem.I_DNI;
import org.grupo10.sistema_totem.conexion.SistemaTotem;
import org.grupo10.sistema_totem.vista.VistaTotem;
import org.grupo10.vista.IVista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ControladorTotem implements ActionListener {

    private static ControladorTotem instance = null;
    private IVista ventana;
    private I_DNI dni_registro;

    public static void main(String[] args) {
        ControladorTotem.getInstance().comenzar();
    }

    public static ControladorTotem getInstance() {
        if (ControladorTotem.instance == null)
            ControladorTotem.instance = new ControladorTotem();
        return ControladorTotem.instance;
    }

    private ControladorTotem() {
    }

    private void comenzar() {
        this.ventana = new VistaTotem();
        new Thread(() -> { // En otro thread para no interferir con GUIRegistros
            try {
                this.dni_registro = new SistemaTotem();
                // Activa el botón Registrar (si no hubo IOException)
                this.ventana.addActionListenerRegistrar(this);
            } catch (FileNotFoundException e) {
                this.ventana.mensajeErrorArchivo();
            } catch (IOException e) {
                this.ventana.mensajeErrorConexion();
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> { // En otro thread para no interferir con GUIRegistros
            JButton boton = (JButton) e.getSource();
            String msg, dni = this.ventana.getDNIFormateado();
            if (boton.getActionCommand() == "REGISTRAR") {
                if (dni.length() >= 9) {
                    try {
                        msg = this.dni_registro.enviarDNIRegistro(dni);
                        if (msg.equals("ACEPTADO"))
                            this.ventana.mensajeDNIRegistrado();
                        else
                            this.ventana.mensajeDNIRepetido();
                    } catch (IOException e1) {
                        this.ventana.mensajeErrorConexion();
                    }
                } else
                    this.ventana.mensajeDNIInvalido();
            }
        }).start();
    }

    public void abrirMensajeConectando() {
        this.ventana.abrirMensajeConectando();
    }

    public void cerrarMensajeConectando() {
        this.ventana.cerrarMensajeConectando();
    }
}

package org.grupo10.sistema_box.controlador;


import org.grupo10.interfaces.IControlador;
import org.grupo10.sistema_box.conexion.SistemaBox;
import org.grupo10.sistema_box.vista.VistaBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ControladorBox implements ActionListener, IControlador {
    private VistaBox ventana;
    private static ControladorBox instance = null;
    private SistemaBox dni_llamado;
    private int nroBox=0;

    public static void main(String[] args) {
        ControladorBox.getInstance().comenzar();
    }

    public static ControladorBox getInstance() {
        if (ControladorBox.instance == null)
            ControladorBox.instance = new ControladorBox();
        return ControladorBox.instance;
    }

    private ControladorBox() {

    }

    public void comenzar() {


        this.ventana = new VistaBox(0);
        new Thread(() -> { // En otro thread para no interferir con GUILlamados
            try {
                this.dni_llamado = new SistemaBox();
                
                // Activa el botón Siguiente (si no hubo IOException)
                this.ventana.prenderLlamar();
            } catch (IOException e) {
                this.ventana.ventanaError(e.getMessage());
                System.exit(1);
            }
        }).start();
    }


    public void actionPerformed(ActionEvent e) {
        new Thread(() -> { // En otro thread para no interferir con GUILlamados
            String msg;
            try {
                msg = this.dni_llamado.llamarSiguiente();
                if (msg.equals("NULL"))
                    this.ventana.ventanaError("Fila Vacia");
                else
                    this.ventana.dniLLamado(msg);
            } catch (IOException e1) {
                this.ventana.ventanaError(e1.getMessage());
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

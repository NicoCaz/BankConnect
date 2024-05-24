package org.grupo10.sistema_totem.controlador;


import org.grupo10.interfaces.IControlador;
import org.grupo10.sistema_totem.conexion.I_DNI;
import org.grupo10.sistema_totem.conexion.SistemaTotem;
import org.grupo10.sistema_totem.vista.VistaTotem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ControladorTotem implements ActionListener , IControlador {

    private static ControladorTotem instance = null;
    private VistaTotem ventana;
    private I_DNI dni_registro;

    public static void main(String[] args) {
        ControladorTotem.getInstance().comenzar();
    }

    public static ControladorTotem getInstance() {
        if (ControladorTotem.instance == null)
            ControladorTotem.instance = new ControladorTotem();
        return ControladorTotem.instance;
    }


    public void comenzar() {

        new Thread(() -> { // En otro thread para no interferir con GUIRegistros
            this.ventana = new VistaTotem();
            try {
                this.dni_registro = new SistemaTotem();
                // Activa el botón Registrar (si no hubo IOException)
            } catch (FileNotFoundException e) {
                this.ventana.ventanaError("No se ha encontrado el archivo de configuracion");
            } catch (IOException e) {
                this.ventana.ventanaError("Error de conexion");
                System.exit(404);
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> { // En otro thread para no interferir con GUIRegistros
            if (e.getActionCommand().equalsIgnoreCase("Aceptar")) {
                String dni = this.ventana.getDisplayLabel().getText();

                if (dni.length() >= 6) {
                    try {
                        String msg = this.dni_registro.enviarDNIRegistro(dni);

                        if (msg.equals("ACEPTADO"))
                            this.ventana.ventanaConfirmacion(dni);
                        else
                            this.ventana.ventanaError("El DNI ingresado ya existe");
                    } catch (IOException e1) {
                        this.ventana.ventanaError("Error de conexion");
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else

                    this.ventana.ventanaError("El DNI proporcionado es invalido");
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

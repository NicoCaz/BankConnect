package org.grupo10.sistema_totem;


import org.grupo10.modelo.Turno;
import org.grupo10.vista.IVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControladorTotem implements ActionListener {

    private IVista vista;
    private SistemaTotem sistemaTotem = new SistemaTotem();

    public ControladorTotem() {
        this.vista = new VistaTotem(this);
        this.vista.mostrar();
        try {
            sistemaTotem.ejecucion();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if(comando.equalsIgnoreCase("Aceptar")) {
            String dni = this.vista.getDisplayLabel().getText();

            try{
                Turno t = sistemaTotem.crearTurno(dni);
                this.vista.ventanaConfirmacion("Su turno es: " + t.getDni());
            } catch (Exception ex) {
                this.vista.ventanaError(ex.getMessage());
            }
        }

    }



}

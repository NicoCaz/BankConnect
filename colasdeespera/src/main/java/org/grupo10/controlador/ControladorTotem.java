package org.grupo10.controlador;


import org.grupo10.modelo.Turno;
import org.grupo10.negocio.SistemaTotem;
import org.grupo10.vista.IVista;
import org.grupo10.vista.VistaTotem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTotem implements ActionListener {

    private IVista vista;
    private SistemaTotem sistemaTotem = new SistemaTotem();

    public ControladorTotem(){
        this.vista = new VistaTotem(this);
        this.vista.mostrar();
        sistemaTotem.ejecucion();
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

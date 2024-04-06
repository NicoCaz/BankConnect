package org.grupo10.controlador;

import org.grupo10.modelo.Turno;
import org.grupo10.negocio.NegocioTurno;
import org.grupo10.vista.IVista;
import org.grupo10.vista.VistaBox;
import org.grupo10.vista.VistaTotem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTotem implements ActionListener, IControlador{
    private IVista vista;
    private NegocioTurno negocioTurno;
    public ControladorTotem(){
        this.vista = new VistaTotem(this);
        this.vista.mostrar();
        this.conectarServer();

        negocioTurno = new NegocioTurno();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if(comando.equalsIgnoreCase("Aceptar")) {
            String dni = this.vista.getDisplayLabel().getText();

            try{
                Turno t = negocioTurno.crearTurno(dni);
                this.vista.ventanaConfirmacion("Su turno es: " + t.getNumeroTurno());
            } catch (Exception ex) {
                this.vista.ventanaError(ex.getMessage());
            }
        }

    }


    @Override
    public void conectarServer() {

    }
}

package org.grupo10.controlador;

import org.grupo10.vista.IVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ControladorBox implements ActionListener{
    private IVista vista;

    public ControladorBox() {
        this.vista = new VentanaPersonal();
        this.vista.setActionListener(this);
        this.vista.mostrar();
        this.conectarServer();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        System.out.println(comando);
        if (comando.equalsIgnoreCase("Siguiente")) { //bien

        } else if (comando.equalsIgnoreCase(("FinalizarTurno"))){ //bien

        }

    }

    private void conectarServer() {
        SistemaEmpleados.getInstancia().conectar("localhost", 1); //puerto del server hardcodeado en 1
        SistemaEmpleados.getInstancia().crearHilo();

    }
}

package org.grupo10.controlador;


import org.grupo10.negocio.SistemaBox;
import org.grupo10.vista.IVista;
import org.grupo10.vista.VistaBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControladorBox implements ActionListener {
    private final IVista vista;
    private SistemaBox sistemaBox = new SistemaBox();

    public ControladorBox() {
        this.vista = new VistaBox(this);
        this.vista.mostrar();
        sistemaBox.ejecuccion();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        System.out.println(comando);
        if (comando.equalsIgnoreCase("Llamar siguiente")) { //bien
            try {

                String dni = sistemaBox.pedirSiguiente();
                vista.getDisplayLabel().setText("Numero Atendido: " + dni);
            } catch (IOException | ClassNotFoundException ex) {
                vista.ventanaError("No hay clientes esperando");
                sistemaBox.ejecuccion();
            }

        } else if (comando.equalsIgnoreCase(("FinalizarTurno"))){ //bien

        }
    }
//    @Override
//    public void conectarServer() {
//        SistemaEmpleados.getInstancia().conectar("localhost", 1); //puerto del server hardcodeado en 1
//        SistemaEmpleados.getInstancia().crearHilo();
//
//    }


}

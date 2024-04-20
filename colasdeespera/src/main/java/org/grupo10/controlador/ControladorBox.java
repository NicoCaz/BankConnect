package org.grupo10.controlador;


import org.grupo10.modelo.Turno;
import org.grupo10.negocio.SistemaBox;
import org.grupo10.vista.IVista;
import org.grupo10.vista.VistaBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControladorBox implements ActionListener {
    private final IVista vista;
    private SistemaBox sistemaBox = new SistemaBox();
    private Turno turnoActual = null;
    private int cantidadTurnos = 0;

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

                this.turnoActual = sistemaBox.pedirSiguiente();

                vista.getDisplayLabel().setText("Numero Atendido: " + this.turnoActual.getDni());
                //vista.apagarLlamar();
            } catch (IOException | ClassNotFoundException ex) {
                vista.ventanaError("No hay clientes esperando");
            }
        }
//        } else if (comando.equalsIgnoreCase(("FinalizarTurno"))){ //bien
//            if(this.turnoActual != null){
//                try {
//                    sistemaBox.finalizarTurno(this.turnoActual);
//                    //vista.prenderLlamar();
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        }
    }





}

package org.grupo10.controlador;


import org.grupo10.modelo.Turno;
import org.grupo10.modelo.dto.EstadisticaDTO;
import org.grupo10.negocio.SistemaBox;
import org.grupo10.negocio.SistemaEstadistica;
import org.grupo10.vista.IVista;
import org.grupo10.vista.VistaBox;
import org.grupo10.vista.VistaEstadisticas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControladorEstadistica implements ActionListener {
    private final IVista vista;
    private SistemaEstadistica sistemaEstadistica = new SistemaEstadistica();
    private EstadisticaDTO estadisticaDTO=null;

    public ControladorEstadistica() {
        this.vista = new VistaEstadisticas(this);
        this.vista.mostrar();
        sistemaEstadistica.ejecucion();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        System.out.println(comando);
        if (comando.equalsIgnoreCase("Pedir Estadistica")) { //bien

            this.estadisticaDTO = sistemaEstadistica.pidoEstadisticas();

            vista.getDisplayLabel().setText("Personas en espera: "+this.estadisticaDTO.getPersonasEspera());

            vista.getDisplayLabel().setText("Personas atendidas: "+this.estadisticaDTO.getPersonasAtendidas());

            vista.getDisplayLabel().setText("Tiempo promedio: "+this.estadisticaDTO.getTiempoPromedio());

        }
    }




}

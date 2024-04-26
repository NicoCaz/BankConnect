package org.grupo10.sistema_estadistica;


import org.grupo10.modelo.dto.EstadisticaDTO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControladorEstadistica implements ActionListener {
    private final VistaEstadisticas vista;
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
        if (comando.equalsIgnoreCase("Refrescar")) { //bien

            try {
                this.estadisticaDTO = sistemaEstadistica.pidoEstadisticas();

                vista.getPersonasEnEsperaLabel().setText("Personas en espera: "+this.estadisticaDTO.getPersonasEspera());

                vista.getPersonasAtendidaLabel().setText("Personas atendidas: "+this.estadisticaDTO.getPersonasAtendidas());

                vista.getTiempoPromedioLabel().setText("Tiempo promedio: "+ vista.formatTime(this.estadisticaDTO.getTiempoPromedio()));

            } catch (IOException | ClassNotFoundException ex) {
                vista.ventanaError(ex.getMessage());
            }

        }
    }




}

package org.grupo10.sistema_estadistica.controlador;


import org.grupo10.exception.EstadisticaException;
import org.grupo10.modelo.dto.EstadisticaDTO;
import org.grupo10.sistema_estadistica.conexion.SistemaEstadistica;
import org.grupo10.sistema_estadistica.vista.VistaEstadisticas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ControladorEstadistica implements ActionListener {
    private VistaEstadisticas ventana;
    private static ControladorEstadistica instance = null;
    private IEstadisticas estadistica_llamado;

    public static void main(String[] args) {
        ControladorEstadistica.getInstance().comenzar();
    }

    public static ControladorEstadistica getInstance() {
        if (ControladorEstadistica.instance == null)
            ControladorEstadistica.instance = new ControladorEstadistica();
        return ControladorEstadistica.instance;
    }

    private ControladorEstadistica() {
    }

    private void comenzar() {
        try {

            //Lectura de archivo de configuracion de box
            String currentDir = System.getProperty("user.dir");
            String archivoTxt = currentDir + "/colasdeespera/src/org/grupo10/sistema_estadistica/estadisticasconfig.txt";

            BufferedReader br = new BufferedReader(new FileReader(archivoTxt));
            String linea;
            //Leo el Servidor Principal
            linea = br.readLine();
            String[] partes = linea.split(":");
            String ip = partes[0];
            int port = Integer.parseInt(partes[1]);

            //Leo el Servidor de Respaldo
            linea = br.readLine();
            partes = linea.split(":");
            String ipOtro = partes[0];
            int portOtro = Integer.parseInt(partes[1]);


            this.ventana = new VistaEstadisticas();

            new Thread(() -> { // En otro thread para no interferir con GUILlamados
                try {
                    this.estadistica_llamado = new SistemaEstadistica(ip, port, ipOtro, portOtro);
                    // Activa el botón Siguiente (si no hubo IOException)
                } catch (IOException e) {
                    this.ventana.ventanaError("Error de conexion");
                } catch (EstadisticaException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (IOException e) {
            this.ventana.ventanaError("No se ha encontrado el archivo de configuracion");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> { // En otro thread para no interferir con GUILlamados

            try {
                EstadisticaDTO estadisticas = this.estadistica_llamado.resiboEstadistica();
                if(estadisticas != null){
                    this.ventana.getPersonasAtendidaLabel().setText("Personas Atendidas: "+ estadisticas.getPersonasAtendidas());
                    this.ventana.getPersonasEnEsperaLabel().setText("Personas en espera: " + estadisticas.getPersonasEspera());
                    this.ventana.getTiempoPromedioLabel().setText("Tiempo promedio de atencion: "+this.ventana.formatTime(estadisticas.getTiempoPromedio()));
                }
            } catch (IOException e1) {
                this.ventana.ventanaError("Error de conexion");
            } catch (EstadisticaException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
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

package org.grupo10.sistema_estadistica.controlador;


import org.grupo10.exception.EstadisticaException;
import org.grupo10.modelo.dto.EstadisticaDTO;
import org.grupo10.sistema_estadistica.conexion.SistemaEstadistica;
import org.grupo10.sistema_estadistica.vista.VistaEstadisticas;
import org.grupo10.sistema_pantalla.conexion.SistemaPantalla;
import org.grupo10.sistema_pantalla.vista.VistaPantalla;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ControladorEstadistica implements IEstadisticas{
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
        this.ventana = new VistaEstadisticas();

        new Thread(() -> {
            try {
                new SistemaEstadistica(this);
            } catch (FileNotFoundException e) {
                this.ventana.ventanaError("No se ha encontrado el archivo de configuracion");
            } catch (IOException e) {
                this.ventana.ventanaError("Error de conexion");
            } catch (EstadisticaException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


    public void agregarEstadistica(String estadistica) {
        String[] datos = estadistica.split(",");
        //aca delga tenes que  hacer lo mismo que en el de vistapantalla
        // Actualiza el listado de últimos llamados, evitando que haya más de 3
        /// estoy hay que completarlo
        this.actualizar();

    }

    public void actualizar() {


        this.ventana.agregaDatos(entrada.getKey().toString(), entrada.getValue());


    }
    public void abrirMensajeConectando() {
        this.ventana.abrirMensajeConectando();
    }

    public void cerrarMensajeConectando() {
        this.ventana.cerrarMensajeConectando();
    }



}

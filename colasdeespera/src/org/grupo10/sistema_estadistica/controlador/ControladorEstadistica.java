package org.grupo10.sistema_estadistica.controlador;


import org.grupo10.exception.EstadisticaException;
import org.grupo10.sistema_estadistica.conexion.SistemaEstadistica;
import org.grupo10.sistema_estadistica.vista.VistaEstadisticas;

import java.io.FileNotFoundException;
import java.io.IOException;

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
    public void comenzar() {
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
        this.actualizar(datos);

    }

    public void actualizar(String[] datos) {
      this.ventana.agregaDatos(datos);
    }
    public void abrirMensajeConectando() {
        this.ventana.abrirMensajeConectando();
    }

    public void cerrarMensajeConectando() {
        this.ventana.cerrarMensajeConectando();
    }



}

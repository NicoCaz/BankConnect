package org.grupo10.sistema_estadistica.conexion;

import org.grupo10.exception.EstadisticaException;
import org.grupo10.interfaces.Conexion;
import org.grupo10.sistema_estadistica.controlador.ControladorEstadistica;
import org.grupo10.sistema_estadistica.controlador.IEstadisticas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class SistemaEstadistica extends Conexion implements I_EsperarActualizaciones{
    private IEstadisticas pantalla;

    public SistemaEstadistica(IEstadisticas estadistica) throws IOException, EstadisticaException {
        super("/estadisticasconfig.txt");

        this.pantalla = estadistica;

        this.esperarActualizaciones();
    }

    public void esperarActualizaciones() throws IOException {
        while (true) {
            String estadistica;
            try {
                estadistica = in.readLine(); // Recibe DNI del servidor

              this.pantalla.agregarEstadistica(estadistica);
            } catch (IOException  e) { // Hubo una falla. Reintenta / cambia de servidor.
                this.reconectar();
            }
        }
    }

    public void conectar(Map.Entry<String, Integer> entry) throws IOException {
        this.socket = new Socket(entry.getKey(), entry.getValue());
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out.println("ESTADISTICA");
    }


    @Override
    protected void abrirMensajeConectando() {
        ControladorEstadistica.getInstance().abrirMensajeConectando();
    }

    @Override
    protected void cerrarMensajeConectando() {
        ControladorEstadistica.getInstance().cerrarMensajeConectando();
    }

}
package org.grupo10.sistema_pantalla.conexion;

import org.grupo10.interfaces.Conexion;
import org.grupo10.sistema_pantalla.controlador.ControladorPantalla;
import org.grupo10.sistema_pantalla.controlador.IPantalla;

import java.io.*;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class SistemaPantalla extends Conexion implements I_EsperarActualizaciones{
    private IPantalla pantalla;

    public SistemaPantalla(IPantalla pantalla) throws IOException, FileNotFoundException {
        super("/pantallaconfig.txt");
        this.pantalla = pantalla;
        this.esperarActualizaciones();
    }

    // Espera novedades del servidor
    public void esperarActualizaciones() throws IOException {
        while (true) {
            String turno;
            try {
                turno = in.readLine(); // Recibe DNI del servidor

                this.pantalla.agregarLlamado(turno);
            } catch (IOException  e) { // Hubo una falla. Reintenta / cambia de servidor.
                this.reconectar();
            }
        }
    }

    public void conectar(Map.Entry<String, Integer> entry) throws IOException {
        this.socket = new Socket(entry.getKey(), entry.getValue());
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out.println("Pantalla");
    }

    @Override
    protected void abrirMensajeConectando() {
        ControladorPantalla.getInstance().abrirMensajeConectando();
    }

    @Override
    protected void cerrarMensajeConectando() {
        ControladorPantalla.getInstance().cerrarMensajeConectando();
    }


}
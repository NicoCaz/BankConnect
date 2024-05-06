package org.grupo10.sistema_estadistica.controlador;


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
            String archivoTxt = currentDir + "/subsistema_llamados/controlador/llamadosconfig.txt";

            BufferedReader br = new BufferedReader(new FileReader(archivoTxt));
            String linea;
            linea = br.readLine();
            int nroBox = Integer.parseInt(linea);
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


            this.ventana = new VistaEstadisticas(nroBox);

            new Thread(() -> { // En otro thread para no interferir con GUILlamados
                try {
                    this.estadistica_llamado = new ConexionLlamados(nroBox, ip, port, ipOtro, portOtro);
                    // Activa el botón Siguiente (si no hubo IOException)
                    this.ventana.addActionListenerBoton(this);
                } catch (IOException e) {
                    this.ventana.mensajeErrorConexion();
                } catch (BoxException e) {
                    this.ventana.mensajeBoxOcupado();
                }
            }).start();
        } catch (IOException e) {
            this.ventana.mensajeErrorArchivo();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> { // En otro thread para no interferir con GUILlamados
            String msg;
            try {
                msg = this.estadistica_llamado.recibirDNILlamado();
                if (msg.equals("NULL"))
                    this.ventana.mensajeFilaVacia();
                else
                    this.ventana.setDNI(msg);
            } catch (IOException e1) {
                this.ventana.mensajeErrorConexion();
            } catch (BoxException e2) {
                this.ventana.mensajeBoxOcupado();
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

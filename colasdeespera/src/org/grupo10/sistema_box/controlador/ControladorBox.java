package org.grupo10.sistema_box.controlador;



import org.grupo10.sistema_box.conexion.SistemaBox;
import org.grupo10.sistema_box.vista.VistaBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class ControladorBox implements ActionListener {
    private VistaBox ventana;
    private static ControladorBox instance = null;
    private SistemaBox dni_llamado;

    public static void main(String[] args) {
        ControladorBox.getInstance().comenzar();
    }

    public static ControladorBox getInstance() {
        if (ControladorBox.instance == null)
            ControladorBox.instance = new ControladorBox();
        return ControladorBox.instance;
    }

    private ControladorBox() {
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


            this.ventana = new VistaBox(nroBox);

            new Thread(() -> { // En otro thread para no interferir con GUILlamados
                try {
                    this.dni_llamado = new SistemaBox(nroBox, ip, port, ipOtro, portOtro);
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


    public void actionPerformed(ActionEvent e) {
        new Thread(() -> { // En otro thread para no interferir con GUILlamados
            String msg;
            try {
                msg = this.dni_llamado.recibirDNILlamado();
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

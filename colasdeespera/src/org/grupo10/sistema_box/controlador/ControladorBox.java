package org.grupo10.sistema_box.controlador;


import org.grupo10.exception.BoxException;
import org.grupo10.interfaces.IControlador;
import org.grupo10.sistema_box.conexion.SistemaBox;
import org.grupo10.sistema_box.vista.VistaBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ControladorBox implements ActionListener, IControlador {
    private VistaBox ventana;
    private static ControladorBox instance = null;
    private SistemaBox dni_llamado;
    private int nroBox=0;

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

    public void comenzar() {
        try {

            //Lectura de archivo de configuracion de box
            String currentDir = System.getProperty("user.dir");
            String archivoTxt = currentDir + "/colasdeespera/src/org/grupo10/sistema_box/boxconfig.txt";

            BufferedReader br = new BufferedReader(new FileReader(archivoTxt));
            String linea;
            linea = br.readLine();
            this.nroBox = Integer.parseInt(linea);
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


            this.ventana = new VistaBox(this.nroBox);

            new Thread(() -> { // En otro thread para no interferir con GUILlamados
                try {
                    this.dni_llamado = new SistemaBox(nroBox, ip, port, ipOtro, portOtro);
                    // Activa el botón Siguiente (si no hubo IOException)
                    this.ventana.prenderLlamar();
                } catch (IOException e) {
                    this.ventana.ventanaError("Hubo un error");

                } catch (BoxException e) {
                    this.ventana.ventanaError("El box esta ocupado");
                    System.exit(1);
                }
            }).start();
        } catch (IOException e) {
            this.ventana.ventanaError("Hubo un error en la lectura del archivo");
        }
    }


    public void actionPerformed(ActionEvent e) {
        new Thread(() -> { // En otro thread para no interferir con GUILlamados
            String msg;
            try {
                msg = this.dni_llamado.llamarSiguente();
                if (msg.equals("NULL"))
                    this.ventana.ventanaError("Fila Vacia");
                else
                    this.ventana.dniLLamado(msg);
            } catch (IOException e1) {
                this.ventana.ventanaError("Error en la conexion");
            } catch (BoxException e2) {
                this.ventana.ventanaError("El box esta ocupado");
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

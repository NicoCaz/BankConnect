package org.grupo10.sistema_pantalla.controlador;

import org.grupo10.sistema_pantalla.conexion.SistemaPantalla;
import org.grupo10.sistema_pantalla.vista.VistaPantalla;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControladorPantalla implements IPantalla {
    private static ControladorPantalla instance;
    private VistaPantalla ventana;
    private VistaPantalla pantalla;
    private final int maxLlamados = 10;


    private List<String> boxUltimosLlamados = new ArrayList<String>();
    private List<String> dniUltimosLlamados = new ArrayList<String>();

    public static void main(String[] args) {
        ControladorPantalla.getInstance().comenzar();
    }

    public static ControladorPantalla getInstance() {
        if (ControladorPantalla.instance == null)
            ControladorPantalla.instance = new ControladorPantalla();
        return ControladorPantalla.instance;
    }

    private ControladorPantalla() {
    }

    private void comenzar() {
        this.ventana = new VistaPantalla();
        this.pantalla = this.ventana;
        new Thread(() -> {
            try {
                new SistemaPantalla(this);
            } catch (FileNotFoundException e) {
                this.ventana.ventanaError("No se ha encontrado el archivo de configuracion");
            } catch (IOException e) {
                this.ventana.ventanaError("Error de conexion");
            }
        }).start();
    }

    @Override
    public void agregarLlamado(String turno) {
        String[] datos = turno.split(",");
        this.boxUltimosLlamados.add(datos[0]);
        this.dniUltimosLlamados.add(datos[1]);

        if (this.boxUltimosLlamados.size() > this.maxLlamados){
            this.dniUltimosLlamados.remove(0);
            this.boxUltimosLlamados.remove(0);
        }
        this.actualizar();
    }

    @Override
    public void actualizar() {

            this.pantalla.agregarDatos(this.boxUltimosLlamados,this.dniUltimosLlamados);

    }

    public void abrirMensajeConectando() {
        this.ventana.abrirMensajeConectando();
    }

    public void cerrarMensajeConectando() {
        this.ventana.cerrarMensajeConectando();
    }
}

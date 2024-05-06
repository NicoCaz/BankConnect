package org.grupo10.sistema_pantalla.controlador;

import org.grupo10.modelo.Turno;
import org.grupo10.sistema_pantalla.conexion.SistemaPantalla;
import org.grupo10.sistema_pantalla.vista.VistaPantalla;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ControladorPantalla implements IPantalla {
    private static ControladorPantalla instance;
    private VistaPantalla ventana;
    private VistaPantalla pantalla;
    private final int maxLlamados = 10;

    private LinkedHashMap<Integer, String> ultimosLlamados = new LinkedHashMap<Integer, String>();

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
                new ConexionEspera(this);
            } catch (FileNotFoundException e) {
                this.ventana.mensajeErrorArchivo();
            } catch (IOException e) {
                this.ventana.mensajeErrorConexion();
            }
        }).start();
    }

    @Override
    public void agregarLlamado(int nroBox, String dni) {
        // Actualiza el listado de últimos llamados, evitando que haya más de 3
        this.ultimosLlamados.remove(nroBox);
        this.ultimosLlamados.put(nroBox, dni);
        if (this.ultimosLlamados.size() > this.maxLlamados)
            this.ultimosLlamados.remove(this.ultimosLlamados.entrySet().iterator().next());
        this.actualizar();
    }

    @Override
    public void actualizar() {
        int i = 0;
        Map.Entry<Integer, String> entrada;
        Iterator<Map.Entry<Integer, String>> it = this.ultimosLlamados.entrySet().iterator();
        while (it.hasNext()) {
            entrada = it.next();
            this.pantalla.setLlamado(this.ultimosLlamados.size() - i, entrada.getKey().toString(),
                    entrada.getValue());
            i++;
        }
    }

    public void abrirMensajeConectando() {
        this.ventana.abrirMensajeConectando();
    }

    public void cerrarMensajeConectando() {
        this.ventana.cerrarMensajeConectando();
    }
}

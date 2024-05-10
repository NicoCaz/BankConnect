package org.grupo10.sistema_pantalla.controlador;

import org.grupo10.sistema_pantalla.conexion.SistemaPantalla;
import org.grupo10.sistema_pantalla.vista.VistaPantalla;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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
                new SistemaPantalla(this);
            } catch (FileNotFoundException e) {
                this.ventana.ventanaError("No se ha encontrado el archivo de configuracion");
            } catch (IOException e) {
                this.ventana.ventanaError("Error de conexion");
            }
        }).start();
    }

    @Override
    public void agregarLlamado( String turno) {
        String[] datos = turno.split(",");
        // Actualiza el listado de últimos llamados, evitando que haya más de 3
        //Integer primeraClaveDelMapa = ultimosLlamados.keySet().iterator().next();

        this.ultimosLlamados.put(Integer.valueOf(datos[0]), datos[1]);
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
            this.pantalla.agregarDatos(entrada.getKey().toString(), entrada.getValue());
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

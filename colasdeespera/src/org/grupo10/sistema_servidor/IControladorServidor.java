package org.grupo10.sistema_servidor;

import org.grupo10.modelo.Fila;
import org.grupo10.modelo.FilaFinalizada;
import org.grupo10.modelo.Turno;

import java.io.IOException;

public interface IControladorServidor {

    void esperarConexiones();

    void enviarTurnoLlamado(Turno t);

    void enviarEstadisticas()throws IOException;
    FilaFinalizada getTurnosFinalizados();
    Fila getTurnosEnEspera();
    boolean hayCambios();
    void setCambios(boolean b);
    void quitarBox(Integer box);
}


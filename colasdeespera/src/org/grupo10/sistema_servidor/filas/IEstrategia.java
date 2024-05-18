package org.grupo10.sistema_servidor.filas;

import org.grupo10.modelo.Turno;

import java.util.ArrayList;

public interface IEstrategia {
    void ordenar(ArrayList<Turno> fila);
}

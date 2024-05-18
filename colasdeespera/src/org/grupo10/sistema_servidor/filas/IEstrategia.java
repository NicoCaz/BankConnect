package org.grupo10.sistema_servidor.filas;

import org.grupo10.modelo.Turno;

import java.io.Serializable;
import java.util.ArrayList;

public interface IEstrategia extends Serializable, Cloneable {
    void ordenar(ArrayList<Turno> fila);
}

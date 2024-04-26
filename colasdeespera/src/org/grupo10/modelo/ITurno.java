package org.grupo10.modelo;

import org.grupo10.exception.ClienteRepetidoException;

public interface ITurno {
    void agregarTurno(Cliente cliente) throws ClienteRepetidoException;
    Cliente sacarTurno();

}

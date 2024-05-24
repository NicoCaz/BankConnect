package org.grupo10.sistema_servidor.filas;

import org.grupo10.exception.ClienteRepetidoException;

import java.util.ArrayList;

public interface IFilas<T> {
    boolean estaVacia();
    void agregarTurno(T t) throws ClienteRepetidoException;
    T sacarTurno();
    int cantidad();
    ArrayList<T> getTurnos();
}

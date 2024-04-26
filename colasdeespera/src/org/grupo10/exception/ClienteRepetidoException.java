package org.grupo10.exception;

public class ClienteRepetidoException extends Exception {
    public ClienteRepetidoException() {
        super("El DNI ingresado ya está en la fila");
    }
}
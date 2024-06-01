package org.grupo10.factory;

public interface AbstractFactory {
    ILogLlamados createLlamados();
    ILogRegistro createRegistro();
    ILogRepositorio createRepositorio();
}

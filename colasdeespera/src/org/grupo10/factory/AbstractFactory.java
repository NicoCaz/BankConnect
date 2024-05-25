package org.grupo10.factory;

public interface AbstractFactory {
    ILlamados createLlamados();
    IRegistro createRegistro();
    IRepositorio createRepositorio();
}

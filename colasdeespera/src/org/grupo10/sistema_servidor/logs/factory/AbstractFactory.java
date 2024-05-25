package org.grupo10.sistema_servidor.logs.factory;

public interface AbstractFactory {
    ILlamados createLlamados();
    IRegistro createRegistro();
    IRepositorio createRepositorio();
}

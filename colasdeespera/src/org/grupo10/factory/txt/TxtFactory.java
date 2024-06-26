package org.grupo10.factory.txt;

import org.grupo10.factory.AbstractFactory;
import org.grupo10.factory.ILogLlamados;
import org.grupo10.factory.ILogRegistro;
import org.grupo10.factory.ILogRepositorio;

public class TxtFactory implements AbstractFactory {

    @Override
    public ILogLlamados createLlamados() {
        return new TxtLlamados();
    }

    @Override
    public ILogRegistro createRegistro() {
        return new TxtRegistro();
    }

    @Override
    public ILogRepositorio createRepositorio() {
        return new TxtRepositorio();
    }
}

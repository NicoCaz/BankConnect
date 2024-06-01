package org.grupo10.factory.xml;

import org.grupo10.factory.AbstractFactory;
import org.grupo10.factory.ILogLlamados;
import org.grupo10.factory.ILogRegistro;
import org.grupo10.factory.ILogRepositorio;

public class XmlFactory implements AbstractFactory {
    @Override
    public ILogLlamados createLlamados() {
        return new XmlLlamados();
    }

    @Override
    public ILogRegistro createRegistro() {
        return new XmlRegistro();
    }

    @Override
    public ILogRepositorio createRepositorio() {
        return new XmlRepositorio();
    }
}

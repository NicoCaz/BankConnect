package org.grupo10.factory.xml;

import org.grupo10.factory.AbstractFactory;
import org.grupo10.factory.ILlamados;
import org.grupo10.factory.IRegistro;
import org.grupo10.factory.IRepositorio;

public class XmlFactory implements AbstractFactory {
    @Override
    public ILlamados createLlamados() {
        return new XmlLlamados();
    }

    @Override
    public IRegistro createRegistro() {
        return new XmlRegistro();
    }

    @Override
    public IRepositorio createRepositorio() {
        return new XmlRepositorio();
    }
}

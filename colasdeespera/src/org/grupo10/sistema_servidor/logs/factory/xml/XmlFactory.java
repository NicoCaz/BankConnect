package org.grupo10.sistema_servidor.logs.factory.xml;

import org.grupo10.sistema_servidor.logs.factory.AbstractFactory;
import org.grupo10.sistema_servidor.logs.factory.ILlamados;
import org.grupo10.sistema_servidor.logs.factory.IRegistro;
import org.grupo10.sistema_servidor.logs.factory.IRepositorio;

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

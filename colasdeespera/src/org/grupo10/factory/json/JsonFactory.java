package org.grupo10.factory.json;


import org.grupo10.factory.AbstractFactory;
import org.grupo10.factory.ILogLlamados;
import org.grupo10.factory.ILogRegistro;
import org.grupo10.factory.ILogRepositorio;

public class JsonFactory implements AbstractFactory {

    @Override
    public ILogLlamados createLlamados() {
        return new JsonLlamados();
    }

    @Override
    public ILogRegistro createRegistro() {
        return new JsonRegistro();
    }

    @Override
    public ILogRepositorio createRepositorio() {
        return new JsonRepositorio();
    }
}

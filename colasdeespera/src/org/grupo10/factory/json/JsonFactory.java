package org.grupo10.factory.json;


import org.grupo10.factory.AbstractFactory;
import org.grupo10.factory.ILlamados;
import org.grupo10.factory.IRegistro;
import org.grupo10.factory.IRepositorio;

public class JsonFactory implements AbstractFactory {

    @Override
    public ILlamados createLlamados() {
        return new JsonLlamados();
    }

    @Override
    public IRegistro createRegistro() {
        return new JsonRegistro();
    }

    @Override
    public IRepositorio createRepositorio() {
        return new JsonRepositorio();
    }
}

package org.grupo10.sistema_servidor.logs.factory.json;


import org.grupo10.sistema_servidor.logs.factory.AbstractFactory;
import org.grupo10.sistema_servidor.logs.factory.ILlamados;
import org.grupo10.sistema_servidor.logs.factory.IRegistro;
import org.grupo10.sistema_servidor.logs.factory.IRepositorio;

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

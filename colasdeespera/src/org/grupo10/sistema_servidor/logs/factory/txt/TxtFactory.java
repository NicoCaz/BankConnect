package org.grupo10.sistema_servidor.logs.factory.txt;

import org.grupo10.sistema_servidor.logs.factory.AbstractFactory;
import org.grupo10.sistema_servidor.logs.factory.ILlamados;
import org.grupo10.sistema_servidor.logs.factory.IRegistro;
import org.grupo10.sistema_servidor.logs.factory.IRepositorio;

public class TxtFactory implements AbstractFactory {

    @Override
    public ILlamados createLlamados() {
        return new TxtLlamados();
    }

    @Override
    public IRegistro createRegistro() {
        return new TxtRegistro();
    }

    @Override
    public IRepositorio createRepositorio() {
        return new TxtRepositorio();
    }
}

package org.grupo10.factory.txt;

import org.grupo10.factory.AbstractFactory;
import org.grupo10.factory.ILlamados;
import org.grupo10.factory.IRegistro;
import org.grupo10.factory.IRepositorio;

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

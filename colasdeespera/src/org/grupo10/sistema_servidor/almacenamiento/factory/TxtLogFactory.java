package org.grupo10.sistema_servidor.almacenamiento.factory;

import org.grupo10.sistema_servidor.almacenamiento.Log;
import org.grupo10.sistema_servidor.almacenamiento.LogBase;
import org.grupo10.sistema_servidor.almacenamiento.TxtLogDecorator;

public class TxtLogFactory implements LogFactory {
    @Override
    public Log createLog() {
        return new TxtLogDecorator(new LogBase());
    }
}

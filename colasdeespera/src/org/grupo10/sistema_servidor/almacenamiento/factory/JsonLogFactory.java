package org.grupo10.sistema_servidor.almacenamiento.factory;

import org.grupo10.sistema_servidor.almacenamiento.JsonLogDecorator;
import org.grupo10.sistema_servidor.almacenamiento.Log;
import org.grupo10.sistema_servidor.almacenamiento.LogBase;

public class JsonLogFactory implements LogFactory {
    @Override
    public Log createLog() {
        return new JsonLogDecorator(new LogBase());
    }


}

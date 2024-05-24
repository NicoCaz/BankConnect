package org.grupo10.sistema_servidor.logs.factory;

import org.grupo10.sistema_servidor.logs.JsonLogDecorator;
import org.grupo10.sistema_servidor.logs.Log;
import org.grupo10.sistema_servidor.logs.LogBase;

public class JsonLogFactory implements LogFactory {
    @Override
    public Log createLog() {
        return new JsonLogDecorator(new LogBase());
    }


}

package org.grupo10.sistema_servidor.logs.factory;

import org.grupo10.sistema_servidor.logs.Log;
import org.grupo10.sistema_servidor.logs.LogBase;
import org.grupo10.sistema_servidor.logs.TxtLogDecorator;

public class TxtLogFactory implements LogFactory {
    @Override
    public Log createLog() {
        return new TxtLogDecorator(new LogBase());
    }
}

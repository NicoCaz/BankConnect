package org.grupo10.factory;

import org.grupo10.factory.json.JsonFactory;
import org.grupo10.factory.txt.TxtFactory;
import org.grupo10.factory.xml.XmlFactory;

public class FactorySelector {
    private AbstractFactory logFactory;

    public FactorySelector(String logFactory) {
        this.logFactory = createLogFactory(logFactory);
    }

    private AbstractFactory createLogFactory(String logFormat) {
        switch (logFormat.toLowerCase()) {
            case "txt":
                return new TxtFactory();
            case "json":
                return new JsonFactory();
            case "xml":
                return new XmlFactory();
            default:
                throw new IllegalArgumentException("Formato de log no soportado: " + logFormat);
        }
    }



    public ILogRegistro logClientRegistro() {
         return logFactory.createRegistro();
    }

    public ILogLlamados logClientLlamado() {
         return logFactory.createLlamados();
    }
    public ILogRepositorio clientRepository(){
        return logFactory.createRepositorio();
    }
}
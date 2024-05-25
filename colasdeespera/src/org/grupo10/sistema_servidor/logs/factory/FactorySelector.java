package org.grupo10.sistema_servidor.logs.factory;

import org.grupo10.sistema_servidor.logs.factory.json.JsonFactory;
import org.grupo10.sistema_servidor.logs.factory.txt.TxtFactory;
import org.grupo10.sistema_servidor.logs.factory.xml.XmlFactory;

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



    public IRegistro logClientRegistro() {
         return logFactory.createRegistro();
    }

    public ILlamados logClientLlamado() {
         return logFactory.createLlamados();
    }
    public IRepositorio clientRepository(){
        return logFactory.createRepositorio();
    }
}
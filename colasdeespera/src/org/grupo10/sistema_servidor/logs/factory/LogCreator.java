package org.grupo10.sistema_servidor.logs.factory;

import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.logs.Log;

import java.util.Date;

public class LogCreator {
    private LogFactory logFactory;

    public LogCreator(String logFactory) {
        this.logFactory = createLogFactory(logFactory);
    }
    private LogFactory createLogFactory(String logFormat) {
        switch (logFormat.toLowerCase()) {
            case "txt":
                return new TxtLogFactory();
            case "json":
                return new JsonLogFactory();
            case "xml":
                return new XmlLogFactory();
            default:
                throw new IllegalArgumentException("Formato de log no soportado: " + logFormat);
        }
    }



    public void logClientRegistro(Turno turno, Date date) {
        Log log = logFactory.createLog();
        log.logRegistro(turno, date);
    }

    public void logClientLlamado(Turno turno, int boxNumber, Date date) {
        Log log = logFactory.createLog();
        log.logLlamado(turno, boxNumber, date);
    }
}
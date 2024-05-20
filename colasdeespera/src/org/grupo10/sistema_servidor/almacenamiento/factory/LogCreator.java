package org.grupo10.sistema_servidor.almacenamiento.factory;

import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.almacenamiento.Log;

import java.util.Date;

public class LogCreator {
    private LogFactory logFactory;

    public LogCreator(LogFactory logFactory) {
        this.logFactory = logFactory;
    }

    public void logClientEntry(Turno turno, Date date) {
        Log log = logFactory.createLogRegistro();
        log.log(turno, date);
    }

    public void logClientCall(Turno turno, int boxNumber, Date date) {
        Log log = logFactory.createLogLlamado();
        log.log(turno, boxNumber, date);
    }
}
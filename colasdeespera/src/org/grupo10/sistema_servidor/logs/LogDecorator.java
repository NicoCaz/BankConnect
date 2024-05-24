package org.grupo10.sistema_servidor.logs;

import org.grupo10.modelo.Turno;

import java.util.Date;

public abstract class LogDecorator implements Log {
    protected Log decoratedLog;

    public LogDecorator(Log decoratedLog) {
        this.decoratedLog = decoratedLog;
    }

    @Override
    public void logLlamado(Turno t, int nBox, Date d) {
        decoratedLog.logLlamado(t, nBox, d);
        logToFile(t, nBox, d);

    }

    @Override
    public void logRegistro(Turno t, Date d) {
        decoratedLog.logRegistro(t, d);
        logToFile(t, d);
    }

    protected abstract void logToFile(Turno turno, Date date);

    protected abstract void logToFile(Turno turno, int boxNumber, Date date);
}
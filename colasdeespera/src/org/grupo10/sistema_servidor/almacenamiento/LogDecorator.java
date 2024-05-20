package org.grupo10.sistema_servidor.almacenamiento;

import org.grupo10.modelo.Turno;

import java.util.Date;

public abstract class LogDecorator implements Log {
    protected Log decoratedLog;

    public LogDecorator(Log decoratedLog) {
        this.decoratedLog = decoratedLog;
    }

    @Override
    public void log(Turno turno, Date date) {
        decoratedLog.log(turno, date);
    }

    @Override
    public void log(Turno turno, int boxNumber, Date date) {
        decoratedLog.log(turno, boxNumber, date);
    }
}
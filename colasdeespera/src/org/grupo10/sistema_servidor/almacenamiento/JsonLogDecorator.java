package org.grupo10.sistema_servidor.almacenamiento;

import org.grupo10.modelo.Turno;

import java.util.Date;

public class JsonLogDecorator extends LogDecorator {
    public JsonLogDecorator(Log decoratedLog) {
        super(decoratedLog);
    }

    @Override
    public void log(Turno turno, Date date) {
        super.log(turno, date);
        saveAsJson(turno, date);
    }

    @Override
    public void log(Turno turno, int boxNumber, Date date) {
        super.log(turno, boxNumber, date);
        saveAsJson(turno, boxNumber, date);
    }

    private void saveAsJson(Turno turno, Date date) {
        // Implementación para guardar el log de entrada en un archivo json
    }

    private void saveAsJson(Turno turno, int boxNumber, Date date) {
        // Implementación para guardar el log de llamado en un archivo json
    }
}
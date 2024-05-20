package org.grupo10.sistema_servidor.almacenamiento;

import org.grupo10.modelo.Turno;

import java.util.Date;

public class TxtLogDecorator extends LogDecorator {
    public TxtLogDecorator(Log decoratedLog) {
        super(decoratedLog);
    }

    @Override
    public void log(Turno turno, Date date) {
        super.log(turno, date);
        saveAsTxt(turno, date);
    }

    @Override
    public void log(Turno turno, int boxNumber, Date date) {
        super.log(turno, boxNumber, date);
        saveAsTxt(turno, boxNumber, date);
    }

    private void saveAsTxt(Turno turno, Date date) {
        // Implementación para guardar el log de entrada en un archivo txt
    }

    private void saveAsTxt(Turno turno, int boxNumber, Date date) {
        // Implementación para guardar el log de llamado en un archivo txt
    }
}
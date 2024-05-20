package org.grupo10.sistema_servidor.almacenamiento;

import org.grupo10.modelo.Turno;

import java.util.Date;

public class XmlLogDecorator extends LogDecorator {
    public XmlLogDecorator(Log decoratedLog) {
        super(decoratedLog);
    }

    @Override
    public void log(Turno turno, Date date) {
        super.log(turno, date);
        saveAsXml(turno, date);
    }

    @Override
    public void log(Turno turno, int boxNumber, Date date) {
        super.log(turno, boxNumber, date);
        saveAsXml(turno, boxNumber, date);
    }

    private void saveAsXml(Turno turno, Date date) {
        // Implementación para guardar el log de entrada en un archivo xml
    }

    private void saveAsXml(Turno turno, int boxNumber, Date date) {
        // Implementación para guardar el log de llamado en un archivo xml
    }
}
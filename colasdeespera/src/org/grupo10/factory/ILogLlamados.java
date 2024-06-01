package org.grupo10.factory;

import org.grupo10.modelo.Turno;

import java.time.LocalDate;

public interface ILogLlamados {
    void logToFile(Turno turno, int boxNumber, LocalDate date);
}

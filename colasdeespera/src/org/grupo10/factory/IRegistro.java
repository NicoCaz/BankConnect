package org.grupo10.factory;

import org.grupo10.modelo.Turno;

import java.time.LocalDate;

public interface IRegistro {
    void logToFile(Turno turno, LocalDate date);
}

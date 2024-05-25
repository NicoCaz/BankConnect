package org.grupo10.sistema_servidor.logs.factory;

import org.grupo10.modelo.Turno;

import java.time.LocalDate;

public interface ILlamados {
    void logToFile(Turno turno, int boxNumber, LocalDate date);
}

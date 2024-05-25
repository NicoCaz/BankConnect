package org.grupo10.sistema_servidor.logs.factory;

import org.grupo10.modelo.Turno;

import java.time.LocalDate;

public interface IRegistro {
    void logToFile(Turno turno, LocalDate date);
}

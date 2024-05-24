package org.grupo10.sistema_servidor.logs;

import org.grupo10.modelo.Turno;

import java.util.Date;

public interface Log {
    void logLlamado(Turno t, int nBox, Date d);
    void logRegistro(Turno t, Date d);

}

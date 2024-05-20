package org.grupo10.sistema_servidor.almacenamiento;

import org.grupo10.modelo.Turno;

import java.util.Date;

public interface Log {
    void log(Turno t, int nBox, Date d);
    void log(Turno t, Date d);

}

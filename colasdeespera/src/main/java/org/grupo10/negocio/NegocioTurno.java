package org.grupo10.negocio;

import org.grupo10.modelo.Turno;

public class NegocioTurno {

    public Turno crearTurno(String dni) throws Exception {
        Turno t;
        if (dni.length() < 6) {
            throw new Exception("ERROR: Ingrese un DNI Valido");
        } else {
            t = new Turno(dni);
        }
        return t;
    }

    public void cerrarTurno(Turno t){

    }
}

package org.grupo10.modelo.dto;

import org.grupo10.modelo.ITurno;
import org.grupo10.modelo.Turno;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class TurnoFinalizadoDTO implements Serializable {
    private Turno t;
    private Date horarioSalida;

    public TurnoFinalizadoDTO(Turno t) {
        this.t = t;
        this.horarioSalida = new Date();
    }

    public Turno getT() {
        return t;
    }

    public void setT(Turno t) {
        this.t = t;
    }

    public Date getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(Date horarioSalida) {
        this.horarioSalida = horarioSalida;
    }
}

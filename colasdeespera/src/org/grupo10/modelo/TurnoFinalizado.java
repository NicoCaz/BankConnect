package org.grupo10.modelo;

import java.io.Serializable;
import java.util.Date;

public class TurnoFinalizado implements Serializable, Cloneable {
    private Turno t;
    private Date horarioSalida;

    public TurnoFinalizado(Turno t) {
        this.t = t;
        this.horarioSalida = new Date();
    }
    @Override
    public Object clone()  {
        Object clone = null;
        try{
            clone = super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return clone;
    }

    public Turno getT() {
        return t;
    }

    public Date getHorarioSalida() {
        return horarioSalida;
    }

}

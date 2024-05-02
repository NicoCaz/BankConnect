package org.grupo10.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class FilaFinalizada implements Serializable, Cloneable {
    private ArrayList<TurnoFinalizado> fila = new ArrayList();

    public int cantidadFinalizada(){
        return this.fila.size();
    }
    public void agregarTurno(TurnoFinalizado turno) {
            this.fila.add(turno);
    }
    public ArrayList<TurnoFinalizado> getTurnos() {return this.fila;}

    public TurnoFinalizado sacarTurno() {
        TurnoFinalizado retorno = null;
        if (this.fila.size() > 0) {
            retorno = this.fila.get(0);
            this.fila.remove(0);
        }
        return retorno;
    }

    // ARREGLAR
    @Override
    public Object clone() {
        FilaFinalizada f = null;
        try {
            f = (FilaFinalizada) super.clone();
            f.fila = new ArrayList<>();
            for (TurnoFinalizado c: this.fila)
                f.fila.add((TurnoFinalizado) c.clone());
        } catch (CloneNotSupportedException e) {
            //Nunca entra
            e.printStackTrace();
        }
        return f;
    }
}

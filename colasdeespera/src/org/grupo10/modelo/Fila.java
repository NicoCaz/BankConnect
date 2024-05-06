package org.grupo10.modelo;

import org.grupo10.exception.ClienteRepetidoException;

import java.io.Serializable;
import java.util.ArrayList;

public class Fila implements  Serializable, Cloneable {
    private ArrayList<Turno> fila = new ArrayList();


    public void agregarTurno(Turno turno) throws ClienteRepetidoException {
        if (!this.fila.contains(turno)) {
            this.fila.add(turno);
        } else
            throw new ClienteRepetidoException();
    }

    public Turno sacarTurno() {
        Turno retorno = null;
        if (this.fila.size() > 0) {
            retorno = this.fila.get(0);
            this.fila.remove(0);
        }
        return retorno;
    }
    public ArrayList<Turno> getTurnos(){
        return this.fila;
    }

    public int cantidadEspera(){
        return this.fila.size();
    }
    // ARREGLAR
    @Override
    public Object clone() {
        Fila f = null;
        try {
            f = (Fila) super.clone();
            f.fila = new ArrayList<>();
            for (Turno c: this.fila)
                f.fila.add((Turno) c.clone());
        } catch (CloneNotSupportedException e) {
            //Nunca entra
            e.printStackTrace();
        }
        return f;
    }
}

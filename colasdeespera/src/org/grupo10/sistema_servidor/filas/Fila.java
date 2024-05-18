package org.grupo10.sistema_servidor.filas;

import org.grupo10.exception.ClienteRepetidoException;
import org.grupo10.modelo.Turno;

import java.io.Serializable;
import java.util.ArrayList;

public class Fila implements IFilas<Turno> ,Serializable, Cloneable {
    private ArrayList<Turno> fila = new ArrayList();
    private IEstrategia estrategia;

    public boolean estaVacia(){
        return this.fila.isEmpty();
    }

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

    public int cantidad(){
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

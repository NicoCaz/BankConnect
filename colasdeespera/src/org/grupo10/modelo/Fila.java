package org.grupo10.modelo;

import org.grupo10.exception.ClienteRepetidoException;

import java.io.Serializable;
import java.util.ArrayList;

public class Fila implements ITurno, Serializable, Cloneable {
    private ArrayList<Cliente> fila = new ArrayList();


    public void agregarTurno(Cliente cliente) throws ClienteRepetidoException {
        if (!this.fila.contains(cliente)) {
            this.fila.add(cliente);
        } else
            throw new ClienteRepetidoException();
    }

    public Cliente sacarTurno() {
        Cliente retorno = null;
        if (this.fila.size() > 0) {
            retorno = this.fila.get(0);
            this.fila.remove(0);
        }
        return retorno;
    }

    // ARREGLAR
    @Override
    public Object clone() {
        Fila f = null;
        try {
            f = (Fila) super.clone();
            f.fila = new ArrayList<>();
            for (Cliente c: this.fila)
                f.fila.add((Cliente) c.clone());
        } catch (CloneNotSupportedException e) {
            //Nunca entra
            e.printStackTrace();
        }
        return f;
    }
}

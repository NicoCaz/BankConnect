package org.grupo10.modelo;

import org.grupo10.sistema_servidor.filas.Fila;
import org.grupo10.sistema_servidor.filas.FilaFinalizada;

import java.io.Serializable;

public class FilasDTO implements Serializable {
    private Fila espera;
    private FilaFinalizada finalizada;

    public FilasDTO (Fila espera, FilaFinalizada finalizada) {
        this.espera = espera;
        this.finalizada = finalizada;
    }

    public FilaFinalizada getFinalizada() {
        return finalizada;
    }

    public Fila getEspera() {
        return espera;
    }
}

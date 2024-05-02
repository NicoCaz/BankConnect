package org.grupo10.modelo.dto;

import org.grupo10.modelo.Fila;
import org.grupo10.modelo.FilaFinalizada;

public class FilasDTO {
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

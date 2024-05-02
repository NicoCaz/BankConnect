package org.grupo10.modelo.dto;

import java.io.Serializable;

public class EstadisticaDTO implements Serializable {
    private int personasEspera;
    private int personasAtendidas;
    private double tiempoPromedio;

    public EstadisticaDTO(int personasEspera, int personasAtendidas, double tiempoPromedio) {
        this.personasEspera = personasEspera;
        this.personasAtendidas= personasAtendidas;
        this.tiempoPromedio= tiempoPromedio;
    }

    public int getPersonasAtendidas() {
        return personasAtendidas;
    }

    public void setPersonasAtendidas(int personasAtendidas) {
        this.personasAtendidas = personasAtendidas;
    }

    public int getPersonasEspera() {
        return personasEspera;
    }

    public void setPersonasEspera(int personasEspera) {
        this.personasEspera = personasEspera;
    }

    public double getTiempoPromedio() {
        return tiempoPromedio;
    }

    public void setTiempoPromedio(Long tiempoPromedio) {
        this.tiempoPromedio = tiempoPromedio;
    }
}

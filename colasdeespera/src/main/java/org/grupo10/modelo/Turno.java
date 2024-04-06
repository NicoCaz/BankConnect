package org.grupo10.modelo;


import java.time.Instant;
import java.util.Date;

public class Turno implements ITurno{

    private static int cantidadDeTurnos=0;
    private String numeroTurno;
    private String dni;
    private Date horarioEntrada;

    public Turno(String dni){
        Turno.cantidadDeTurnos++;
        this.numeroTurno =Turno.cantidadDeTurnos +"-"+ dni.subSequence(dni.length()-3,dni.length()).toString();
        this.dni=dni;
        this.horarioEntrada = Date.from(Instant.now());
    }

    public String getNumeroTurno() {
        return numeroTurno;
    }

    public String getDni() {
        return dni;
    }


    public Date getHorarioEntrada() {
        return horarioEntrada;
    }

    public static int getCantidadDeTurnos() {
        return cantidadDeTurnos;
    }
}

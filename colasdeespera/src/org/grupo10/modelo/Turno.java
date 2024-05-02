package org.grupo10.modelo;


import java.io.Serializable;
import java.util.Date;

public class Turno implements Serializable, Cloneable{

    private static int cantidadDeTurnos=0;
    private String numeroTurno;
    private String dni;
    private Date horarioEntrada;
    private Date horarioSalida;
    private int box;

    public Turno(String dni){
        Turno.cantidadDeTurnos++;
        this.numeroTurno =Turno.cantidadDeTurnos +"-"+ dni.subSequence(dni.length()-3,dni.length()).toString();
        this.dni=dni;
        this.horarioEntrada = new Date();
    }

    @Override
    protected Object clone()  {
        Object clone = null;
        try{
            clone = super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return clone;
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


    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public Date getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(Date horarioSalida) {
        this.horarioSalida = horarioSalida;
    }


}

package org.grupo10.modelo;


import java.io.Serializable;
import java.util.Date;

public class Turno implements Serializable, Cloneable{
    private String dni;
    private Date horarioEntrada;
    private int box;

    public Turno(String dni){
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

    public String getDni() {
        return dni;
    }


    public Date getHorarioEntrada() {
        return horarioEntrada;
    }


    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

}

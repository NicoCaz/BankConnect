package org.grupo10.modelo;


import java.io.Serializable;
import java.util.Date;

public class Turno implements Serializable, Cloneable{
    private String dni;
    private String gEtario;
    private int prioridad;
    private Date horarioEntrada;
    private int box;

    public Turno(String dni){

        this.prioridad = 3;
        this.dni=dni;
        this.horarioEntrada = new Date();
    }

    public Turno(String dni, String nacimiento, int prioridad) {

        this.dni = dni;
        this.prioridad = prioridad;
        this.horarioEntrada = new Date();
    }

    @Override
    public Object clone()  {
        Object clone = null;
        try{
            clone = super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return clone;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getGrupoEtario() {
        return gEtario;
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

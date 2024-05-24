package org.grupo10.modelo;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Turno implements Serializable, Cloneable{
    private String dni;
    private String gEtario;
    private LocalDate fecha;
    private Cliente cliente;
    private int prioridad;
    private Date horarioEntrada;
    private int box;

    public Turno(Cliente c){
        this.cliente = c;
        this.prioridad = 3;
        this.dni= cliente.getDni();
        this.horarioEntrada = new Date();
    }

    public Turno (String dni){
        this.dni = dni;
        this.gEtario = "JOVEN";
        this.prioridad = 3;
        this.horarioEntrada = new Date();
    }

    public Turno(String dni, LocalDate nacimiento, int prioridad) {

        this.dni = dni;
        this.prioridad = prioridad;
        this.fecha = nacimiento;
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

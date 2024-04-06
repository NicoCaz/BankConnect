package org.grupo10.modelo;
public class Turno implements ITurno{

    public static int cantidadDeTurnos=0;
    private String numeroTurno;
    private String dni;
    public Turno(String dni){
        Turno.cantidadDeTurnos++;
        this.numeroTurno =Turno.cantidadDeTurnos +"-"+ dni.subSequence(dni.length()-3,dni.length()).toString();
        this.dni=dni;
    }

    public String getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(String numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}

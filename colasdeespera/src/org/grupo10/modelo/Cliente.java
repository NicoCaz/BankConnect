package org.grupo10.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Cliente implements Serializable, Cloneable {
    private String dni, nombre;
    private static final long UID= 333;
    private LocalDate fechaCumple;
    private int prioridad;

    public Cliente() {
    }


    public Cliente(String dni, String nombre, int prioridad, LocalDate fechaCumple) {
        this.dni = dni;
        this.fechaCumple = fechaCumple;
        this.prioridad = prioridad;
        this.nombre = nombre;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getDni() {
        return this.dni;
    }

    @Override
    public boolean equals(Object obj) {
        boolean retorno = false;
        Cliente otro;
        if (obj != null) {
            otro = (Cliente) obj;
            if (otro.dni.equals(this.dni))
                retorno = true;
        }
        return retorno;
    }

    @Override
    public Object clone() {
        Object clon = null;
        try {
            clon = super.clone();
        } catch (CloneNotSupportedException e) {
            // Nunca entra.
            e.printStackTrace();
        }
        return clon;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaCumple() {
        return fechaCumple;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int calcularEdad() {

        // Calcular la diferencia entre fechas
        Period periodo = Period.between(this.fechaCumple,LocalDate.now());

        // Obtener la cantidad de años transcurridos
        int edad = periodo.getYears();

        // Ajustar la edad si el cumpleaños aún no ha ocurrido en el año actual
        if (periodo.getMonths() < 0 || (periodo.getMonths() == 0 && periodo.getDays() < 0)) {
            edad--;
        }

        return edad;
    }
}

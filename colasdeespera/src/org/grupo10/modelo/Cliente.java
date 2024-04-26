package org.grupo10.modelo;

import java.io.Serializable;

public class Cliente implements Serializable, Cloneable {
    private static final long serialVersionUID = 421;
    private String dni, nombreYApellido;

    public Cliente() {
    }

    public Cliente(String dni, String nombreYApellido) {
        this.dni = dni;
        this.nombreYApellido = nombreYApellido;

    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombreYApellido(String nombreYApellido) {
        this.nombreYApellido = nombreYApellido;
    }


    public String getDni() {
        return this.dni;
    }

    public String getNombreYApellido() {
        return this.nombreYApellido;
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
}

package org.grupo10.sistema_servidor.logs;


import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.logs.factory.LogCreator;

import java.util.Date;


public class Main {
    public static void main(String[] args) {
        LogCreator logCreator=new LogCreator("xml");

        Turno turno=new Turno("1180160688");
        Date now = new Date();

        logCreator.logClientRegistro(turno, now);
        logCreator.logClientLlamado(turno, 1, now);

        turno=new Turno("345232534");
        logCreator.logClientRegistro(turno, now);
        logCreator.logClientLlamado(turno, 2, now);

    }
}
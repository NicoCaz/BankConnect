package org.grupo10.sistema_servidor.logs;


import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.logs.factory.FactorySelector;
import org.grupo10.sistema_servidor.logs.factory.ILlamados;
import org.grupo10.sistema_servidor.logs.factory.IRegistro;


import java.time.LocalDate;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        FactorySelector logCreator=new FactorySelector("xml");

        Turno turno=new Turno("1180160688");
        LocalDate now = LocalDate.now();

        IRegistro registrio=logCreator.logClientRegistro();
        registrio.logToFile(turno, now);

        ILlamados llamados= logCreator.logClientLlamado();
        llamados.logToFile(turno, 1, now);

        turno=new Turno("345232534");
        registrio.logToFile(turno, now);
        llamados.logToFile(turno, 2, now);

    }
}
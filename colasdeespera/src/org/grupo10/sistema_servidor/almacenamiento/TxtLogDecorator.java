package org.grupo10.sistema_servidor.almacenamiento;

import org.grupo10.modelo.Turno;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TxtLogDecorator extends LogDecorator {
    public TxtLogDecorator(Log decoratedLog) {
        super(decoratedLog);
    }

    protected void logToFile(Turno turno, Date date) {
        try (FileWriter writer = new FileWriter("logRegistro.txt", true)) {
            writer.write("Entrada al sistema - Cliente: " + turno.getDni() + ", Date: " + date.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void logToFile(Turno turno, int boxNumber, Date date) {
        try (FileWriter writer = new FileWriter("logLlamado.txt", true)) {
            writer.write("Llamada - Cliente: " + turno.getDni() +  ", Box: " + boxNumber + ", Date: " + date.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
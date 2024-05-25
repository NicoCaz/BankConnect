package org.grupo10.sistema_servidor.logs.factory.txt;

import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.logs.factory.ILlamados;

import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;

public class TxtLlamados implements ILlamados {
    @Override
    public void logToFile(Turno turno, int boxNumber, LocalDate date) {
        try (FileWriter writer = new FileWriter("logLlamado.txt", true)) {
            writer.write("Llamada - Cliente: " + turno.getDni() +  ", Box: " + boxNumber + ", Date: " + date.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

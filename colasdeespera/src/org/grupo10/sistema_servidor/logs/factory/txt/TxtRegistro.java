package org.grupo10.sistema_servidor.logs.factory.txt;

import org.grupo10.modelo.Turno;
import org.grupo10.sistema_servidor.logs.factory.IRegistro;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class TxtRegistro implements IRegistro {

    public void logToFile(Turno turno, LocalDate date) {
        try (FileWriter writer = new FileWriter("logRegistro.txt", true)) {
            writer.write("Entrada al sistema - Cliente: " + turno.getDni() + ", Date: " + date.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

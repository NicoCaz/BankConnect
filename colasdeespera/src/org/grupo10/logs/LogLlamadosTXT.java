package org.grupo10.logs;

import org.grupo10.exception.LogException;
import org.grupo10.modelo.Cliente;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogLlamadosTXT implements ILogsLlamados{
    private String filename;
    private DateFormat dateFormat;

    public LogLlamadosTXT(String filename) throws FileNotFoundException {
        try {
            FileWriter fw = new FileWriter(filename,true);
            fw.close();
            this.filename = filename;
            this.dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    @Override
    public void agregarLlamado(Cliente cliente, int nroBox, Date fechaYHora) throws LogException{
        try {
            FileWriter fw = new FileWriter(filename,true);
            PrintWriter writer = new PrintWriter(fw);
            String log = "Cliente: " + cliente.getDni();
            log += "\nPuesto de atención: Box " + nroBox;
            log += "\nFecha y hora de atención: " + dateFormat.format(fechaYHora);
            log += "\n*****\n";
            fw.append(log);
            fw.close();
        } catch (IOException e) {
            throw new LogException();
        }
    }
}

package org.grupo10.factory.json;

import org.grupo10.modelo.Turno;
import org.grupo10.factory.IRegistro;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JsonRegistro implements IRegistro {

    public void logToFile(Turno turno, LocalDate date) {
        JSONParser parser = new JSONParser();
        JSONObject logEntry = new JSONObject();
        logEntry.put("cliente", turno.getDni());

        logEntry.put("date", date.toString());

        File file = new File("logRegistro.json");
        JSONObject logFile;
        JSONArray logsArray;

        if (file.exists()) {
            // Leer el archivo JSON existente
            try (FileReader reader = new FileReader(file)) {
                logFile = (JSONObject) parser.parse(reader);
                logsArray = (JSONArray) logFile.get("logs");
                if (logsArray == null) {
                    logsArray = new JSONArray();
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
                return;
            }
        } else {
            // Crear un nuevo archivo JSON
            logFile = new JSONObject();
            logsArray = new JSONArray();
            logFile.put("logs", logsArray);
        }

        // Agregar el nuevo log al array
        logsArray.add(logEntry);

        // Escribir el archivo JSON actualizado
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(logFile.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

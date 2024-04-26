package org.grupo10.repository;

import org.grupo10.exception.ClienteNoExistenteException;
import org.grupo10.modelo.Cliente;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class RepoClientesTXT implements IRepoClientes {

    private String filename;

    public RepoClientesTXT(String filename) throws FileNotFoundException{
        try {
            FileReader fr = new FileReader(filename);
            fr.close();
            this.filename = filename;
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    // Asume que el archivo está formateado correctamente.
    // Una linea para el nombre y apellido seguida de otra para el DNI y la categoría, separados por un espacio.
    @Override
    public Cliente getCliente(String dniFormateado) throws ClienteNoExistenteException, FileNotFoundException{
        Cliente retorno = null;
        try {
            FileReader fr = new FileReader(this.filename);
            BufferedReader br = new BufferedReader(fr);
            StringTokenizer tokens;
            boolean encontro = false;
            String nombre, dni;
            int cat;
            while (!encontro && (nombre = br.readLine()) != null) { // Lee el nombre y apellido
                tokens = new StringTokenizer(br.readLine(), " "); // Lee la linea con el DNI y la categoría
                dni = tokens.nextToken();
                if (dni.equals(dniFormateado)) {
                    encontro = true;

                    retorno = new Cliente(dni,nombre);
                }
            }
            if (!encontro)
                throw new ClienteNoExistenteException();
            fr.close();
        } catch (IOException e) {
            throw new FileNotFoundException("Hubo un error al leer el repositorio de clientes.");
        }
        return retorno;
    }
}

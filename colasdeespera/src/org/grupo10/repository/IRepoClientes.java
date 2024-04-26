package org.grupo10.repository;

import org.grupo10.exception.ClienteNoExistenteException;
import org.grupo10.modelo.Cliente;

import java.io.FileNotFoundException;

public interface IRepoClientes {
    Cliente getCliente(String dniFormateado) throws ClienteNoExistenteException, FileNotFoundException;
}

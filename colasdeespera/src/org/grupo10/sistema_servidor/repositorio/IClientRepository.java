package org.grupo10.sistema_servidor.repositorio;

import org.grupo10.exception.ClienteNoExistenteException;
import org.grupo10.modelo.Cliente;

import java.io.FileNotFoundException;

public interface IClientRepository {
    Cliente getCliente(String dni) throws ClienteNoExistenteException, FileNotFoundException;
}

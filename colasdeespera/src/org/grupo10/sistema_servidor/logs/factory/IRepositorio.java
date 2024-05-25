package org.grupo10.sistema_servidor.logs.factory;

import org.grupo10.exception.ClienteNoExistenteException;
import org.grupo10.modelo.Cliente;


import java.io.FileNotFoundException;


public interface IRepositorio {
    void readRepo(String filename) throws FileNotFoundException ;
    Cliente getCliente(String dni) throws ClienteNoExistenteException, FileNotFoundException;
}

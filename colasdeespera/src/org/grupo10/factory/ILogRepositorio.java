package org.grupo10.factory;

import org.grupo10.exception.ClienteNoExistenteException;
import org.grupo10.modelo.Cliente;


import java.io.FileNotFoundException;


public interface ILogRepositorio {
    void readRepo( ) throws FileNotFoundException ;
    Cliente getCliente(String dni) throws ClienteNoExistenteException, FileNotFoundException;
}

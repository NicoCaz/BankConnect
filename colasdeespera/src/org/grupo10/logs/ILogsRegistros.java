package org.grupo10.logs;

import org.grupo10.exception.LogException;
import org.grupo10.modelo.Cliente;

import java.util.Date;

public interface ILogsRegistros {
    void agregarRegistro(Cliente cliente, Date fechaYHora) throws LogException;
}

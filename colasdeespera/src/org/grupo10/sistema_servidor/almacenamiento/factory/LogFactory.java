package org.grupo10.sistema_servidor.almacenamiento.factory;

import org.grupo10.sistema_servidor.almacenamiento.Log;

public interface LogFactory {
    Log createLogRegistro();
    Log createLogLlamado();
}

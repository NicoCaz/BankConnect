package org.grupo10.sistema_estadistica.controlador;

import org.grupo10.exception.EstadisticaException;
import org.grupo10.modelo.dto.EstadisticaDTO;

import java.io.IOException;

public interface IEstadisticas {
    EstadisticaDTO resiboEstadistica() throws IOException, ClassNotFoundException, EstadisticaException;
}

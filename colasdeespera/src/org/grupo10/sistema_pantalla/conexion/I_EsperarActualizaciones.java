package org.grupo10.sistema_pantalla.conexion;

import java.io.IOException;
import java.util.Map;

public interface I_EsperarActualizaciones {
    void esperarActualizaciones() throws IOException;
    void conectar(Map.Entry<String, Integer> entry) throws IOException;
    void reconectar() throws IOException;

}

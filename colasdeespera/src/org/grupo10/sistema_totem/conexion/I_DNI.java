package org.grupo10.sistema_totem.conexion;

import java.io.IOException;
import java.util.Map;

public interface I_DNI {
    String enviarDNIRegistro(String dni) throws IOException;
    void conectar(Map.Entry<String, Integer> entry) throws IOException;
    void reconectar() throws IOException;
}

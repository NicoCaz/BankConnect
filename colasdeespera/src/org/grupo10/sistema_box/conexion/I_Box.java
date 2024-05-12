package org.grupo10.sistema_box.conexion;

import org.grupo10.exception.BoxException;

import java.io.IOException;
import java.util.Map;

public interface I_Box {
    String recibirDNILlamado() throws IOException, BoxException;
    void conectar(Map.Entry<String, Integer> entry) throws IOException, BoxException;
    void reconectar() throws IOException , BoxException;


}

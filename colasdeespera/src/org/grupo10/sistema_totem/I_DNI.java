package org.grupo10.sistema_totem;

import java.io.IOException;

public interface I_DNI {
    void enviarDNI(Object t);
    void conectar() throws IOException;
    void reconectar() throws IOException;
}

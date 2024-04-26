package org.grupo10.sistema_servidor;

import java.io.IOException;

public interface IRedundancia {
    void enviarPulso() throws IOException;
    void enviarEstado() throws IOException;

}

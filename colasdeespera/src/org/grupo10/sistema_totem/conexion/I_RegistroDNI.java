package org.grupo10.sistema_totem.conexion;

import java.io.IOException;

public interface I_RegistroDNI {
    String enviarDNIRegistro(String dni) throws IOException, ClassNotFoundException;

}

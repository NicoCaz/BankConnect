package org.grupo10.modelo;
public interface IClienteServer {
     void conectar(String host, int puerto);
     void enviarDatos(Object t);

}

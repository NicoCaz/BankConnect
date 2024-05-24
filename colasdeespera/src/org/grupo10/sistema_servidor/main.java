package org.grupo10.sistema_servidor;

import org.grupo10.sistema_servidor.repositorio.ClientRepositoryTXT;
import org.grupo10.sistema_servidor.repositorio.IClientRepository;

import java.io.FileNotFoundException;

public class main {
    public static void main(String[] args) {
        Prueba p= new Prueba();
        p.leerRepo();
    }



 static class Prueba{
     private IClientRepository repoClientes;
     public void leerRepo() {
         String currentDir = System.getProperty("user.dir");
         try {
             this.repoClientes = new ClientRepositoryTXT(currentDir+"/colasdeespera/src/org/grupo10/sistema_servidor/repo.txt");
         } catch (FileNotFoundException e) {
             System.out.println("Hubo un error al leer el repositorio de clientes. Verificar que el archivo no esté dañado.");
             // No tiene sentido utilizar el servidor sin clientes.
             System.exit(0);
         }
     }
 }

}

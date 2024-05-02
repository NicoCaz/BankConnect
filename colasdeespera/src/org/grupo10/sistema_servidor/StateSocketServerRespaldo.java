package org.grupo10.sistema_servidor;

import org.grupo10.modelo.Fila;
import org.grupo10.modelo.FilaFinalizada;
import org.grupo10.modelo.dto.FilasDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class StateSocketServerRespaldo implements IStateServidor{
    private ControladorServidor servidor;
    private ObjectInputStream inOtro;

    private Fila turnosEnEspera ;
    private FilaFinalizada turnosFinalizados ;

    public StateSocketServerRespaldo(ControladorServidor servidor) throws IOException {
        this.servidor = servidor;
        String ipOtro = this.servidor.getIpOtro();
        int portOtro = this.servidor.getPortOtro();
        Socket socketOtro = new Socket(ipOtro, portOtro);
        socketOtro.setSoTimeout(5100); // Heartbeat de 5 segundos. Tolerancia de 0.1 segundos.

        PrintWriter out = new PrintWriter(socketOtro.getOutputStream(), true);
        out.println("SERVIDOR");
        this.inOtro = new ObjectInputStream(socketOtro.getInputStream());
        System.out.println("Se encontró un servidor en " + ipOtro + ":" + portOtro + ". Iniciando como backup...");
    }
    @Override
    public void esperar() {
        long before = System.currentTimeMillis();
        try {
            while (true) { // Espera pulsos/actualizaciones
                Object o = this.inOtro.readObject();
                if (o.getClass() == String.class) { // Se recibió un pulso
                    System.out.println("Se recibió un pulso del servidor principal.");
                } else { // Se recibió la fila
                    FilasDTO filasDTO = (FilasDTO) o;
                    this.turnosEnEspera = filasDTO.getEspera();
                    this.turnosFinalizados = filasDTO.getFinalizada();
                    System.out.println("Se actualizó la fila.");
                }
                before = System.currentTimeMillis();
            }
        } catch (SocketTimeoutException e) {
            // Pasaron más de 5 segundos. El SV primario no envió pulso.
            System.out.println("No se recibió un pulso en los ultimos " + (System.currentTimeMillis() - before) + " milisegundos.");
            this.cambiarEstado();
        } catch (IOException e) {
            // Hubo un error de conexión con el servidor primario.
            System.out.println("Hubo un error de conexión con el servidor primario.");
            this.cambiarEstado();
        } catch (ClassNotFoundException e) {
            // Nunca pasa.
            e.printStackTrace();
        }
    }

    private void cambiarEstado() {
        System.out.println("Cambiando a modo primario...");  // CAMBIO DE SV SECUNDARIO A PRIMARIO
        try {
            this.servidor.setEstado(new StateSocketServerPrimario(this.servidor,this.turnosEnEspera,this.turnosFinalizados));
        } catch (IOException e) {
            System.out.println("No se pudo adoptar el modo primario. Cerrando...");
            System.exit(0);
        }
    }


}
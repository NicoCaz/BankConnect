package org.grupo10.sistema_servidor;

import org.grupo10.modelo.IFilas;
import org.grupo10.modelo.Turno;
import org.grupo10.modelo.TurnoFinalizado;
import org.grupo10.modelo.dto.FilasDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;

public class ServidorSecundarioState implements ServidorState{

    private ControladorServidor servidor;

    private ObjectInputStream inOtro;
    private IFilas<Turno> turnosEnEspera ;
    private IFilas<TurnoFinalizado> turnosFinalizados ;
    public HashSet<Integer> boxesOcupados = new HashSet<>();



    public ServidorSecundarioState(ControladorServidor controladorServidor) throws IOException {
        this.servidor = controladorServidor;
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
    public void esperarConexiones() {
        esperarPulsos();
    }

    @Override
    public void cambiarEstado() {
        System.out.println("Cambiando a modo primario...");  // CAMBIO DE SV SECUNDARIO A PRIMARIO
        try {
            this.servidor.setEstado(new ServidorPrincipalState(this.servidor,this.turnosEnEspera,this.turnosFinalizados));
        } catch (IOException e) {
            System.out.println("No se pudo adoptar el modo primario. Cerrando...");
            System.exit(0);
        }
    }

    private void esperarPulsos() {
        long before = System.currentTimeMillis();
        try {
            while (true) { // Espera pulsos/actualizaciones
                Object o = this.inOtro.readObject();
                if (o.getClass() == String.class) { // Se recibió un pulso
                    System.out.println("Se recibió un pulso del servidor principal.");
                } else { // Se recibió la fila
                    FilasDTO filasDTO=(FilasDTO) o;
                    this.turnosEnEspera = filasDTO.getEspera();
                    this.turnosFinalizados=filasDTO.getFinalizada();
                    System.out.println("Se actualizó la fila.");
                }
                before = System.currentTimeMillis();
            }
        } catch (SocketTimeoutException e) {
            // Pasaron más de 5 segundos. El SV primario no envió pulso.
            System.out.println("No se recibió un pulso en los ultimos " + (System.currentTimeMillis() - before) + " milisegundos.");
            System.out.println("Cambiando a modo primario...");  // CAMBIO DE SV SECUNDARIO A PRIMARIO
            this.cambiarEstado();
        } catch (IOException e) {
            // ERROR DE CONEXION CON EL SV PRIMARIO
            System.out.println("Hubo un error de conexión con el servidor primario.");
            System.out.println("Cambiando a modo primario..."); // CAMBIO DE SV SECUNDARIO A PRIMARIO
            this.cambiarEstado();
        } catch (ClassNotFoundException e) {
            // No se encontró la clase del objeto enviado (nunca pasa)
            e.printStackTrace();
        }
    }
}

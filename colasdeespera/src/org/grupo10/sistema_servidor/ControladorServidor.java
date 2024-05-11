package org.grupo10.sistema_servidor;

import org.grupo10.modelo.Fila;
import org.grupo10.modelo.FilaFinalizada;
import org.grupo10.modelo.Turno;
import org.grupo10.modelo.TurnoFinalizado;
import org.grupo10.modelo.dto.FilasDTO;
import org.grupo10.sistema_servidor.manejoClientes.BoxClientHandler;
import org.grupo10.sistema_servidor.manejoClientes.RedundanciaHandler;
import org.grupo10.sistema_servidor.manejoClientes.TotemClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class ControladorServidor extends Thread {
    private Fila turnosEnEspera ;
    private FilaFinalizada turnosFinalizados ;

    private static ControladorServidor instance = null;

    // Parámetros del archivo de configuración
    private ServerSocket serverSocket;
    private boolean primario;
    private String ip,ipOtro;
    private int port, portOtro;
    private ObjectInputStream inOtro;
    //
    public HashSet<Integer> boxesOcupados = new HashSet<>();
    private List<TotemClientHandler> Totems = new ArrayList<>();
    private List<BoxClientHandler> boxClients = new ArrayList<>();
    private List<Socket> EstadisticaClients = new ArrayList<>();
    private List<Socket> PantallasClients = new ArrayList<>();
    private boolean cambios;

    public static void main(String[] args) {
        ControladorServidor.getInstance();
    }
    public static ControladorServidor getInstance() {
        if (ControladorServidor.instance == null)
            ControladorServidor.instance = new ControladorServidor();
        return ControladorServidor.instance;
    }

    private ControladorServidor() {
        this.cambios = true;
        this.turnosEnEspera = new Fila();
        this.turnosFinalizados =  new FilaFinalizada();
        // Lectura de archivo de configuración
        String currentDir = System.getProperty("user.dir");

        String archivoTxt = currentDir + "/colasdeespera/src/org/grupo10/sistema_servidor/serverconfig.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoTxt))) {
            String linea;

            //Leo el Servidor Principal
            linea = br.readLine();
            String[] partes = linea.split(":");
            ip = partes[0];
            port = Integer.parseInt(partes[1]);

            //Leo el Servidor de Respaldo
            linea = br.readLine();
            partes = linea.split(":");
            ipOtro = partes[0];
            portOtro = Integer.parseInt(partes[1]);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        // Trata de conectarse al otro servidor para ver si es primario o secundario
        try {
            System.out.println("Intentando conectar al servidor " + ipOtro + ":" + portOtro + ".");
            this.setSecundario();
            this.start();
            // Encontró servidor primario
        } catch (IOException e) { // No encontró servidor primario
            System.out.println("No se encontró el servidor en " + this.ipOtro + ":" + this.portOtro + ".");
            this.setPrimario();
            if (this.serverSocket != null)
                this.start();
        }
    }

    private void setPrimario() {
        this.primario = true;
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Se inició el servidor primario en " + ip + " : " + this.port + ".");
        } catch (IOException e) {
            System.out.println("No se pudo abrir el servidor primario en el puerto " + this.port + ".");
        }
    }

    private void setSecundario() throws IOException {
        this.primario = false; // Se inicializa como secundario
        Socket socketOtro = new Socket(this.ipOtro, this.portOtro);
        socketOtro.setSoTimeout(5100); // Heartbeat de 5 segundos. Tolerancia de 0.1 segundos.

        PrintWriter out = new PrintWriter(socketOtro.getOutputStream(), true);
        out.println("SERVIDOR");

        this.inOtro = new ObjectInputStream(socketOtro.getInputStream());
        System.out.println("Se encontró un servidor en " + this.ipOtro + ":" + this.portOtro + ". Iniciando como backup...");
    }

    @Override
    public void run() {
        while (true) {
            if (this.primario) { // Modo primario
                this.esperarConexiones();
            } else { // Modo secundario
                this.esperarPulsos();
            }
        }
    }

    private void esperarConexiones() {
        try {
            Socket socket = this.serverSocket.accept();
            new Thread(() -> { // En otro thread para no interferir con la conexión de nuevas terminales
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg = in.readLine(); // Recibe identificación
                    System.out.println("INTENTO DE CONEXION DE: " + msg);
                    if ("Box".equals(msg)) {
                        System.out.println("BOX ENCONTRADO");
                        BoxClientHandler b=new BoxClientHandler(socket);
                        boxClients.add(b);
                        b.start();
                        System.out.println("Box arranco");
                    } else if("Totem".equals(msg)) {
                        TotemClientHandler t = new TotemClientHandler(socket);
                        Totems.add(t);
                        t.start();
                        System.out.println("Totem arranco");

                    } else if("ESTADISTICA".equals(msg)) {

                        EstadisticaClients.add(socket);
                        System.out.println("Estadistica arranco");
                    } else if("Pantalla".equals(msg)) {
                        PantallasClients.add(socket);
                        System.out.println("Pantalla arranco");
                    } else if("SERVIDOR".equals(msg)) {
                        new RedundanciaHandler(socket).start();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
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
            this.setPrimario();
        } catch (IOException e) {
            // ERROR DE CONEXION CON EL SV PRIMARIO
            System.out.println("Hubo un error de conexión con el servidor primario.");
            System.out.println("Cambiando a modo primario..."); // CAMBIO DE SV SECUNDARIO A PRIMARIO
            this.setPrimario();
        } catch (ClassNotFoundException e) {
            // No se encontró la clase del objeto enviado (nunca pasa)
            e.printStackTrace();
        }
    }



    public synchronized void enviarActualizacion(Turno t) {
        for (Socket socket : this.PantallasClients) {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println(t.getDni()+","+t.getBox());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                this.PantallasClients.remove(socket);
                System.out.println(
                        "Se desconectó la pantalla de espera con IP " + socket.getInetAddress().getHostAddress());
            }
        }
    }


    public synchronized void enviarEstadisticas() throws IOException {
        int cantEspera=turnosEnEspera.cantidadEspera();
        int cantAtendidos=turnosFinalizados.cantidadFinalizada();
        long  tiempoPromedio = 0;

        if(!turnosFinalizados.estaVacia()) {
            Iterator<TurnoFinalizado> iterator = turnosFinalizados.getTurnos().iterator();
            TurnoFinalizado turno = iterator.next();
            while (iterator.hasNext()) {
                tiempoPromedio += Math.abs(turno.getHorarioSalida().getTime() - turno.getT().getHorarioEntrada().getTime());
                turno = iterator.next();
            }

            tiempoPromedio = (tiempoPromedio / cantAtendidos);
        }


        String aux= cantEspera + "," + cantAtendidos + "," + tiempoPromedio;

        for (Socket socket : this.EstadisticaClients) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(aux);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public FilaFinalizada getTurnosFinalizados() {
        return turnosFinalizados;
    }

    public void setTurnosFinalizados(FilaFinalizada turnosFinalizados) {
        this.turnosFinalizados = turnosFinalizados;
    }

    public Fila getTurnosEnEspera() {
        return turnosEnEspera;
    }

    public void setTurnosEnEspera(Fila turnosEnEspera) {
        this.turnosEnEspera = turnosEnEspera;
    }

    public int getPort() {
        return this.port;
    }

    public String getIpOtro() {
        return this.ipOtro;
    }

    public int getPortOtro() {
        return this.portOtro;
    }
    public boolean hayCambios() {
        return this.cambios;
    }

    public void setCambios(boolean b) {
        this.cambios = b;
    }


    public void quitarBox(Integer box){
        boxesOcupados.remove(box);
        System.out.println("El elimino el box: " +box);
    }


}

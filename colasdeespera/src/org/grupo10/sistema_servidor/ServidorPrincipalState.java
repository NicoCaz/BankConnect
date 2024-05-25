package org.grupo10.sistema_servidor;

import org.grupo10.modelo.Turno;
import org.grupo10.modelo.TurnoFinalizado;
import org.grupo10.sistema_servidor.filas.Fila;
import org.grupo10.sistema_servidor.filas.FilaFinalizada;
import org.grupo10.sistema_servidor.filas.IFilas;
import org.grupo10.sistema_servidor.manejoThreads.BoxClientHandler;
import org.grupo10.sistema_servidor.manejoThreads.RedundanciaHandler;
import org.grupo10.sistema_servidor.manejoThreads.TotemClientHandler;
import org.grupo10.sistema_servidor.repositorio.ClientRepositoryTXT;
import org.grupo10.sistema_servidor.repositorio.IClientRepository;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ServidorPrincipalState implements ServidorState{
    private ControladorServidor servidor;
    private ServerSocket serverSocket;

    //Declaracion de filas y boxes ocupados
    private IFilas<Turno> turnosEnEspera ;
    private IFilas<TurnoFinalizado> turnosFinalizados ;
    private HashSet<Integer> boxesOcupados = new HashSet<>();

    private IClientRepository repoClientes;
    //Manejo de logs
    //private LogCreator logCreator;

    //Instanciacion de las listas que guardan las referencias a los threads
    private List<TotemClientHandler> Totems = new ArrayList<>();
    private List<BoxClientHandler> boxClients = new ArrayList<>();
    private List<Socket> EstadisticaClients = new ArrayList<>();
    private List<Socket> PantallasClients = new ArrayList<>();
    private boolean cambios;

    public ServidorPrincipalState(ControladorServidor controladorServidor) throws IOException {
        this.servidor = controladorServidor;
        int port = this.servidor.getPort();
        this.serverSocket = new ServerSocket(port);
        this.turnosEnEspera = new Fila(this.servidor.getEstrategiaFila());
        this.turnosFinalizados = new FilaFinalizada();
        //this.logCreator= new LogCreator(this.servidor.getTipoLog());
        this.leerRepo();
        this.cambios = true;
        this.turnosEnEspera = new Fila(this.servidor.getEstrategiaFila());
        System.out.println("Se inició el servidor principal en el puerto " + port + ".");
    }

    public ServidorPrincipalState(ControladorServidor servidor, IFilas<Turno> turnos, IFilas<TurnoFinalizado> finalizados) throws IOException{
        this(servidor);
        this.turnosEnEspera = turnos;
        this.turnosFinalizados = finalizados;

    }

    @Override
    public void esperarConexiones() {
        try {
            Socket socket = this.serverSocket.accept();
            new Thread(() -> { // En otro thread para no interferir con la conexión de nuevas terminales
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg = in.readLine(); // Recibe identificación
                    System.out.println("INTENTO DE CONEXION DE: " + msg);
                    if ("Box".equals(msg)) {
                        System.out.println("BOX ENCONTRADO");
                        BoxClientHandler b=new BoxClientHandler(socket,this);
                        boxClients.add(b);
                        b.start();
                        System.out.println("Box arranco");
                    } else if("Totem".equals(msg)) {
                        TotemClientHandler t = new TotemClientHandler(socket,this);
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
                        new RedundanciaHandler(socket,this).start();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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



    @Override
    public void cambiarEstado() {

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
        int cantEspera=turnosEnEspera.cantidad();
        int cantAtendidos=turnosFinalizados.cantidad();
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

    public IClientRepository getRepoClientes() {
        return repoClientes;
    }

    public FilaFinalizada getTurnosFinalizados() {
        return (FilaFinalizada) turnosFinalizados;
    }

    public Fila getTurnosEnEspera() {
        return (Fila) turnosEnEspera;
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

    public boolean agregarBox(Integer box){
        return this.boxesOcupados.add(box);
    }

  //  public LogCreator getLogCreator() {
    //    return logCreator;
    //}
}

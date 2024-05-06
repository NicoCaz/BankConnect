package org.grupo10.sistema_servidor;

import org.grupo10.exception.ClienteRepetidoException;
import org.grupo10.logs.LogLlamadosTXT;
import org.grupo10.logs.LogRegistrosTXT;
import org.grupo10.modelo.Fila;
import org.grupo10.modelo.FilaFinalizada;
import org.grupo10.modelo.Turno;
import org.grupo10.modelo.dto.EstadisticaDTO;
import org.grupo10.modelo.TurnoFinalizado;
import org.grupo10.repository.RepoClientesTXT;
import org.grupo10.sistema_servidor.manejoClientes.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StateSocketServerPrimario implements IStateServidor{
    private static final int PORT = 8080;
    private ControladorServidor servidor;
    private ServerSocket serverSocket;
    private LogLlamadosTXT logLlamados;
    private LogRegistrosTXT logRegistros;
    private RepoClientesTXT repoClientes;
    private boolean cambios;

    private List<TotemClientHandler> Totems = new ArrayList<>();
    private List<BoxClientHandler> boxClients = new ArrayList<>();
    private List<EstadisticaClientHandler> EstadisticaClients = new ArrayList<>();
    private List<PantallaClientHandler> PantallasClients = new ArrayList<>();
    private int idBoxs=0;
    private int idPantallas=0;
    private int idEstadisticas=0;
    private int idTotems=0;
    //////////////////////////////////////////////////////////////////////////////////////////
    private Fila turnosEnEspera ;
    private FilaFinalizada turnosFinalizados ;


    public StateSocketServerPrimario(ControladorServidor servidor) throws IOException {
        this.servidor = servidor;
        int port = this.servidor.getPort();
        this.serverSocket = new ServerSocket(port);
        this.leerRepo();
        this.abrirLogs();
        this.cambios = true;
        this.turnosEnEspera = new Fila();
        this.turnosFinalizados =  new FilaFinalizada();
        System.out.println("Se inició el servidor primario en el puerto " + port + ".");
    }


    public StateSocketServerPrimario(ControladorServidor servidor, Fila turnosEnEspera,FilaFinalizada turnosFinalizados) throws IOException{
        this(servidor);
        this.turnosEnEspera = turnosEnEspera;
        this.turnosFinalizados=turnosFinalizados;
    }
    @Override
    public void esperar() {
        try {
            //ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en el puerto " + this.serverSocket.getLocalPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nueva conexión entrante");
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                Object var =inputStream.readObject();
                BasicClientHandler clientHandler = null;
                System.out.println(var);
                if ("Box".equals(var)) {
                    clientHandler = new BoxClientHandler(clientSocket, this, inputStream, outputStream,++this.idBoxs);
                    boxClients.add((BoxClientHandler) clientHandler);
                } else if("Totem".equals(var)) {
                    clientHandler = new TotemClientHandler(clientSocket, this, inputStream, outputStream,++this.idTotems);
                    Totems.add((TotemClientHandler) clientHandler);
                } else if("Estadistica".equals(var)) {
                    clientHandler = new EstadisticaClientHandler(clientSocket, this, inputStream, outputStream,++this.idEstadisticas);
                    EstadisticaClients.add((EstadisticaClientHandler) clientHandler);
                } else if("Pantalla".equals(var)) {
                    clientHandler = new PantallaClientHandler(clientSocket, this, inputStream, outputStream,++this.idPantallas);
                    PantallasClients.add((PantallaClientHandler) clientHandler);
                }
                clientHandler.start();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void leerRepo() {
        try {
            this.repoClientes = new RepoClientesTXT("repo.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Hubo un error al leer el repositorio de clientes. Verificar que el archivo no esté dañado.");
            // No tiene sentido utilizar el servidor sin clientes.
            System.exit(0);
        }
    }

    public void abrirLogs() {
        // Asignación de archivos de log
        try {
            this.logLlamados = new LogLlamadosTXT("logLlamados.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Hubo un error al crear/leer el historial de llamados.");
        }
        try {
            this.logRegistros = new LogRegistrosTXT("logRegistros.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Hubo un error al crear/leer el historial de registros.");
        }
    }
    public boolean getCambios() {
        return this.cambios;
    }

    public synchronized void setCambios(boolean b) {
        this.cambios = b;
    }


    public void respuesta(Object res,BasicClientHandler clientHandler) {


    }

    public synchronized void siguienteTurno(Turno res,BoxClientHandler client){
        if(res instanceof Turno){
            client.sendObject(res);
            Iterator<PantallaClientHandler> iterador = this.PantallasClients.iterator();
            while (iterador.hasNext()) {
                PantallaClientHandler pantalla = iterador.next();
                pantalla.sendObject(res);
            }
            TurnoFinalizado turnofinalizado = new TurnoFinalizado(res);
            turnofinalizado.setHorarioSalida(new Date());
            this.turnosFinalizados.agregarTurno(turnofinalizado);
        }else{
            client.sendObject(res);
        }


    }
    public synchronized void calculoEstadistica(EstadisticaClientHandler client){
        int cantEspera=cantidadEnEspera();
        int cantAtendidos=cantidadFinalizado();
        double  tiempoPromedio = 0;
        Iterator<TurnoFinalizado> iterator= this.turnosFinalizados.getTurnos().iterator();

        while(iterator.hasNext()) {
            TurnoFinalizado turno= iterator.next();
            tiempoPromedio+=Math.abs(turno.getHorarioSalida().getTime()-turno.getT().getHorarioEntrada().getTime());
        }

        tiempoPromedio=(tiempoPromedio/cantAtendidos);
        //tiempoPromedio= TimeUnit.SECONDS.convert(tiempoPromedio, TimeUnit.MILLISECONDS) % 60;

        EstadisticaDTO res = new EstadisticaDTO(cantEspera,cantAtendidos,tiempoPromedio);


        //mando a todos los hilos de estadisticas el objeto con todos los calculos obtenidos
        Iterator<EstadisticaClientHandler> estadisticasClients = this.EstadisticaClients.iterator();
        while (estadisticasClients.hasNext()) {
            EstadisticaClientHandler aux = estadisticasClients.next();
            aux.sendObject(res);
        }

    }



    public void finalizarTurno(TurnoFinalizado finalizadoDTO){
        this.turnosFinalizados.agregarTurno(finalizadoDTO);
    }

    public Turno getUltimoTurno(BoxClientHandler box){

        if(turnosEnEspera.cantidadEspera()>0){
            Turno ultimoturno = this.turnosEnEspera.sacarTurno();
            ultimoturno.setBox(box.getID());
            //mandarTurnoPantallas(ultimoturno);
            System.out.println("vuelvo de llamar a las pantalias " + ultimoturno);
            return ultimoturno;
        }else{
            return null;
        }

    }

    public Fila getTurnosEnEspera() {
        return this.turnosEnEspera;
    }
    public FilaFinalizada getTurnosAtendidos() {
        return this.turnosFinalizados;
    }

    public void addTurnosEnEspera(Turno turnosEnEspera) throws ClienteRepetidoException {
        this.turnosEnEspera.agregarTurno(turnosEnEspera);
    }

    public int cantidadEnEspera(){
        return this.turnosEnEspera.cantidadEspera();
    }
    public int cantidadFinalizado(){
        return this.turnosFinalizados.cantidadFinalizada();
    }

}
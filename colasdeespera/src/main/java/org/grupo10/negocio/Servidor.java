package org.grupo10.negocio;

import org.grupo10.modelo.Cliente;
import org.grupo10.modelo.IClienteServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Servidor extends Observable implements Runnable{

    public static Servidor instancia;
    private Socket socket;
    private ServerSocket socketServer;
    private static int boxes=0;
    private String pre="[SERVER]";
    private Queue<Cliente> clientes = new LinkedList<>();
    private ArrayList<Socket> sockets = new ArrayList<Socket>();

    private ArrayList<Socket> monitores = new ArrayList<Socket>();



    public static Servidor getInstancia() {
        if (instancia == null) {
            instancia = new Servidor();
        }
        return instancia;
    }


    public ArrayList<Socket> getMonitores() {
        return this.monitores;
    }


    public ArrayList<Socket> getSockets() {
        return this.sockets;
    }

    public Queue<Cliente> getClientes() {
        return this.clientes;
    }


    public void enviarQueue() {        //este metodo envia a todos los sockets conectados la queue de clientes
        Iterator<Socket> iterador = this.sockets.iterator();
        while (iterador.hasNext()) {
            Socket aux = iterador.next();
            try {
                ObjectOutputStream flujo = new ObjectOutputStream(aux.getOutputStream());
                System.out.println("Enviando queue al socket de puerto "+ aux.getPort());
                flujo.writeObject(this.clientes);
                flujo.flush();
            } catch (IOException e) {
                System.out.println("Excepcion enviando queues: "+ e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try {
            int puerto = 1; //hardcodeado por ahora
            this.socketServer = new ServerSocket(puerto);
            System.out.println(pre+"Servidor iniciado. Puerto: " + puerto);
            while (true) {
                socket = socketServer.accept();
                System.out.println(pre+"Ha entrado una conexion al servidor");
                this.setChanged();
                this.notifyObservers("Alta");
                this.sockets.add(socket);

                Thread escucha = new Thread(new Escuchar(socket));   //porque cada socket debe tener un hilo propio escuchando e/s de datos
                //, ademas del hilo original del server que escucha conexiones entrantes

                //solo puede haber un solo flujo de entrada y salida para cada socket así que puse los ObjectInput/OutputStream
                //en el hilo de la clase Escuchar que envía y recibe datos

                escucha.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void enviarBox(Socket socket) {
        try {
            ObjectOutputStream flujo = new ObjectOutputStream(socket.getOutputStream());
            flujo.writeObject(++this.boxes);
        } catch (IOException e) {
            System.out.println(pre+"Exception enviando el box actual "+ e.toString());
        }
    }


    public void enviarBoxMonitores(int box, String DNISig) { //envío a todos los monitores el box que hizo el request de siguiente
        Iterator<Socket> iterador = this.monitores.iterator();
        while (iterador.hasNext()) {
            Socket aux = iterador.next();
            try {
                ObjectOutputStream flujo = new ObjectOutputStream(aux.getOutputStream());
                System.out.println(pre+"Enviando queue al socket de MONITOR de puerto "+ aux.getPort());
                System.out.println(pre+"DNI que vamos a enviar al monitor: "+ DNISig);
              //  Datos datos = new Datos(box,DNISig); datos es la info de los turnos
///flujo.writeObject(datos);
                flujo.flush();
             //   System.out.println(pre+" Enviamos " + datos.toString() + " a los monitores!!!");
            } catch (IOException e) {
                System.out.println(pre+"Excepcion enviando queues: "+ e.getMessage());
            }
        }
    }
    private class Escuchar implements Runnable { //seria el hilo de cada socket. puse la clase aca para q esté mas a mano
        private Socket socket;

        public Escuchar(Socket socket) {
            System.out.println(pre+"Creando clase escuchadora");
            this.socket = socket;
        }



        @Override
        public void run() {
            try {
                // Datos es gestion atencion 
                System.out.println(pre+"Clase escuchadora creada xd");
                ObjectInputStream flujoEntrada = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream());
                Servidor.getInstancia().enviarQueue();        //cuando uno nuevo se conecta, envio las queues nuevamente para que si ya habia empleados, se le carguen los datos también.
                //  Servidor.getInstancia().enviarBox(socket);    //envio el numero de box que le corresponde al empleado que acaba de conectarse
                while (true) {
                    System.out.println(pre+"Escuchando...........");
                    Object object = flujoEntrada.readObject();
                    if (object instanceof Datos) {
                        Datos datos = (Datos) object;
                        if (datos.isSiguiente()) { // implica que un empleado pidió para siguiente. enviar a los monitores quién fue
                            System.out.println(pre+"El server recibió DNI "+ datos.getDNISig() +" en una request para siguiente ");
                            Servidor.getInstancia().getClientes().poll();
                            Servidor.getInstancia().enviarQueue();
                            Servidor.getInstancia().enviarBoxMonitores(datos.getBox(),datos.getDNISig());

                        } else {


                        }

                    } else if (object instanceof Cliente) {        //es un registro
                        Cliente cliente = (Cliente) object;
                        System.out.println(pre+"El servidor recibió el DNI "+ cliente.getDNI());
                        Servidor.getInstancia().getClientes().add(cliente); //agrego al cliente a una coleccion de clientes
                        Servidor.getInstancia().enviarQueue(); //enviar la queue actualziada a todos los empleados
                    } else if (object instanceof Integer) {     //identificador de monitores
                        int x = (int) object;
                        if (x==2176) { //codigo que dan los monitores para identificarse
                            System.out.println(pre+"Se agrego un nuevo monitor al sistema");
                            Servidor.getInstancia().getMonitores().add(socket);
                        } else if (x==21) {
                            System.out.println(pre+"Se agregó un nuevo empleado al sistema");
                            Servidor.getInstancia().enviarBox(socket);    //envio el numero de box que le corresponde al empleado que acaba de conectarse
                        }


                    }

                }

            } catch (Exception e) {
                System.out.println(pre+"Excepcion "+ e.getMessage());

            }
        }
    }
}

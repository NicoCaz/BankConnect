package org.grupo10.sistema_box.conexion;

import org.grupo10.exception.BoxException;
import org.grupo10.interfaces.Conexion;
import org.grupo10.sistema_box.controlador.ControladorBox;

import java.io.*;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class SistemaBox extends Conexion implements I_LlamarDNI{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<Map.Entry<String, Integer>> servers = new ArrayList<>();
    private int serverActivo, nroBox;

    public SistemaBox() throws IOException , BoxException {
        super("/boxconfig.txt");
        this.nroBox = nroBox;

    }
    @Override
    public String llamarSiguiente() throws IOException, BoxException  {
        this.out.println("SIGUIENTE");
        try {
            return this.in.readLine(); // Recibe DNI del servidor
        } catch (IOException e) { // Hubo una falla. Reintenta / cambia de servidor.
            this.reconectar();
            this.out.println("SIGUIENTE");
            return this.in.readLine();
        }
    }

    @Override
    public void conectar(Map.Entry<String, Integer> entry) throws IOException, BoxException {
        this.socket = new Socket(entry.getKey(), entry.getValue());
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out.println("Box");

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Chequea si el número de box ya está en uso
        this.out.println(this.nroBox);
        String msg = this.in.readLine();

        if (msg.equals("OCUPADO"))
            throw new BoxException();
    }

    @Override
    protected void hookPrimerasLineas(BufferedReader br) throws IOException {
        String linea;
        linea = br.readLine();
        this.nroBox = Integer.parseInt(linea);
    }

    @Override
    protected void abrirMensajeConectando() {
        ControladorBox.getInstance().abrirMensajeConectando();
    }

    @Override
    protected void cerrarMensajeConectando() {
        ControladorBox.getInstance().cerrarMensajeConectando();
    }

}
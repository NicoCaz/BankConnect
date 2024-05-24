package org.grupo10.sistema_box.conexion;

import org.grupo10.interfaces.Conexion;
import org.grupo10.sistema_box.controlador.ControladorBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class SistemaBox extends Conexion implements I_LlamarDNI{
    private int nroBox;

    public SistemaBox() throws IOException {
        super("/boxconfig.txt");

    }
    @Override
    public String llamarSiguiente() throws IOException  {
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
    public void conectar(Map.Entry<String, Integer> entry) throws IOException {
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
            throw new IOException("El box " + nroBox + " esta ocupado");
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
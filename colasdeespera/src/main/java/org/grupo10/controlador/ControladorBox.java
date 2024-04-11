package org.grupo10.controlador;


import org.grupo10.modelo.IClienteServer;
import org.grupo10.vista.IVista;
import org.grupo10.vista.VistaBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorBox implements ActionListener, IClienteServer {
    private IVista vista;

    public ControladorBox() {
        this.vista = new VistaBox();
        this.vista.setActionListener(this);
        this.vista.mostrar();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        System.out.println(comando);
        if (comando.equalsIgnoreCase("Siguiente")) { //bien

        } else if (comando.equalsIgnoreCase(("FinalizarTurno"))){ //bien

        }
    }
//    @Override
//    public void conectarServer() {
//        SistemaEmpleados.getInstancia().conectar("localhost", 1); //puerto del server hardcodeado en 1
//        SistemaEmpleados.getInstancia().crearHilo();
//
//    }


    @Override
    public void conectar(String host, int puerto) {

    }

    @Override
    public void enviarDatos(Object t) {

    }
}

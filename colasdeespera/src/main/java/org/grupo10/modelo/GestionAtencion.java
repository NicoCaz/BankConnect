package org.grupo10.modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GestionAtencion {
    private Queue<ITurno> turnosPendientes;
    private Queue<ITurno> turnosAtendidos;
    private List<IBox> boxes= new ArrayList<IBox>();;

    public GestionAtencion(int cantidadBoxes){

        for (int i = 0; i < cantidadBoxes; i++) {
            IBox box=new Box(i+1);
            boxes.add(box);
        }
    }

    public void agregarTurno(ITurno turno){
        this.turnosPendientes.add(turno);
    }
    public void terminarTurno(ITurno turno){

        this.turnosAtendidos.add(turno);
    }
    public void atenderTurno(ITurno turno){
        this.turnosPendientes.remove(turno);
        //logica de atender turno
        //llamar a los box para que lo atiendan
    }
}

package org.grupo10.modelo;
import java.util.ArrayList;
import java.util.List;

public class GestionAtencion {
    private List<ITurno> turnosPendientes;
    private List<ITurno> turnosAtendidos;
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

    public void comenzarAtencion() {
        // aca invoca a todos los metodos de las otras clases para que se inicialice la atencion

    }
}

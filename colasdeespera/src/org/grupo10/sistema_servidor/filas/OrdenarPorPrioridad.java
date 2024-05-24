package org.grupo10.sistema_servidor.filas;

import org.grupo10.modelo.Turno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrdenarPorPrioridad implements IEstrategia{
    @Override
    public void ordenar(ArrayList<Turno> fila) {
        Collections.sort(fila,new ComparadorPrioridad());
    }

    private class ComparadorPrioridad implements Comparator<Turno> {
        @Override
        public int compare(Turno o1, Turno o2) {
            int prioridad1 = o1.getPrioridad();
            int prioridad2 = o2.getPrioridad();
            System.out.println(prioridad1 + " p1 - p2 " + prioridad2);
            // Comparar por prioridad
            if (prioridad1 < prioridad2) {
                System.out.println("ENTRO p1 < p2");
                return -1; // Prioridad más alta (menor valor) va primero
            } else if (prioridad1 > prioridad2) {
                System.out.println("ENTRO p1 > p2");
                return 1; // Prioridad más baja (mayor valor) va después
            } else {
                System.out.println("ENTRO FIFO");
                // Si tienen la misma prioridad, comparar por tiempo de llegada
                return (int) (o1.getHorarioEntrada().getTime() - o2.getHorarioEntrada().getTime());
            }
        }
    }
}

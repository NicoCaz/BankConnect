package org.grupo10.sistema_servidor.filas;

import org.grupo10.modelo.Turno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class OrdenarPorEtario implements IEstrategia{

    @Override
    public void ordenar(ArrayList<Turno> fila) {
        Collections.sort(fila,new ComparadorEtario());
    }

    private class ComparadorEtario implements Comparator<Turno> {
        @Override
        public int compare(Turno o1, Turno o2) {
            String grupoEtario1 = o1.getGrupoEtario();
            String grupoEtario2 = o2.getGrupoEtario();

            // Comparar por grupo etario
            if (grupoEtario1.equalsIgnoreCase("JOVEN")) {
                if (grupoEtario2.equalsIgnoreCase("ADULTO") || grupoEtario2.equalsIgnoreCase("ADULTO MAYOR")) {
                    return 1; // JOVEN tiene menor prioridad que ADULTO y ADULTO MAYOR
                }
            } else if (grupoEtario1.equalsIgnoreCase("ADULTO")) {
                if (grupoEtario2.equalsIgnoreCase("JOVEN")) {
                    return -1; // ADULTO tiene prioridad sobre JOVEN
                } else if (grupoEtario2.equalsIgnoreCase("ADULTO MAYOR")) {
                    return 1; // ADULTO tiene menor prioridad que ADULTO MAYOR
                }
            } else if (grupoEtario1.equalsIgnoreCase("ADULTO MAYOR")) {
                if (grupoEtario2.equalsIgnoreCase("JOVEN") || grupoEtario2.equalsIgnoreCase("ADULTO")) {
                    return -1; // ADULTO MAYOR tiene prioridad sobre JOVEN y ADULTO
                }
            }

            // Si los grupos etarios son iguales, comparar por tiempo de entrada
            return (int) (o1.getHorarioEntrada().getTime() - o2.getHorarioEntrada().getTime());
        }
    }
}

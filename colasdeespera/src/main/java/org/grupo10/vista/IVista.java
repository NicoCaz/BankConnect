package org.grupo10.vista;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface IVista {
    void cerrar();

    void mostrar();
    void ventanaConfirmacion(String msg);
    void ventanaError(String msg);

    void setActionListener(ActionListener actionListener);

    JLabel getDisplayLabel();
    StringBuilder getInputBuffer();

}

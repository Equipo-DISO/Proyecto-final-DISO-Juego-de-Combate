package com.utad.proyectoFinal.ui;

import java.util.ArrayList;
import java.util.List;

public class InterfaceMainExample {
    public static void main(String[] args) {
        MenuInterface interfaceMain = new MenuInterface();
        interfaceMain.showInterface();
        interfaceMain.waitTillClose();
        List<String> data = interfaceMain.getData();
        
        // Primer elemento de la lista es el jugador humano
        // Los siguientes elementos son los bots
        for (String element : data) {
            if(data.indexOf(element) == 0) System.out.println("Ruta de Imagen del Jugador: " + element + "\n");
            else if(data.indexOf(element) == 1) System.out.println("Nombre del Jugador: " + element + "\nLista de Bots: ");
            else System.out.println("#" + (data.indexOf(element) - 1) + ": " + element);
        }
    }
}

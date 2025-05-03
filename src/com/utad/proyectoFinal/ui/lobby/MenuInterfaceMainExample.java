package com.utad.proyectoFinal.ui.lobby;

import java.util.ArrayList;
import java.util.List;

import com.utad.proyectoFinal.mapa.MapGenerator;

public class MenuInterfaceMainExample {
    public static void main(String[] args) {
        MenuInterface interfaceMain = new MenuInterface();
        interfaceMain.showInterface();
        interfaceMain.waitTillClose();

        List<String> data = interfaceMain.getData();

        MapGenerator instance = MapGenerator.getInstance(1100, 0, 6, 4);
        instance.displayMap();

        
        
        // Primer elemento de la lista es el jugador humano
        // Los siguientes elementos son los bots
        for (String element : data) {
            if(data.indexOf(element) == 0) System.out.println("Ruta de Imagen del Jugador: " + element);
            else if(data.indexOf(element) == 1) System.out.println("Nombre del Jugador: " + element + "\nLista de Bots: ");
            else System.out.println("#" + (data.indexOf(element) - 1) + ": " + element);
        }
    }
}

package com.utad.proyectoFinal.ui.lobby;

import java.util.LinkedList;
import java.util.List;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseWeapon;
import com.utad.proyectoFinal.characterSystem.tools.items.HelmetType;
import com.utad.proyectoFinal.characterSystem.tools.items.WeaponType;
import com.utad.proyectoFinal.gameManagement.GameContext;
import com.utad.proyectoFinal.mapa.MapGenerator;

public class MenuInterfaceMainExample {
    public static void main(String[] args) {
        MenuInterface interfaceMain = new MenuInterface();
        interfaceMain.showInterface();
        interfaceMain.waitTillClose();

        List<String> data = interfaceMain.getData();
        
        // Primer elemento de la lista es el jugador humano
        // Los siguientes elementos son los bots
        for (String element : data) {
            if(data.indexOf(element) == 0) System.out.println("Ruta de Imagen del Jugador: " + element);
            else if(data.indexOf(element) == 1) System.out.println("Nombre del Jugador: " + element + "\nLista de Bots: ");
            else System.out.println("#" + (data.indexOf(element) - 1) + ": " + element);
        }

        LinkedList<Bot> bots = interfaceMain.getBotList();
        BaseCharacter player = interfaceMain.getPlayerCharacter();
        
        GameContext.getInstance().setInitialCharacters(bots.size() + 1, bots);

        MapGenerator instance = MapGenerator.getInstance(1500, 0, 6, bots.size() + 1, bots, player);
        instance.displayMap();
    }
}

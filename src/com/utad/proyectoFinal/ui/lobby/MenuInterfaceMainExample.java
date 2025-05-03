package com.utad.proyectoFinal.ui.lobby;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.ImplementationAI.Bot;
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

        player.equipWeapon(new com.utad.proyectoFinal.characterSystem.tools.BaseWeapon(com.utad.proyectoFinal.characterSystem.tools.WeaponType.SPEAR));
        player.equipHelmet(new com.utad.proyectoFinal.characterSystem.tools.BaseHelmet(com.utad.proyectoFinal.characterSystem.tools.HelmetType.DEMON_HELMET));

        
        MapGenerator instance = MapGenerator.getInstance(1100, 0, 6, bots.size() + 1, bots, player);
        instance.displayMap();
    }
}

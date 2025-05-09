package com.utad.proyectoFinal.gameManagement;

import java.util.LinkedList;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.mapa.MapGenerator;
import com.utad.proyectoFinal.ui.lobby.MenuInterface;

public class GameControllerFacade {

    public void startGame() {
        GameContext gameContext = GameContext.getInstance();

        MenuInterface interfaceMain = new MenuInterface();
        interfaceMain.showInterface();
        interfaceMain.waitTillClose();

        BaseCharacter player = interfaceMain.getPlayerCharacter();
        LinkedList<Bot> bots = interfaceMain.getBotList();
        LinkedList<String> botNames = interfaceMain.getBotNames();
        
        if (player != null && bots != null && botNames != null) { // Check if player and bots are initialized
            player.addObserver(gameContext);
            bots.forEach(b -> { 
                if (bots.indexOf(b) < botNames.size()) { // Ensure botNames has a corresponding name
                    b.setName(botNames.get(bots.indexOf(b))); 
                }
                b.addObserver(gameContext); 
            });

            gameContext.setInitialCharacters(bots.size() + 1, bots);

            MapGenerator instance = MapGenerator.getInstance(1500, 0, 6, bots.size() + 1, bots, player);
            instance.displayMap();
        } else {
            System.out.println("Error: Game initialization failed. Player or bot data is missing.");
        }
    }
} 
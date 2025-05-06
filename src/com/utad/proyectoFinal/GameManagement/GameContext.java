package com.utad.proyectoFinal.gameManagement;

import java.util.LinkedList;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.ui.podium.PodiumInterface;

public class GameContext implements PushModelObserver {

    private static GameContext instance = null;
    PodiumInterface podium = null;

    private Integer personajesIniciales;
    private Integer personajesVivos;
    private LinkedList<String> personajesMuertos = new LinkedList<>();
    private LinkedList<Bot> bots;

    public static GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    public void setInitialCharacters(Integer initialCharacters, LinkedList<Bot> b) {
        this.personajesIniciales = initialCharacters;
        this.personajesVivos = initialCharacters;
        this.bots = b;
    }

    public void botTurn(BaseCharacter bot)
    {
        this.bots.forEach(b -> {
            if (!bot.equals(b))
            {
                b.BotMove();
            }
        });
    }

    @Override
    public void characterHasDied(BaseCharacter character) {
        personajesVivos--;
        System.out.println("Ha muerto " + character.getName() + ". Vivos: " + personajesVivos);

        if(personajesVivos == 1 && character instanceof Bot && podium == null) {
            System.out.println(personajesMuertos.size());
            podium = new PodiumInterface(personajesVivos, personajesIniciales, null, personajesMuertos);
            podium.showInterface();
        }
    }

    public void playerKilled(CombatCharacter character) {
        if (podium == null){
            podium = new PodiumInterface(personajesVivos, personajesIniciales, character.getName(), personajesMuertos);
            podium.showInterface();
        }
    }

    public void characterKilled(CombatCharacter character) {
        personajesMuertos.add(character.getName());
        if (podium != null) podium.updateKillList(personajesMuertos);
    }
    
    public Integer getPersonajesVivos() {
        return personajesVivos;
    }
}

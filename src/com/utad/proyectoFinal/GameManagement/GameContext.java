package com.utad.proyectoFinal.gameManagement;

import java.util.LinkedList;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.ui.podium.PodiumInterface;

public class GameContext implements PushModelObserver {

    private static GameContext instance = null;

    private Integer personajesIniciales;
    private Integer personajesVivos;
    private LinkedList<String> personajesMuertos = new LinkedList<>();

    public static GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    public void setInitialCharacters(Integer initialCharacters) {
        this.personajesIniciales = initialCharacters;
        this.personajesVivos = initialCharacters;
    }

    @Override
    public void characterHasDied(BaseCharacter character) {
        personajesVivos--;
        System.out.println("Ha muerto " + character.getName() + ". Vivos: " + personajesVivos);

        if(personajesVivos == 1) {
            PodiumInterface podium = new PodiumInterface(personajesIniciales, personajesVivos, null, personajesMuertos);
            podium.showInterface();
        }
    }

    public void playerKilled(CombatCharacter character) {
        PodiumInterface podium = new PodiumInterface(personajesIniciales, personajesVivos, character.getName(), personajesMuertos);
        podium.showInterface();
    }

    public void characterKilled(CombatCharacter character) {
        personajesMuertos.add(character.getName());
    }
    
    public Integer getPersonajesVivos() {
        return personajesVivos;
    }
}

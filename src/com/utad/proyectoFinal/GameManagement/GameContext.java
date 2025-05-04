package com.utad.proyectoFinal.GameManagement;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

public class GameContext implements PushModelObserver {

    private Integer personajesVivos;
    public GameContext(Integer personajesIniciales) {
        this.personajesVivos = personajesVivos;
    }

    @Override
    public void characterHasDied(BaseCharacter character) {
        personajesVivos--;
        System.out.println("Ha muerto " + character.getName() + ". Vivos: " + personajesVivos);

        if(personajesVivos == 1) {
            //lógica para end game (Pantalla final)
        }
    }

    public Integer getPersonajesVivos() {
        return personajesVivos;
    }
}

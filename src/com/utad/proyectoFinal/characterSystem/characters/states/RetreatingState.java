package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;

// P(ret) = Mp / 2 Me PROBS retire MP -> mana player, Me -> mana enemy
public class RetreatingState extends BaseState {

    RetreatingState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        boolean retirada = false;



        // Calcular la probabilidad de retirada
        if (Math.random() * 100 <= probabilidadRetirada) {
            retirada = true;
        }

        character.setRetreatSuccess(retirada);
    }

    @Override
    public String getName() {
        return "Retreating";
    }
}

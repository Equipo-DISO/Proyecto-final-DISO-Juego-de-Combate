package com.utad.proyectoFinal.deprecated;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.BaseState;

@Deprecated
public class DefendingState extends BaseState {

    DefendingState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleDefense(BaseCharacter opponent) {
        if (contraAtaco() && opponent.getCurrentState().getName().equals("Attacking")) {
            this.contraataque(opponent, opponent.getBaseAttack());
        }
    }

    @Override
    public String getName() {
        return "Defending";
    }

    protected Boolean contraAtaco() {
        return character.getRetreatChance() > Math.random() * 100;
    }

    // Sub-mecanica de la defensa: contraataque (devolver un la mitad del daño recibido)
    protected void contraataque(BaseCharacter opponent, Double ataque) {
        opponent.getCurrentState().handleReceiveAttack(ataque * character.getBaseCounterAttackDamage() / 100);
        System.out.println("¡Que suerte! El personaje ha contraatacado y ha devuelto un "
                + character.getRetreatChance() + "% del daño recibido!");
    }
}

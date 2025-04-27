package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;

// P(ret) = (MPr_p / MPr_e) / 2 * 100
public class RetreatingState extends BaseState {

    RetreatingState(BaseCharacter character) {
        super(character);
    }

    // TODO: Change algorithm to use the new mana system and P(ret) formula
    @Override
    public void handleRetreat(BaseCharacter opponent) {
        boolean retirada = false;

        // Calcular la probabilidad de retirada
        double probabilidadRetirada = character.getRetreatChance();
        if (Math.random() * 100 <= probabilidadRetirada) {
            retirada = true;
            System.out.printf("%s ha conseguido retirarse con éxito.%n", character.getName());
        } else {
            System.out.printf("%s ha intentado retirarse pero ha fallado.%n", character.getName());
        }

        character.setRetreatSuccess(retirada);

        // Actualizar estado
        updateState();
    }

    @Override
    public void updateState() {
        // In testing mode, don't transition automatically
        if (isTestingMode()) {
            return;
        }

        if (character.isRetreatSuccessful()) {
            character.transitionTo(character.getStates().getMovingOnMapState());
        } else {
            character.transitionTo(character.getStates().getIdleState());
        }
    }

    @Override
    public String getName() {
        return "Retreating";
    }
}

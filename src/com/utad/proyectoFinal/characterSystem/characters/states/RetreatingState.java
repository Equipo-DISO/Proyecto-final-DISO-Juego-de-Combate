package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.gameManagement.Calculator;
import com.utad.proyectoFinal.ui.combat.Action;

// P(ret) = (MPr_p / MPr_e) / 2 * 100
public class RetreatingState extends BaseState {

    RetreatingState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        boolean retirada = false;

        StringBuilder message = new StringBuilder(String.format("%s intenta retirarse.%n", character.getName()));
        System.out.printf(message.toString());
        if (character.getFeedLogger() != null) {
            character.getFeedLogger().addFeedLine(message.toString(), Action.RUN);
        }

        // Usar Calculator para calcular la probabilidad de retirada
        double probabilidadRetirada = Calculator.getInstance().calculateRetreatProbability(character, opponent);
        if (Math.random() * 100 <= probabilidadRetirada) {
            retirada = true;
            message = new StringBuilder(String.format("%s ha conseguido retirarse con éxito.%n", character.getName())); 
            
        } else {
            message = new StringBuilder(String.format("%s ha intentado retirarse pero ha fallado.%n", character.getName()));
        }

        System.out.printf(message.toString());
        if (character.getFeedLogger() != null) {
            character.getFeedLogger().addFeedLine(message.toString(), Action.RUN);
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

        
        character.transitionTo(character.getStates().getIdleState());
        
    }

    @Override
    public String getName() {
        return "Retreating";
    }
}

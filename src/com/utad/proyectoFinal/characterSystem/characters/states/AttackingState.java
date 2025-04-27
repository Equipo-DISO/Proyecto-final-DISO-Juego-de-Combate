package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;

import java.util.Scanner;

public class AttackingState extends BaseState {

    private AttackStrategy currentStrategy;

    AttackingState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
        selectStrategy();
        if (currentStrategy != null) {
            currentStrategy.execute(character, opponent);
            updateState();
        }
    }

    private void selectStrategy() {
        // Use the common testing mode flag from BaseState
        if (isTestingMode()) {
            // Use light attack strategy for testing
            currentStrategy = new LightAttackStrategy();
            return;
        }

        // Normal user input for non-testing mode
        System.out.printf("%n%s: elige tipo de ataque (1) Ligero (2) Pesado ▶ ",
                character.getName());
        int option = new Scanner(System.in).nextInt();
        currentStrategy = option == 2
                ? new HeavyAttackStrategy()
                : new LightAttackStrategy();
    }

    @Override
    public void updateState() {
        // Comprobar si el personaje tiene poco maná después del ataque
        if (!checkAndTransitionToTiredIfNeeded()) {
            // Use the common testing mode flag from BaseState
            // In testing mode, don't transition back to Idle automatically
            // This allows tests to check the state after an attack
            if (!isTestingMode()) {
                character.transitionTo(character.getStates().getIdleState()); // volver a Idle si tiene suficiente maná
            }
        }
    }

    @Override
    public String getName() {
        return "Attacking";
    }
}

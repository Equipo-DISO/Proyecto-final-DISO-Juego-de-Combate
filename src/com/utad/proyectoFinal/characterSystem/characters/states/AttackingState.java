package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.*;

public class AttackingState extends BaseState {

    private AttackStrategy currentStrategy;

    AttackingState(BaseCharacter character) {
        super(character);
    }


    
    /**
     * Handle attack with a specified strategy
     * @param opponent The target character to attack
     * @param attackStrategy The strategy to use for this attack
     */
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        currentStrategy = attackStrategy;

        // TODO: delete after testing
        selectStrategy();

        executeAttack(opponent);
    }
    
    /**
     * Execute the current attack strategy
     * @param opponent The target character to attack
     */
    private void executeAttack(BaseCharacter opponent) {
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
    
    /**
     * Get the current attack strategy
     * @return The current attack strategy
     */
    public AttackStrategy getCurrentStrategy() {
        return currentStrategy;
    }
    
    /**
     * Set a specific attack strategy
     * @param strategy The attack strategy to set
     */
    public void setStrategy(AttackStrategy strategy) {
        this.currentStrategy = strategy;
    }

    @Override
    public String getName() {
        return "Attacking";
    }
}

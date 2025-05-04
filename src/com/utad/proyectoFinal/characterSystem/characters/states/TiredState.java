package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;

/**
 * Estado que representa cuando el personaje tiene energía baja y no puede atacar.
 */
public class TiredState extends BaseState {

    // El umbral de maná se ha movido a BaseCharacter.LOW_MANA_THRESHOLD

    TiredState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        System.out.printf("%s está demasiado cansado para atacar. Necesita recuperar energía.%n",
                character.getName());
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        // Cambiar al estado de retirada
        character.transitionTo(character.getStates().getRetreatingState());
        character.getCurrentState().handleRetreat(opponent);
    }

    /**
     * Método para manejar la curación
     */
    @Override
    public void handleHeal() {
        // Cambiar al estado de curación
        character.transitionTo(character.getStates().getHealState());
        ((HealState) character.getCurrentState()).handleHeal();
    }

    /**
     * Método para manejar la ganancia de maná
     */
    @Override
    public void handleGainMana() {
        // Cambiar al estado de ganancia de maná
        character.transitionTo(character.getStates().getGainManaState());
        ((GainManaState) character.getCurrentState()).gainMana();
    }
    
    @Override
    public void updateState() {
        // In testing mode, don't transition automatically
        if (isTestingMode()) {
            return;
        }

        // Si el personaje recupera suficiente maná, volver a Idle
        if (!character.isLowEnergy()) {
            character.transitionTo(character.getStates().getIdleState());
        }
    }

    @Override
    public String getName() {
        return "Tired";
    }

    /**
     * Método estático para comprobar si un personaje debería estar en estado cansado
     * @param character El personaje a comprobar
     * @return true si el personaje debería estar cansado, false en caso contrario
     */
    public static boolean shouldBeTired(BaseCharacter character) {
        return character.isLowEnergy();
    }
}

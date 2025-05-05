package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.ui.combat.Action;

/**
 * Estado que representa cuando el personaje elige curarse.
 */
public class HealState extends BaseState {

    HealState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        StringBuilder message = new StringBuilder(String.format("%s está curándose y no puede atacar en este momento.%n",
                character.getName()));
        System.out.printf(message.toString());
        if (character.getFeedLogger() != null) {
            character.getFeedLogger().addFeedLine(message.toString(), Action.HEAL);
        }
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        StringBuilder message = new StringBuilder(String.format("%s está curándose y no puede retirarse en este momento.%n",
                character.getName()));
        System.out.printf(message.toString());
        if (character.getFeedLogger() != null) {
            character.getFeedLogger().addFeedLine(message.toString(), Action.HEAL);
        }
    }

    /**
     * Ejecuta la acción de curación
     */
    @Override
    public void handleHeal() {
        // Comprobar si tiene suficiente maná para curarse
        if (character.getHpPotions() <= 0) {
            StringBuilder message = new StringBuilder(String.format("%s intenta curarse pero no tiene pociones de salud.%n",
                    character.getName()));
            System.out.printf(message.toString());
            if (character.getFeedLogger() != null) {
                character.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
            }
        } else {
            // Consumir maná
            character.useHpPotion();

            Integer currentHealth = character.getHealthPoints();

            // Calcular nueva salud (sin exceder el máximo)
            character.gainHealth(currentHealth + DefaultAttributes.POTION_HEAL_AMOUNT);

            StringBuilder message = new StringBuilder(String.format("%s se ha curado %d puntos de salud.%n",
                    character.getName(), character.getHealthPoints() - currentHealth));
            System.out.printf(message.toString());
            if (character.getFeedLogger() != null) {
                character.getFeedLogger().addFeedLine(message.toString(), Action.HEAL);
            }
        }
        // Actualizar estado
        updateState();
    }

    @Override
    public void updateState() {
        // In testing mode, don't transition back to Idle automatically
        if (isTestingMode()) {
            return;
        }

        // Volver a Idle después de curarse
        character.transitionTo(character.getStates().getIdleState());
    }

    @Override
    public String getName() {
        return "Heal";
    }
}

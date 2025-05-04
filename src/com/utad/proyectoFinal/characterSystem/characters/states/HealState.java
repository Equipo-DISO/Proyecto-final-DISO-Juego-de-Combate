package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;

/**
 * Estado que representa cuando el personaje elige curarse.
 */
public class HealState extends BaseState {

    HealState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        System.out.printf("%s está curándose y no puede atacar en este momento.%n",
                character.getName());
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        System.out.printf("%s está curándose y no puede retirarse en este momento.%n",
                character.getName());
    }

    /**
     * Ejecuta la acción de curación
     */
    @Override
    public void handleHeal() {
        // Comprobar si tiene suficiente maná para curarse
        if (character.getHpPotions() <= 0) {
            System.out.printf("%s intenta curarse pero no tiene pociones de salud.%n",
                    character.getName());
        } else {
            // Consumir maná
            character.useHpPotion();

            Integer currentHealth = character.getHealthPoints();

            // Calcular nueva salud (sin exceder el máximo)
            character.gainHealth(currentHealth + DefaultAttributes.POTION_HEAL_AMOUNT);

            System.out.printf("%s se ha curado %d puntos de salud.%n",
                    character.getName(), character.getHealthPoints() - currentHealth);
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

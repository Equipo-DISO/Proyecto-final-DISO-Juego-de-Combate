package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;

/**
 * Estado que representa cuando el personaje elige curarse.
 */
public class HealState extends BaseState {

    // Cantidad de salud que se recupera al curarse
    private static final int HEAL_AMOUNT = 20;
    
    // Coste de maná para curarse
    private static final int HEAL_MANA_COST = 5;

    HealState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
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
    public void heal() {
        // Comprobar si tiene suficiente maná para curarse
        if (character.getManaPoints() < HEAL_MANA_COST) {
            System.out.printf("%s intenta curarse pero no tiene suficiente maná (%d requerido).%n",
                    character.getName(), HEAL_MANA_COST);
            // Si no tiene suficiente maná, pasar a estado cansado
            if (TiredState.shouldBeTired(character)) {
                character.setTiredState();
            } else {
                character.setIdleState();
            }
            return;
        }

        // Consumir maná
        character.decreaseManaPoints(HEAL_MANA_COST);

        Integer currentHealth = character.getHealthPoints();

        // Calcular nueva salud (sin exceder el máximo)
        character.gainHealth(currentHealth + HEAL_AMOUNT);
        
        System.out.printf("%s se ha curado %d puntos de salud.%n",
                character.getName(), character.getHealthPoints() - currentHealth);
        
        // Actualizar estado
        updateState();
    }

    @Override
    public void updateState() {
        // Volver a Idle después de curarse
        character.setIdleState();
    }

    @Override
    public String getName() {
        return "Heal";
    }
}
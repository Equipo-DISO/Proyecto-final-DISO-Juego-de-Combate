package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;

/**
 * Estado que representa cuando el personaje gana energía.
 */
public class GainManaState extends BaseState {

    // Cantidad de maná que se recupera
    private static final int MANA_GAIN_AMOUNT = 15;

    GainManaState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
        System.out.printf("%s está recuperando energía y no puede atacar en este momento.%n",
                character.getName());
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        System.out.printf("%s está recuperando energía y no puede retirarse en este momento.%n",
                character.getName());
    }

    /**
     * Ejecuta la acción de ganar maná
     */
    public void gainMana() {
        // Calcular nuevo maná (sin exceder el máximo)
        int currentMana = character.getManaPoints();
        int newMana = Math.min(currentMana + MANA_GAIN_AMOUNT, DefaultAttributes.MAX_MANA_POINTS);
        character.setManaPoints(newMana);

        System.out.printf("%s ha recuperado %d puntos de energía.%n",
                character.getName(), newMana - currentMana);

        // Actualizar estado
        updateState();
    }

    @Override
    public void updateState() {
        // In testing mode, don't transition back to Idle automatically
        if (isTestingMode()) {
            return;
        }

        // Volver a Idle después de ganar maná
        character.transitionTo(character.getStates().getIdleState());
    }

    @Override
    public String getName() {
        return "GainMana";
    }
}

package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

/**
 * Estado que representa cuando el personaje tiene energía baja y no puede atacar.
 */
public class TiredState extends BaseState {

    // Umbral de maná por debajo del cual el personaje está cansado
    private static final int MANA_THRESHOLD = 10;

    TiredState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
        System.out.printf("%s está demasiado cansado para atacar. Necesita recuperar energía.%n",
                character.getName());
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        // Cambiar al estado de retirada
        character.setCurrentState(new RetreatingState(character));
        character.getCurrentState().handleRetreat(opponent);
    }

    @Override
    public void updateState() {
        // Si el personaje recupera suficiente maná, volver a Idle
        if (character.getManaPoints() > MANA_THRESHOLD) {
            character.setIdleState();
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
        return character.getManaPoints() <= MANA_THRESHOLD;
    }
}
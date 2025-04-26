package com.utad.proyectoFinal.characterSystem.characters.states;


import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

public class IdleState extends BaseState {

    IdleState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
        // Comprobar si el personaje tiene suficiente maná para atacar
        if (TiredState.shouldBeTired(character)) {
            System.out.printf("%s tiene poca energía y no puede atacar.%n", character.getName());
            character.setTiredState();
        } else {
            // Cambiar al estado de ataque
            character.setAttackingState();
            character.getCurrentState().handleAttack(opponent);
        }
    }


    @Override
    public void handleRetreat(BaseCharacter opponent) {
        // Cambiar al estado de retirada
        character.setRetreatingState();
        character.getCurrentState().handleRetreat(opponent);
    }

    @Override
    public void handleMove(Object tile) {
        // Cambiar al estado de movimiento en el mapa
        character.setMovingOnMapState();
        character.getCurrentState().handleMove(tile);
    }

    /**
     * Método para manejar la curación
     */
    public void handleHeal() {
        // Cambiar al estado de curación
        character.setHealState();
        ((HealState) character.getCurrentState()).heal();
    }

    /**
     * Método para manejar la ganancia de maná
     */
    public void handleGainMana() {
        // Cambiar al estado de ganancia de maná
        character.setGainManaState();
        ((GainManaState) character.getCurrentState()).gainMana();
    }

    @Override
    public void updateState() {
        // Comprobar si el personaje tiene poca energía
        if (TiredState.shouldBeTired(character)) {
            character.setTiredState();
        }
    }

    @Override
    public void handleReceiveAttack(Double damage) {
        // Usar la implementación por defecto de BaseState
        super.handleReceiveAttack(damage);
    }

    @Override
    public String getName() {
        return "Idle";
    }
}

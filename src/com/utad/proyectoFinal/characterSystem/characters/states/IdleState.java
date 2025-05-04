package com.utad.proyectoFinal.characterSystem.characters.states;


import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.mapa.GenericTile;

public class IdleState extends BaseState {

    IdleState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        // Comprobar si el personaje tiene suficiente maná para atacar
        if (checkAndTransitionToTiredIfNeeded()) {
            System.out.printf("%s tiene poca energía y no puede atacar.%n", character.getName());
        } else {
            // Cambiar al estado de ataque
            character.transitionTo(character.getStates().getAttackingState());
            character.getCurrentState().handleAttack(opponent, attackStrategy);
        }
    }


    @Override
    public void handleRetreat(BaseCharacter opponent) {
        // Cambiar al estado de retirada
        character.transitionTo(character.getStates().getRetreatingState());
        character.getCurrentState().handleRetreat(opponent);
    }

    @Override
    public void handleMove(GenericTile moveToTile) {
        // Cambiar al estado de movimiento en el mapa
        character.transitionTo(character.getStates().getMovingOnMapState());
        character.getCurrentState().handleMove(moveToTile);
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
    public void handleGainMana() {
        // Cambiar al estado de ganancia de maná
        character.transitionTo(character.getStates().getGainManaState());
        ((GainManaState) character.getCurrentState()).gainMana();
    }

    @Override
    public void updateState() {
        // Check if character is dead first
        if (!character.isAlive()) {
            character.transitionTo(character.getStates().getDeadState());
            character.notifyDeathObservers();
            return;
        }

        // Comprobar si el personaje tiene poca energía
        checkAndTransitionToTiredIfNeeded();
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

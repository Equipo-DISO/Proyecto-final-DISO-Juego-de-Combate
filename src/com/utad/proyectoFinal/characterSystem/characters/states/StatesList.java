package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

public class StatesList {
    // Campos privados
    private final IdleState idleState;
    private final AttackingState attackingState;
    private final HealState healState;
    private final GainManaState gainManaState;
    private final RetreatingState retreatingState;
    private final TiredState tiredState;
    private final MovingOnMapState movingOnMapState;
    private final DeadState deadState;

    // Constructor (suponiendo que ya recibes BaseCharacter character)
    public StatesList(BaseCharacter character) {
        idleState = new IdleState(character);
        attackingState = new AttackingState(character);   // <-- se inyectará la UI más adelante
        healState = new HealState(character);
        gainManaState = new GainManaState(character);
        retreatingState = new RetreatingState(character);
        tiredState = new TiredState(character);
        movingOnMapState = new MovingOnMapState(character);
        deadState = new DeadState(character);
    }

    // Getters públicos (uno por estado)
    public IdleState getIdleState() {
        return idleState;
    }

    public AttackingState getAttackingState() {
        return attackingState;
    }

    public HealState getHealState() {
        return healState;
    }

    public GainManaState getGainManaState() {
        return gainManaState;
    }

    public RetreatingState getRetreatingState() {
        return retreatingState;
    }

    public TiredState getTiredState() {
        return tiredState;
    }

    public MovingOnMapState getMovingOnMapState() {
        return movingOnMapState;
    }

    public DeadState getDeadState() {
        return deadState;
    }
}
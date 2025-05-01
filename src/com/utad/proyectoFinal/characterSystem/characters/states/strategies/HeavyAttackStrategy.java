package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.tools.Calculator;

public class HeavyAttackStrategy extends AbstractAttackStrategy {

    private static final int MANA_COST = 10;
    private static final double HIT_CHANCE = 60.0; // Probabilidad de acierto 60%
    private static final double DAMAGE_MULTIPLIER = 2.0;
    // Constante del coste de durabilidad
    private static final int DURABILITY_COST = 2;

    public HeavyAttackStrategy() {
        super("Ataque Pesado");
    }

    @Override
    public double calculateDamage(BaseCharacter attacker) {
        return Calculator.getInstance().calculateAttackDamage(attacker, DAMAGE_MULTIPLIER);
    }

    @Override
    public int calculateManaCost() {
        return MANA_COST;
    }

    @Override
    public boolean calculateHitSuccess(BaseCharacter attacker) {
        return Calculator.getInstance().calculateHitProbability(HIT_CHANCE);
    }

    @Override
    public int calculateDurabilityCost() {
        return Calculator.getInstance().calculateWeaponDurabilityCost(DURABILITY_COST);
    }
}

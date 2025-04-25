package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

/**
 * Interfaz que define la estrategia de ataque
 */
public interface AttackStrategy {
    public String getName();
    public Double calculateDamage(BaseCharacter attacker);
    public Boolean calculateHitSuccess(BaseCharacter attacker);
    public Integer calculateManaCost();
    public void execute(BaseCharacter attacker, BaseCharacter target);
}

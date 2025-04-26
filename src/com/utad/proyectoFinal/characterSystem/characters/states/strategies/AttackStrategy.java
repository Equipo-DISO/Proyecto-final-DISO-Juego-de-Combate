package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

public interface AttackStrategy {

    /* Nombre identificativo (para logs, UI, etc.) */
    String getName();

    /* Daño antes de aplicar la defensa del objetivo */
    double calculateDamage(BaseCharacter attacker);

    /* Coste de maná del ataque */
    int calculateManaCost();

    /* Probabilidad de acierto */
    boolean calculateHitSuccess(BaseCharacter attacker);

    /* Método de alto nivel que orquesta el ataque */
    void execute(BaseCharacter attacker, BaseCharacter target);

    /* Coste de durabilidad del arma */
    int calculateDurabilityCost();
}

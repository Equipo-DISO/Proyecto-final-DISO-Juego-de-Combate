package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.gameManagement.Calculator;

/**
 * Implementación de la estrategia para ataques ligeros.
 * <p>
 * Los ataques ligeros se caracterizan por:
 * <ul>
 *   <li>Alta probabilidad de acierto (90%)</li>
 *   <li>Daño normal (multiplicador x1)</li>
 *   <li>Bajo coste de maná (7 puntos)</li>
 *   <li>Bajo desgaste del arma (1 punto)</li>
 * </ul>
 * Esta estrategia es ideal para asegurar impactos a costa de un daño menor.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class LightAttackStrategy extends AbstractAttackStrategy {

    private static final int MANA_COST = 7;
    private static final double HIT_CHANCE = 90.0; // Probabilidad de acierto 90%
    private static final double DAMAGE_MULTIPLIER = 1.0;
    // Constante extraída para coste de durabilidad
    private static final int DURABILITY_COST = 1;

    /**
     * Constructor que inicializa la estrategia de ataque ligero.
     * Establece "Ataque Ligero" como nombre de la estrategia.
     */
    public LightAttackStrategy() {
        super("Ataque Ligero");
    }

    /**
     * Calcula el daño que causará el ataque ligero.
     * <p>
     * Utiliza el {@code Calculator} para determinar si ocurre un golpe crítico
     * y para calcular el daño base aplicando el multiplicador estándar (x1).
     * </p>
     * 
     * @param attacker El personaje que realiza el ataque
     * @return El valor de daño calculado
     */
    @Override
    public double calculateDamage(BaseCharacter attacker) {
        Boolean isCritical = Calculator.getInstance().isCriticalHit(attacker);
        if (isCritical)
            handleCriticalHitMessage(attacker);
        return Calculator.getInstance().calculateAttackDamage(attacker, DAMAGE_MULTIPLIER, isCritical);
    }

    /**
     * Obtiene el coste de maná requerido para ejecutar un ataque ligero.
     * 
     * @return El coste de maná (7 puntos)
     */
    @Override
    public int calculateManaCost() {
        return MANA_COST;
    }

    /**
     * Determina si el ataque ligero impacta en el objetivo.
     * <p>
     * Utiliza el {@code Calculator} para determinar si el ataque acierta
     * basándose en la alta probabilidad de acierto (90%).
     * </p>
     * 
     * @param attacker El personaje que realiza el ataque
     * @return {@code true} si el ataque acierta, {@code false} si falla
     */
    @Override
    public boolean calculateHitSuccess(BaseCharacter attacker) {
        return Calculator.getInstance().calculateHitProbability(HIT_CHANCE);
    }

    /**
     * Calcula el coste de durabilidad que sufre el arma al realizar un ataque ligero.
     * <p>
     * Los ataques ligeros tienen un desgaste mínimo del arma.
     * </p>
     * 
     * @return El coste de durabilidad (1 punto)
     */
    @Override
    public int calculateDurabilityCost() {
        return Calculator.getInstance().calculateWeaponDurabilityCost(DURABILITY_COST);
    }
}

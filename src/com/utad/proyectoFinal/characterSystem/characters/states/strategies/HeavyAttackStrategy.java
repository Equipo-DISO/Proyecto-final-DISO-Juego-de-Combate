package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.gameManagement.Calculator;

/**
 * Implementación de la estrategia para ataques pesados.
 * <p>
 * Los ataques pesados se caracterizan por:
 * <ul>
 *   <li>Probabilidad de acierto media (60%)</li>
 *   <li>Alto daño (multiplicador x2)</li>
 *   <li>Alto coste de maná (10 puntos)</li>
 *   <li>Mayor desgaste del arma (2 puntos)</li>
 * </ul>
 * Esta estrategia es ideal para causar gran daño en un unico turno a costa de menor precisión y más recursos.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class HeavyAttackStrategy extends AbstractAttackStrategy {

    private static final int MANA_COST = 10;
    private static final double HIT_CHANCE = 60.0; // Probabilidad de acierto 60%
    private static final double DAMAGE_MULTIPLIER = 2.0;
    // Constante del coste de durabilidad
    private static final int DURABILITY_COST = 2;

    /**
     * Constructor que inicializa la estrategia de ataque pesado.
     * Establece "Ataque Pesado" como nombre de la estrategia.
     */
    public HeavyAttackStrategy() {
        super("Ataque Pesado");
    }

    /**
     * Calcula el daño que causará el ataque pesado.
     * <p>
     * Utiliza el {@code Calculator} para determinar si ocurre un golpe crítico
     * y para calcular el daño base aplicando un multiplicador elevado (x2).
     * </p>
     * 
     * @param attacker El personaje que realiza el ataque
     * @return El valor de daño calculado, potencialmente mayor que un ataque ligero
     */
    @Override
    public double calculateDamage(BaseCharacter attacker) {
        Boolean isCritical = Calculator.getInstance().isCriticalHit(attacker);
        if (isCritical)
            handleCriticalHitMessage(attacker);
        return Calculator.getInstance().calculateAttackDamage(attacker, DAMAGE_MULTIPLIER, isCritical);
    }

    /**
     * Obtiene el coste de maná requerido para ejecutar un ataque pesado.
     * 
     * @return El coste de maná (10 puntos)
     */
    @Override
    public int calculateManaCost() {
        return MANA_COST;
    }

    /**
     * Determina si el ataque pesado impacta en el objetivo.
     * <p>
     * Utiliza el {@code Calculator} para determinar si el ataque acierta
     * basándose en una probabilidad media de acierto (60%).
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
     * Calcula el coste de durabilidad que sufre el arma al realizar un ataque pesado.
     * <p>
     * Los ataques pesados tienen un mayor desgaste del arma que los ataques ligeros.
     * </p>
     * 
     * @return El coste de durabilidad (2 puntos)
     */
    @Override
    public int calculateDurabilityCost() {
        return Calculator.getInstance().calculateWeaponDurabilityCost(DURABILITY_COST);
    }
}

package com.utad.proyectoFinal.characterSystem.tools;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

public final class Calculator {

    // Instanciación en estático
    private static final Calculator INSTANCE = new Calculator();

    private Calculator() { }

    public static Calculator getInstance() {
        return INSTANCE;
    }

    /* --------------------------- Cálculos básicos -------------------------- */

    public double calculateBaseDamage(BaseCharacter attacker) {
        double weaponDamage = attacker.getWeapon() != null
                ? attacker.getWeapon().getDamage() : 0;
        return attacker.getBaseAttack() + weaponDamage;
    }

    public double calculateHelmetReduction(BaseCharacter target, double damage) {
        double helmetDefense = target.getHelmet() != null
                ? target.getHelmet().getDefense() : 0;
        return Math.max(0, damage - helmetDefense);
    }

    /* --------------------------- Cálculos de ataque -------------------------- */

    /**
     * Calcula el daño de ataque aplicando un multiplicador al daño base.
     * 
     * @param attacker El personaje que realiza el ataque
     * @param multiplier El multiplicador de daño a aplicar
     * @return El daño calculado después de aplicar el multiplicador
     */
    public double calculateAttackDamage(BaseCharacter attacker, double multiplier) {
        double baseDamage = calculateBaseDamage(attacker);
        return baseDamage * multiplier;
    }

    /**
     * Calcula si un ataque tiene éxito basado en una probabilidad base.
     * 
     * @param baseHitChance La probabilidad base de acierto (0-100)
     * @return true si el ataque tiene éxito, false en caso contrario
     */
    public boolean calculateHitProbability(double baseHitChance) {
        return Math.random() * 100 <= baseHitChance;
    }

    /**
     * Calcula el coste de durabilidad para un arma.
     * 
     * @param baseCost El coste base de durabilidad
     * @return El coste de durabilidad calculado
     */
    public int calculateWeaponDurabilityCost(int baseCost) {
        // Por ahora simplemente devuelve el coste base, pero podría
        // incorporar lógica adicional en el futuro
        return baseCost;
    }
}

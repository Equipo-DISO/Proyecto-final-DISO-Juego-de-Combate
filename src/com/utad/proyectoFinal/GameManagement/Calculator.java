package com.utad.proyectoFinal.GameManagement;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

import java.util.Random;

public final class Calculator {

    // Instanciación en estático
    private static final Calculator INSTANCE = new Calculator();
    
    // Random instance for consistent randomization
    private final Random random = new Random();

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
     * Ahora incluye la posibilidad de golpes críticos basados en la probabilidad del arma.
     * 
     * @param attacker El personaje que realiza el ataque
     * @param multiplier El multiplicador de daño a aplicar
     * @return El daño calculado después de aplicar el multiplicador
     */
    public double calculateAttackDamage(BaseCharacter attacker, double multiplier) {
        double baseDamage = calculateBaseDamage(attacker);
        double finalDamage = baseDamage * multiplier;
        
        // Verificar si el atacante tiene un arma y puede hacer crítico
        if (attacker.getWeapon() != null && isCriticalHit(attacker)) {
            // Aplicar daño crítico
            finalDamage = applyWeaponCriticalDamage(attacker, finalDamage);
            System.out.printf("¡%s asesta un golpe CRÍTICO con %s!%n", 
                    attacker.getName(), attacker.getWeapon().getName());
        }
        
        return finalDamage;
    }
    
    /**
     * Determina si un ataque es crítico basado en la probabilidad crítica del arma.
     * 
     * @param attacker El personaje que realiza el ataque
     * @return true si el ataque es crítico, false en caso contrario
     */
    public boolean isCriticalHit(BaseCharacter attacker) {
        if (attacker.getWeapon() == null) {
            return false;
        }
        
        // La probabilidad crítica está en rango 0.0-1.0
        double criticalChance = attacker.getWeapon().getCriticalChance();
        return random.nextDouble() <= criticalChance;
    }
    
    /**
     * Aplica el multiplicador de daño crítico del arma al daño base.
     * 
     * @param attacker El personaje que realiza el ataque
     * @param baseDamage El daño base calculado
     * @return El daño final después de aplicar el crítico
     */
    public double applyWeaponCriticalDamage(BaseCharacter attacker, double baseDamage) {
        double criticalMultiplier = attacker.getWeapon().getCriticalDamage();
        return baseDamage * criticalMultiplier;
    }

    /**
     * Calcula si un ataque tiene éxito basado en una probabilidad base.
     * 
     * @param baseHitChance La probabilidad base de acierto (0-100)
     * @return true si el ataque tiene éxito, false en caso contrario
     */
    public boolean calculateHitProbability(double baseHitChance) {
        return random.nextDouble() * 100 <= baseHitChance;
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

    /**
     * Calcula la probabilidad de retirada exitosa basada en la relación de puntos de maná.
     * Formula: P(ret) = (MPr_p / MPr_e) / 2 * 100
     * 
     * @param retreatingCharacter El personaje que intenta retirarse
     * @param opponent El oponente del personaje
     * @return La probabilidad de retirada (0-100)
     */
    public double calculateRetreatProbability(BaseCharacter retreatingCharacter, BaseCharacter opponent) {
        return (((double) retreatingCharacter.getManaPoints() / retreatingCharacter.getMaxManaPoints())
                / ((double) opponent.getManaPoints() / opponent.getMaxManaPoints()) / 2) * 100;
    }
}

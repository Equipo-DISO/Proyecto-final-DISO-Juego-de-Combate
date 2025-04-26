package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.tools.Calculator;

/**
* Plantilla común para todas las estrategias de ataque.
* Implementa el método {@code execute} final para evitar duplicar lógica.
*/
public abstract class AbstractAttackStrategy implements AttackStrategy {

    protected final String name;

    protected AbstractAttackStrategy(String name) {
        this.name = name;
    }

    /* ------------------------------- Template ------------------------------ */

    @Override
    public final void execute(BaseCharacter attacker, BaseCharacter target) {
        if (!attacker.isAlive() || !target.isAlive()) {
            return;
        }

        int manaCost = calculateManaCost();
        if (attacker.getManaPoints() < manaCost) {
            // TODO: Esto debería deshabilitar el ataque en la UI y lanzar un mensaje
            System.out.printf("%s intenta %s pero no tiene suficiente maná (%d requerido).%n",
                    attacker.getName(), name, manaCost);
            return;
        }

        // 1. Consumir maná
        attacker.decreaseManaPoints(manaCost);

        // 2. Comprobar si impacta
        if (!calculateHitSuccess(attacker)) {
            applyMiss(attacker, target);
            return;
        }

        // 3. Calcular daño y aplicarlo
        double damage = calculateDamage(attacker);
        applyHit(attacker, target, damage);
    }

    /* -------------------------- Métodos utilitarios ------------------------ */

    protected void applyHit(BaseCharacter attacker, BaseCharacter target, double damage) {
        target.getCurrentState().handleReceiveAttack(damage);
        System.out.printf("%s ejecuta %s y causa %.0f de daño a %s.%n",
                attacker.getName(), name, damage, target.getName());

        // Ataque con éxito -> reducir durabilidad del arma
        if (attacker.getWeapon() != null) {
            attacker.getWeapon().decreaseDurability(calculateDurabilityCost());
            handleWeaponBreak(attacker);
        }
    }

    private void handleWeaponBreak(BaseCharacter attacker) {
        if (attacker.getWeapon() != null && attacker.getWeapon().getDurability() <= 0) {
            System.out.printf("%s ha roto su arma.%n", attacker.getName());
            attacker.setWeapon(null);
        }
    }

    protected void applyMiss(BaseCharacter attacker, BaseCharacter target) {
        System.out.printf("%s falla su %s contra %s.%n",
                attacker.getName(), name, target.getName());
    }

    /* -------------------- Métodos a implementar por hijos ------------------ */

    @Override
    public abstract double calculateDamage(BaseCharacter attacker);

    @Override
    public abstract int calculateManaCost();

    @Override
    public abstract boolean calculateHitSuccess(BaseCharacter attacker);

    @Override
    public abstract int calculateDurabilityCost();

    @Override
    public String getName() {
        return name;
    }
}

package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.ui.combat.Action;

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
            StringBuilder message = new StringBuilder(String.format("%s intenta %s pero no tiene suficiente maná (%d requerido).%n",
                    attacker.getName(), name, manaCost));
            System.out.printf(message.toString());
            if (attacker.getFeedLogger() != null) {
                attacker.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
            }
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
        StringBuilder message = new StringBuilder(String.format("%s ejecuta %s y causa %.0f de daño a %s.%n",
                attacker.getName(), name, damage, target.getName()));
        System.out.printf(message.toString());
        if (attacker.getFeedLogger() != null) {
            attacker.getFeedLogger().addFeedLine(message.toString(), Action.ATACK);
        }

        // Ataque con éxito -> reducir durabilidad del arma
        if (attacker.getWeapon() != null) {
            attacker.getWeapon().decreaseDurability(calculateDurabilityCost());
            handleWeaponBreak(attacker);
        }
    }

    private void handleWeaponBreak(BaseCharacter attacker) {
        if (attacker.getWeapon() != null && attacker.getWeapon().getDurability() <= 0) {
            StringBuilder message = new StringBuilder(String.format("%s ha roto su arma.%n", attacker.getName()));
            System.out.printf(message.toString());
            if (attacker.getFeedLogger() != null) {
                attacker.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
            }
            attacker.setWeapon(null);
        }
    }

    protected void applyMiss(BaseCharacter attacker, BaseCharacter target) {
        StringBuilder message = new StringBuilder(String.format("%s falla su %s contra %s.%n",
                attacker.getName(), name, target.getName()));
        System.out.printf(message.toString());
        if (attacker.getFeedLogger() != null) {
            attacker.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
        }
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

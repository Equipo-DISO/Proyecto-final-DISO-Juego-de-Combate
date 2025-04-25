package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

/**
 * Ataque ligero: menos daño, mayor probabilidad de acierto, menor consumo de maná
 */
public class LightAttackStrategy implements AttackStrategy {
    private static final Double DAMAGE_MULTIPLIER = 1.0;
    private static final Double HIT_CHANCE = 90.0; // Probabilidad de acierto 90%
    private static final Integer MANA_COST = 7;

    @Override
    public String getName() {
        return "Ataque Ligero";
    }

    @Override
    public Double calculateDamage(BaseCharacter attacker) {
        // Daño base reducido
        return (attacker.getWeapon().getDamage() + (attacker.getBaseAttack())) * DAMAGE_MULTIPLIER;
    }

    @Override
    public Boolean calculateHitSuccess(BaseCharacter attacker) {
        // Probabilidad de acierto
        return Math.random() * 100 <= HIT_CHANCE;
    }

    @Override
    public Integer calculateManaCost() {
        return MANA_COST;
    }

    @Override
    public void execute(BaseCharacter attacker, BaseCharacter target) {
        // Verificar maná
        if (attacker.getManaPoints() < MANA_COST) {
            System.out.println(attacker.getName() + " no tiene suficiente maná para atacar.");
            return;
        }

        // Consumir maná
        attacker.decreaseManaPoints(MANA_COST);

        // Verificar acierto
        if (!calculateHitSuccess(attacker)) {
            System.out.println("¡Oh no! " + attacker.getName() + " ha fallado su ataque ligero.");
            return;
        }

        // Calcular y aplicar daño
        double damage = calculateDamage(attacker);
        int finalDamage = (int)damage; //TODO: DamageCalculator.getInstance().applyDefense(damage, target);

        target.reduceHealth(finalDamage);
        System.out.println(attacker.getName() + " realiza un ataque ligero a " +
                target.getName() + " causando " + finalDamage + " de daño.");

        // Ataque con exito -> reducir durabilidad del arma
        attacker.getWeapon().decreaseDurability();

        // Verificar estado del objetivo
        if (!target.isAlive()) {
            System.out.println(target.getName() + " ha sido derrotado.");
        }
    }
}

package com.utad.proyectoFinal.characterSystem.characters.states.strategies;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

public class HeavyAttackStrategy implements AttackStrategy{

    private static final double DAMAGE_MULTIPLIER = 2;
    private static final double HIT_CHANCE = 60.0; // Probabilidad de acierto 90%
    private static final int MANA_COST = 10;
    // Constante extraída para coste de durabilidad
    private static final int DURABILITY_COST = 2;

    public String getName(){
        return "Heavy Attack !";
    }

    public Double calculateDamage(BaseCharacter attacker){
        return (attacker.getWeapon().getDamage() + (attacker.getBaseAttack())) * DAMAGE_MULTIPLIER;
    }

    public Boolean calculateHitSuccess(BaseCharacter attacker){
        return Math.random() * 100 <= HIT_CHANCE;
    }

    public Integer calculateManaCost(){
        return MANA_COST;
    }

    @Override
    public void execute(BaseCharacter attacker, BaseCharacter target) {
        Integer manaCost = calculateManaCost();
        if (attacker.getManaPoints() < manaCost) {
            System.out.printf("%s no tiene suficiente maná para atacar.%n", attacker.getName());
            return;
        }

        attacker.decreaseManaPoints(manaCost);

        Double damage = calculateDamage(attacker);
        Boolean hit = calculateHitSuccess(attacker);

        if(hit) {
            applyHit(attacker, target, damage);
        } else {
            applyMiss(attacker, target);
        }
    }

    private void applyHit(BaseCharacter attacker, BaseCharacter target, double damage) {
        target.getCurrentState().handleReceiveAttack(damage);
        attacker.getWeapon().decreaseDurability(DURABILITY_COST);
        System.out.printf("%s ataca a %s causando %d puntos de daño.%n",
            attacker.getName(), target.getName(), (int) damage);
        handleWeaponBreak(attacker);
    }

    private void applyMiss(BaseCharacter attacker, BaseCharacter target) {
        System.out.printf("%s ha evitado el ataque.%n", target.getName());
        // El maná ya fue descontado en execute()
    }

    private void handleWeaponBreak(BaseCharacter attacker) {
        if (attacker.getWeapon().getDurability() <= 0) {
            System.out.printf("%s ha roto su arma.%n", attacker.getName());
            attacker.setWeapon(null);
        }
    }
}
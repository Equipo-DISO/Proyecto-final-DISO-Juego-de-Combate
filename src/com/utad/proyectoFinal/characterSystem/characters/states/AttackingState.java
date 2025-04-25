package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

/**
 * Representa el estado neutral de un personaje
 */
public class AttackingState extends BaseState {

    AttackingState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {

        if (character.isAlive() && opponent.isAlive()) {
            // TODO: Replace with call to DamageCalculator
            Double damage = calcularDanio();
            opponent.getCurrentState().handleReceiveAttack(damage); // TODO: Replace with call to DamageCalculator
            System.out.println(character.getName() + " ataca a " + opponent.getName() +
                    " causando " + damage.intValue() + " puntos de daño.");
        }

        throw new UnsupportedOperationException("Work in progress");
    }

    @Override
    public String getName() {
        return "Attacking";
    }

    protected Double calcularDanio() {
        // Obtenemos el daño base del arma
        // TODO: Implement Weapon Class, then --> Double danioTotal = this.armaPersonaje.getDanio() * (1 + (this.ataque / 100));
//        if (this.armaPersonaje.getPrecision() < Math.random() * 100) {
//            danioTotal = 0.0; // No se ha acertado el golpe
//        }
//        return danioTotal;
        return character.getBaseAttack(); // TODO: Replace with call to DamageCalculator / Placeholder
    }
}
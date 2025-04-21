package characterSystem.characters.states;

import characterSystem.characters.BaseCharacter;

/**
 * Representa el estado neutral de un personaje
 */
public class AttackingState extends BaseState {

    AttackingState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {

        throw new UnsupportedOperationException("Work in progress");

       // if (character.isAlive() && opponent.isAlive()) {
            // TODO: Replace with call to DamageCalculator
//            opponent.receiveAttack(damage); // TODO: Replace with call to DamageCalculator
//            System.out.println(character.getName() + " ataca a " + opponent.getName() +
//                    " causando " + (int)damage + " puntos de daño.");
       // }
        // No cambia de estado, ya está en estado normal
    }

    @Override
    public String getName() {
        return "Attacking";
    }
}
package characterSystem.characters.states;

import characterSystem.characters.BaseCharacter;

public abstract class BaseState implements CharacterState {

    protected BaseCharacter character;

    BaseState(BaseCharacter character) {
        this.character = character;
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Acción no soportada en el estado actual.");
    }

    @Override
    public void handleDefense(BaseCharacter opponent) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Acción no soportada en el estado actual.");
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Acción no soportada en el estado actual.");
    }

    @Override
    public void handleMove(Object toBeReplacedByTileClass) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Acción no soportada en el estado actual.");
    }

    @Override
    public void updateState() {
        if (character.isAlive())
            character.setIdleState();
        else
            character.setDeadState();
    }

    @Override
    public void handleReceiveAttack(Double damage) {
        double finalDamage = damage;

        if (character.getCurrentState() instanceof DefendingState) {
            finalDamage = damage; // TODO: Shield Implementation -> * (1 - character.getShield.getDefense()/100.0);
            System.out.println(character.getName() + " se ha defendido y ha reducido el daño recibido");
        }

        if (character.getCurrentState() instanceof RetreatingState && character.isRetreatSuccessful()) {
            finalDamage = 0.0;
            System.out.println(character.getName() + " ha evitado el ataque con éxito");
        }

        int actualDamage = (int) (finalDamage - (finalDamage * character.getBaseDefense()/100.0));
        character.reduceHealth(actualDamage); // TODO: Should be managed by 'Calculator' class

        System.out.println(character.getName() + " ha recibido un ataque de " + (int)finalDamage +
                " puntos pero gracias a su defensa ha recibido " + actualDamage + " de daño");

        if (!character.isAlive()) {
            System.out.println(character.getName() + " ha sido derrotado en combate");
        }

        updateState();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return obj != null && getClass() == obj.getClass();
    }
}

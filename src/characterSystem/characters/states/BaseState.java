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
    public void handleReceiveAttack(BaseCharacter character, Double damage) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Acción no soportada en el estado actual.");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return obj != null && getClass() == obj.getClass();
    }
}

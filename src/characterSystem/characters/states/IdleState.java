package characterSystem.characters.states;


import characterSystem.characters.BaseCharacter;

public class IdleState extends BaseState {

    IdleState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
        throw new UnsupportedOperationException("Cambia de estado antes de realizar cualquier acción");
    }

    @Override
    public void handleDefense(BaseCharacter opponent) {
        throw new UnsupportedOperationException("Cambia de estado antes de realizar cualquier acción");
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        throw new UnsupportedOperationException("Cambia de estado antes de realizar cualquier acción");
    }

    @Override
    public void handleMove(Object toBeReplacedByTileClass) {
        throw new UnsupportedOperationException("Cambia de estado antes de realizar cualquier acción");
    }

    @Override
    public void updateState() {
        throw new UnsupportedOperationException("Cambia de estado antes de realizar cualquier acción");
    }

    @Override
    public void handleReceiveAttack(BaseCharacter character, Double damage) {
        throw new UnsupportedOperationException("Cambia de estado antes de realizar cualquier acción");
    }

    @Override
    public String getName() {
        return "Idle";
    }
}

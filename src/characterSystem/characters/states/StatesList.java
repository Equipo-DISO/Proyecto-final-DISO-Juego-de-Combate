package characterSystem.characters.states;

import characterSystem.characters.BaseCharacter;

public class StatesList {
    private final CharacterState[] states;
    
    public StatesList(BaseCharacter character) {
        if (character == null) {
            throw new IllegalArgumentException("El personaje no puede ser nulo");
        }
        
        states = new CharacterState[] {
                new IdleState(character),
                new AttackingState(character),
                new DefendingState(character),
                new RetreatingState(character),
                new DeadState(character)
        };
    }
    
    public CharacterState getIdleState() {
        return states[0];
    }
    
    public CharacterState getAttackingState() {
        return states[1];
    }
    
    public CharacterState getDefendingState() {
        return states[2];
    }
    
    public CharacterState getRetreatingState() {
        return states[3];
    }

    public CharacterState getDeadState() {
        return states[4];
    }

    public CharacterState getStateByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("El nombre del estado no puede ser nulo");
        }
        for (CharacterState state : states) {
            if (state.getName().equalsIgnoreCase(name)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Estado no encontrado: " + name);
    }
}
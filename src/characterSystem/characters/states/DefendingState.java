package characterSystem.characters.states;

import characterSystem.characters.BaseCharacter;

public class DefendingState extends BaseState {

    DefendingState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleDefense(BaseCharacter opponent) {
        // TODO: Implement the logic for handling defense
        throw new UnsupportedOperationException("Unimplemented method 'handleDefense' in DefendingState");
    }

    @Override
    public String getName() {
        return "Defending";
    }

}

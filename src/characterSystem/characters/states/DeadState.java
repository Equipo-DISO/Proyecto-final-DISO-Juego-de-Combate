package characterSystem.characters.states;

import characterSystem.characters.BaseCharacter;

public class DeadState extends BaseState {
    public DeadState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void updateState() {
        // No se puede actualizar el estado de un personaje muerto
        System.out.println("El personaje está muerto y no puede realizar ninguna acción.");
    }

    @Override
    public String getName() {
        return "Dead";
    }
}

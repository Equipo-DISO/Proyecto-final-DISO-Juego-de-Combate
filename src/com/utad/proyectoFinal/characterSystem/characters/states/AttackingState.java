package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;

import java.util.Scanner;

class AttackingState extends BaseState {

    private AttackStrategy currentStrategy;

    AttackingState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
        selectStrategy();
        if (currentStrategy != null) {
            currentStrategy.execute(character, opponent);
            updateState();
        }
    }

    private void selectStrategy() {
        // Lógica muy simple para testing, TODO: conectar a UI externa
        System.out.printf("%n%s: elige tipo de ataque (1) Ligero (2) Pesado ▶ ",
                character.getName());
        int option = new Scanner(System.in).nextInt();
        currentStrategy = option == 2
                ? new HeavyAttackStrategy()
                : new LightAttackStrategy();
    }

    @Override
    public void updateState() {
        // Comprobar si el personaje tiene poco maná después del ataque
        if (TiredState.shouldBeTired(character)) {
            character.setTiredState(); // cambiar a Tired si tiene poco maná
        } else {
            character.setIdleState(); // volver a Idle si tiene suficiente maná
        }
    }

    @Override
    public String getName() {
        return "Attacking";
    }
}

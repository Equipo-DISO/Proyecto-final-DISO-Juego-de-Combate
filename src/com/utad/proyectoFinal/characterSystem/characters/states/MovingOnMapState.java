package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

/**
 * Estado que representa cuando el personaje se mueve en el mapa.
 */
public class MovingOnMapState extends BaseState {

    // Ya no hay coste de maná para moverse

    public MovingOnMapState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
        System.out.printf("%s está moviéndose y no puede atacar en este momento.%n",
                character.getName());
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        System.out.printf("%s ya está moviéndose.%n",
                character.getName());
    }

    @Override
    public void handleMove(Object tile) {
        // Lógica de movimiento (a implementar cuando se tenga la clase Tile)
        System.out.printf("%s se ha movido a una nueva posición.%n", character.getName());

        // Si hay un enemigo en la casilla, atacar
        boolean enemyInTile = false; // TODO: Implementar cuando se tenga la clase Tile
        if (enemyInTile) {
            System.out.printf("%s ha encontrado un enemigo en la casilla.%n", character.getName());
            // Cambiar al estado de ataque
            character.transitionTo(character.getStates().getAttackingState());
        } else {
            // Actualizar estado
            updateState();
        }
    }

    @Override
    public void updateState() {
        // In testing mode, don't transition back to Idle automatically
        if (isTestingMode()) {
            return;
        }

        // Volver a Idle después de moverse
        character.transitionTo(character.getStates().getIdleState());
    }

    @Override
    public String getName() {
        return "MovingOnMap";
    }
}

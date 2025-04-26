package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

/**
 * Estado que representa cuando el personaje se mueve en el mapa.
 */
public class MovingOnMapState extends BaseState {

    // Coste de maná para moverse
    private static final int MOVE_MANA_COST = 3;

    MovingOnMapState(BaseCharacter character) {
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
        // Comprobar si tiene suficiente maná para moverse
        if (character.getManaPoints() < MOVE_MANA_COST) {
            System.out.printf("%s intenta moverse pero no tiene suficiente maná (%d requerido).%n",
                    character.getName(), MOVE_MANA_COST);
            // Si no tiene suficiente maná, pasar a estado cansado
            if (TiredState.shouldBeTired(character)) {
                character.setCurrentState(new TiredState(character));
            } else {
                character.setIdleState();
            }
            return;
        }

        // Consumir maná
        character.decreaseManaPoints(MOVE_MANA_COST);
        
        // Lógica de movimiento (a implementar cuando se tenga la clase Tile)
        System.out.printf("%s se ha movido a una nueva posición.%n", character.getName());
        
        // Si hay un enemigo en la casilla, atacar
        boolean enemyInTile = false; // TODO: Implementar cuando se tenga la clase Tile
        if (enemyInTile) {
            System.out.printf("%s ha encontrado un enemigo en la casilla.%n", character.getName());
            // Cambiar al estado de ataque
            character.setCurrentState(new AttackingState(character));
        } else {
            // Actualizar estado
            updateState();
        }
    }

    @Override
    public void updateState() {
        // Volver a Idle después de moverse
        character.setIdleState();
    }

    @Override
    public String getName() {
        return "MovingOnMap";
    }
}
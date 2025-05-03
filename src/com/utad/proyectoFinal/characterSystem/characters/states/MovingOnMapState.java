package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.mapa.GenericTile;

/**
 * Estado que representa cuando el personaje se mueve en el mapa.
 */
public class MovingOnMapState extends BaseState {


    public static final Integer MANA_RECOVERED = 5;
    
    public MovingOnMapState(BaseCharacter character) {
        super(character);
    }

    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        System.out.printf("%s está moviéndose y no puede atacar en este momento.%n",
                character.getName());
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        System.out.printf("%s ya está moviéndose.%n",
                character.getName());
    }

    @Override
    public void handleMove(GenericTile moveToTile) {
        // Lógica de movimiento (a implementar cuando se tenga la clase Tile)
        System.out.printf("%s se ha movido a una nueva posición.%n", character.getName());

        // Recuperar maná al moverse
        character.increaseManaPoints(MANA_RECOVERED);
        character.setCurrentPosition(moveToTile);
        // Actualizar estado
        updateState();
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

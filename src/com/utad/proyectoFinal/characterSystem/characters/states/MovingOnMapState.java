package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.mapa.GenericTile;

/**
 * Estado que representa a un personaje moviéndose en el mapa.
 * <p>
 * En este estado, el personaje dedica su turno a desplazarse a una nueva casilla del mapa.
 * Durante el movimiento, el personaje recupera una pequeña cantidad de maná (5 puntos),
 * pero no puede realizar otras acciones como atacar o retirarse.
 * </p>
 * <p>
 * Cuando un personaje se mueve, se actualiza su posición en el mapa y las referencias
 * de ocupación en las casillas de origen y destino.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class MovingOnMapState extends BaseState {

    /**
     * Cantidad de maná recuperado al moverse por el mapa.
     */
    public static final Integer MANA_RECOVERED = 7;
    
    /**
     * Constructor que inicializa el estado con una referencia al personaje.
     * 
     * @param character El personaje asociado a este estado
     */
    public MovingOnMapState(BaseCharacter character) {
        super(character);
    }

    /**
     * Sobreescribe handleAttack para evitar que un personaje moviéndose pueda atacar.
     * <p>
     * Muestra un mensaje indicando que la acción no está permitida.
     * </p>
     * 
     * @param opponent El oponente al que se intentaría atacar
     * @param attackStrategy La estrategia de ataque que se intentaría usar
     */
    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        System.out.printf("%s está moviéndose y no puede atacar en este momento.%n",
                character.getName());
    }

    /**
     * Sobreescribe handleRetreat para evitar que un personaje moviéndose pueda retirarse.
     * <p>
     * Muestra un mensaje indicando que ya está en movimiento.
     * </p>
     * 
     * @param opponent El oponente del que se intentaría huir
     */
    @Override
    public void handleRetreat(BaseCharacter opponent) {
        System.out.printf("%s ya está moviéndose.%n",
                character.getName());
    }

    /**
     * Ejecuta la acción de movimiento en el mapa.
     * <p>
     * Cambia la posición del personaje, actualizando las referencias de ocupación
     * en las casillas de origen y destino. También recupera una pequeña cantidad
     * de maná como beneficio adicional por el movimiento.
     * </p>
     * 
     * @param moveToTile La casilla de destino para el movimiento
     */
    @Override
    public void handleMove(GenericTile moveToTile) {
        // Lógica de movimiento (a implementar cuando se tenga la clase Tile)
        System.out.printf("%s se ha movido a una nueva posición.%n", character.getName());

        // Recuperar maná al moverse
        character.increaseManaPoints(MANA_RECOVERED);

        // actualizar la posicion
        character.getCurrentPosition().setOcupiedObject(null); 
        character.setCurrentPosition(moveToTile);

        moveToTile.setOcupiedObject(character);
        // Actualizar estado
        updateState();
    }

    /**
     * Actualiza el estado del personaje después de moverse.
     * <p>
     * Si no está en modo de pruebas, transiciona al estado Idle.
     * </p>
     */
    @Override
    public void updateState() {
        // In testing mode, don't transition back to Idle automatically
        if (isTestingMode()) {
            return;
        }

        // Volver a Idle después de moverse
        character.transitionTo(character.getStates().getIdleState());
    }

    /**
     * Obtiene el nombre del estado.
     * 
     * @return El nombre "MovingOnMap"
     */
    @Override
    public String getName() {
        return "MovingOnMap";
    }
}

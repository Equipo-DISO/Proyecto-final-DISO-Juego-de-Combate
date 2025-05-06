package com.utad.proyectoFinal.characterSystem.characters.states;


import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.ui.combat.Action;
import com.utad.proyectoFinal.mapa.GenericTile;

/**
 * Estado que representa a un personaje en reposo, preparado para realizar cualquier acción.
 * <p>
 * Este es el estado por defecto de un personaje, desde el cual puede iniciar cualquier acción
 * (atacar, curarse, moverse, etc.) siempre que tenga suficiente maná. También actúa como el
 * estado al que vuelven los personajes después de completar otras acciones.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public class IdleState extends BaseState {

    /**
     * Constructor que inicializa el estado con una referencia al personaje.
     * 
     * @param character El personaje asociado a este estado
     */
    IdleState(BaseCharacter character) {
        super(character);
    }

    /**
     * Maneja el intento de ataque de un personaje.
     * <p>
     * Primero verifica si el personaje tiene suficiente maná para atacar.
     * Si está cansado, no permite el ataque; de lo contrario, cambia al estado
     * de ataque y ejecuta el ataque con la estrategia proporcionada.
     * </p>
     * 
     * @param opponent El oponente a atacar
     * @param attackStrategy La estrategia de ataque a utilizar
     */
    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        // Comprobar si el personaje tiene suficiente maná para atacar
        if (checkAndTransitionToTiredIfNeeded()) {
            StringBuilder message = new StringBuilder(String.format("%s tiene poca energía y no puede atacar.%n", character.getName()));
            System.out.printf(message.toString());
            if (character.getFeedLogger() != null) {
                character.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
            }

        } else {
            if (opponent != super.character){
                // Cambiar al estado de ataque
                character.transitionTo(character.getStates().getAttackingState());
                character.getCurrentState().handleAttack(opponent, attackStrategy);
            }
            else {
                // Ocurre cuando el personaje intenta atacarse a sí mismo
                // No deberia pasar, pero lo hace
                // He estado haciendo pruebas, y ha medio funcionado, pero
                // las he tenido que descartar xq se me han roto otras cosas peores
                StringBuilder message = new StringBuilder(String.format("%s no puede atacarse a sí mismo.%n", character.getName()));
                System.out.printf(message.toString());
                if (character.getFeedLogger() != null) {
                    character.getFeedLogger().addFeedLine(message.toString(), Action.BREAK);
                }
            }
        }
    }

    /**
     * Maneja el intento de retirada de un personaje.
     * <p>
     * Cambia al estado de retirada y ejecuta la acción de retirada.
     * </p>
     * 
     * @param opponent El oponente del cual se intenta huir
     */
    @Override
    public void handleRetreat(BaseCharacter opponent) {
        // Cambiar al estado de retirada
        character.transitionTo(character.getStates().getRetreatingState());
        character.getCurrentState().handleRetreat(opponent);
    }

    /**
     * Maneja el intento de movimiento de un personaje.
     * <p>
     * Cambia al estado de movimiento y ejecuta la acción de mover a la casilla especificada.
     * </p>
     * 
     * @param moveToTile La casilla de destino
     */
    @Override
    public void handleMove(GenericTile moveToTile) {
        // Cambiar al estado de movimiento en el mapa
        character.transitionTo(character.getStates().getMovingOnMapState());
        character.getCurrentState().handleMove(moveToTile);
    }

    /**
     * Maneja el intento de curación de un personaje.
     * <p>
     * Cambia al estado de curación y ejecuta la acción de curación.
     * </p>
     */
    @Override
    public void handleHeal() {
        // Cambiar al estado de curación
        character.transitionTo(character.getStates().getHealState());
        character.getCurrentState().handleHeal();
    }

    /**
     * Maneja el intento de recuperación de maná de un personaje.
     * <p>
     * Cambia al estado de ganancia de maná y ejecuta la acción de recuperación.
     * </p>
     */
    @Override
    public void handleGainMana() {
        // Cambiar al estado de ganancia de maná
        character.transitionTo(character.getStates().getGainManaState());
        character.getCurrentState().handleGainMana();
    }

    /**
     * Actualiza el estado del personaje.
     * <p>
     * Comprueba si el personaje ha muerto para cambiar al estado de muerte.
     * Si está vivo pero tiene poco maná, cambia al estado de cansancio.
     * </p>
     */
    @Override
    public void updateState() {
        // Check if character is dead first
        if (!character.isAlive()) {
            character.transitionTo(character.getStates().getDeadState());
            return;
        }

        // Comprobar si el personaje tiene poca energía
        checkAndTransitionToTiredIfNeeded();
    }

    /**
     * Maneja la recepción de un ataque.
     * <p>
     * Utiliza la implementación por defecto proporcionada por la clase BaseState.
     * </p>
     * 
     * @param damage La cantidad de daño recibido
     */
    @Override
    public void handleReceiveAttack(Double damage) {
        // Usar la implementación por defecto de BaseState
        super.handleReceiveAttack(damage);
    }

    /**
     * Obtiene el nombre del estado.
     * 
     * @return El nombre "Idle"
     */
    @Override
    public String getName() {
        return "Idle";
    }
}

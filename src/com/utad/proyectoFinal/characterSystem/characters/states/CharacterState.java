package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;

/**
 * Interfaz que define las transiciones de estado para las acciones de un personaje
 */
public interface CharacterState {
    /**
     * Ejecuta una acción de ataque y maneja la transición de estado
     *
     * @param opponent El oponente que recibe el ataque
     */
    void handleAttack(BaseCharacter opponent);

    /**
     * Ejecuta una acción de defensa y maneja la transición de estado
     *
     * @param opponent El oponente
     */
    void handleDefense(BaseCharacter opponent);

    /**
     * Ejecuta una acción de retirada y maneja la transición de estado
     *
     * @param opponent El oponente
     */
    void handleRetreat(BaseCharacter opponent);

    /**
     * Ejecuta una acción de movimiento y maneja la transición de estado
     *
     * @param toBeReplacedByTileClass Destino del movimiento
     */
    void handleMove(Object toBeReplacedByTileClass);

    /**
     * Actualiza el estado al final del turno
     */
    void updateState();

    /**
     * Gestiona el comportamiento al recibir un ataque en cualquier estado
     *
     * @param damage La cantidad de daño recibido
     */
    void handleReceiveAttack(Double damage);

    /**
     * Obtiene el nombre del estado
     * @return El nombre del estado
     */
    String getName();
}
package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.mapa.GenericTile;

/**
 * Interfaz que define las transiciones de estado para las acciones de un personaje
 */
public interface CharacterState {
    /**
     * Ejecuta una acción de ataque y maneja la transición de estado
     *
     * @param opponent       El oponente que recibe el ataque
     * @param attackStrategy
     */
    void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy);

    /**
     * Ejecuta una acción de retirada y maneja la transición de estado
     *
     * @param opponent El oponente
     */
    void handleRetreat(BaseCharacter opponent);

    /**
     * Ejecuta una acción de movimiento y maneja la transición de estado
     *
     * @param moveToTile Destino del movimiento
     */
    void handleMove(GenericTile moveToTile);

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
     * Gestiona el comportamiento al curarse
     */
    void handleHeal();

    /**
     * Gestiona el comportamiento al ganar maná
     */
    void handleGainMana();

    /**
     * Obtiene el nombre del estado
     * @return El nombre del estado
     */
    String getName();
}

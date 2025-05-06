package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.mapa.GenericTile;

/**
 * Interfaz que define las transiciones de estado para las acciones de un personaje.
 * <p>
 * Implementa el patrón State para modelar los diferentes estados y comportamientos de los personajes.
 * Cada estado concreto implementa esta interfaz y define cómo el personaje debe responder a diferentes
 * acciones según su estado actual (atacar, curarse, retirarse, etc.).
 * </p>
 * <p>
 * Los métodos handle* son los puntos de entrada para cada acción que puede realizar un personaje.
 * Cada implementación concreta de estado decide si la acción es válida en ese estado y cómo procesarla.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public interface CharacterState {
    /**
     * Ejecuta una acción de ataque y maneja la transición de estado.
     * <p>
     * Este método determina si el personaje puede realizar un ataque en su estado actual,
     * y si es posible, aplica la estrategia de ataque contra el oponente.
     * </p>
     *
     * @param opponent El oponente que recibe el ataque
     * @param attackStrategy La estrategia de ataque a utilizar
     */
    void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy);

    /**
     * Ejecuta una acción de retirada y maneja la transición de estado.
     * <p>
     * Este método determina si el personaje puede retirarse en su estado actual,
     * y si es posible, intenta escapar del combate con una probabilidad de éxito.
     * </p>
     *
     * @param opponent El oponente del cual se intenta huir
     */
    void handleRetreat(BaseCharacter opponent);

    /**
     * Ejecuta una acción de movimiento y maneja la transición de estado.
     * <p>
     * Este método determina si el personaje puede moverse en su estado actual,
     * y si es posible, lo mueve a la casilla especificada.
     * </p>
     *
     * @param moveToTile Destino del movimiento
     */
    void handleMove(GenericTile moveToTile);

    /**
     * Actualiza el estado al final del turno.
     * <p>
     * Este método se llama después de que un personaje complete una acción para
     * determinar si debe cambiar a otro estado (por ejemplo, volver a Idle después
     * de un ataque, o cambiar a Tired si tiene poco maná).
     * </p>
     */
    void updateState();

    /**
     * Gestiona el comportamiento al recibir un ataque en cualquier estado.
     * <p>
     * Este método procesa el daño recibido, aplica reducciones por equipo defensivo,
     * y actualiza la salud del personaje. También maneja la transición al estado Dead
     * si la salud llega a cero.
     * </p>
     *
     * @param damage La cantidad de daño recibido
     */
    void handleReceiveAttack(Double damage);

    /**
     * Gestiona el comportamiento al curarse.
     * <p>
     * Este método determina si el personaje puede curarse en su estado actual,
     * y si es posible, utiliza una poción para recuperar salud.
     * </p>
     */
    void handleHeal();

    /**
     * Gestiona el comportamiento al ganar maná.
     * <p>
     * Este método determina si el personaje puede recuperar maná en su estado actual,
     * y si es posible, aumenta sus puntos de maná.
     * </p>
     */
    void handleGainMana();

    /**
     * Obtiene el nombre del estado.
     * <p>
     * Este método devuelve un identificador de texto para este estado, útil para
     * depuración, registros y presentación en la interfaz de usuario.
     * </p>
     * 
     * @return El nombre del estado
     */
    String getName();
}

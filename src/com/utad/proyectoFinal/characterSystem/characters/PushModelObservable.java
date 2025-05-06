package com.utad.proyectoFinal.characterSystem.characters;

/**
 * Interfaz que define el comportamiento de un objeto observable según el patrón Observer (modelo Push).
 * <p>
 * Los objetos que implementan esta interfaz pueden notificar a sus observadores
 * cuando cuando el personaje muere.
 * Esta interfaz forma parte del sistema de notificación del contador de personajes
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public interface PushModelObservable {
    /**
     * Notifica a todos los observadores registrados que el personaje ha muerto.
     * Este método debe ser llamado cuando la salud del personaje llega a cero.
     */
    public void notifyDeathObservers();
}

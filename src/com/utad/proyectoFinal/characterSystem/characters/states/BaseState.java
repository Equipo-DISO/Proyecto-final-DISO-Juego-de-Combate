package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.gameManagement.Calculator;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.ui.combat.Action;
import test.com.utad.proyectoFinal.characterSystem.TestUtils;

/**
 * Clase abstracta base que implementa comportamientos comunes para todos los estados de personaje.
 * <p>
 * Esta clase proporciona implementaciones por defecto para los métodos de la interfaz {@code CharacterState},
 * donde la mayoría de las acciones no están permitidas por defecto (lanzando excepciones).
 * Los estados concretos deben sobrescribir los métodos relevantes para permitir acciones específicas.
 * </p>
 * <p>
 * También proporciona utilidades compartidas como la verificación del estado de cansancio y la gestión
 * de ataques recibidos, que son comportamientos comunes independientemente del estado.
 * </p>
 * 
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseState implements CharacterState {

    protected BaseCharacter character;

    /**
     * Verifica si el sistema está en modo de pruebas.
     * <p>
     * En modo de pruebas, algunas transiciones de estado automáticas están desactivadas
     * para facilitar la validación del comportamiento.
     * </p>
     * 
     * @return {@code true} si está en modo de pruebas, {@code false} en caso contrario
     */
    protected static boolean isTestingMode() {
        return TestUtils.isTestingMode();
    }

    /**
     * Constructor que inicializa el estado con una referencia al personaje.
     * 
     * @param character El personaje asociado a este estado
     */
    BaseState(BaseCharacter character) {
        this.character = character;
    }

    /**
     * Método auxiliar para comprobar si el personaje está cansado y cambiar al estado correspondiente.
     * <p>
     * Verifica si el nivel de maná del personaje está por debajo del umbral de cansancio,
     * y en ese caso, realiza la transición al estado Tired.
     * </p>
     * 
     * @return {@code true} si el personaje estaba cansado y se cambió de estado, {@code false} en caso contrario
     */
    protected boolean checkAndTransitionToTiredIfNeeded() {
        if (TiredState.shouldBeTired(character)) {
            character.transitionTo(character.getStates().getTiredState());
            return true;
        }
        return false;
    }

    /**
     * Implementación por defecto para manejar un ataque.
     * <p>
     * Por defecto, lanza una excepción indicando que el ataque no está permitido
     * en el estado actual. Los estados concretos que permiten ataques deben sobrescribir este método.
     * </p>
     * 
     * @param opponent El oponente que recibiría el ataque
     * @param attackStrategy La estrategia de ataque a utilizar
     * @throws UnsupportedOperationException si el ataque no está permitido en el estado actual
     */
    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Ataque no soportado en el estado actual. " + this.getClass().getSimpleName()   );
    }

    /**
     * Implementación por defecto para manejar una retirada.
     * <p>
     * Por defecto, lanza una excepción indicando que la retirada no está permitida
     * en el estado actual. Los estados concretos que permiten retiradas deben sobrescribir este método.
     * </p>
     * 
     * @param opponent El oponente del cual se intentaría huir
     * @throws UnsupportedOperationException si la retirada no está permitida en el estado actual
     */
    @Override
    public void handleRetreat(BaseCharacter opponent) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Retirada no soportada en el estado actual. " + this.getClass().getSimpleName());
    }

    /**
     * Implementación por defecto para manejar un movimiento.
     * <p>
     * Por defecto, lanza una excepción indicando que el movimiento no está permitido
     * en el estado actual. Los estados concretos que permiten movimientos deben sobrescribir este método.
     * </p>
     * 
     * @param moveToTile La casilla de destino del movimiento
     * @throws UnsupportedOperationException si el movimiento no está permitido en el estado actual
     */
    @Override
    public void handleMove(GenericTile moveToTile) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Movimiento no soportado en el estado actual. " + this.getClass().getSimpleName());
    }

    /**
     * Implementación por defecto para manejar una curación.
     * <p>
     * Por defecto, lanza una excepción indicando que la curación no está permitida
     * en el estado actual. Los estados concretos que permiten curaciones deben sobrescribir este método.
     * </p>
     * 
     * @throws UnsupportedOperationException si la curación no está permitida en el estado actual
     */
    @Override
    public void handleHeal() {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Curación no soportada en el estado actual. " + this.getClass().getSimpleName());
    }

    /**
     * Implementación por defecto para manejar una recuperación de maná.
     * <p>
     * Por defecto, lanza una excepción indicando que la recuperación de maná no está permitida
     * en el estado actual. Los estados concretos que permiten recuperación de maná deben sobrescribir este método.
     * </p>
     * 
     * @throws UnsupportedOperationException si la recuperación de maná no está permitida en el estado actual
     */
    @Override
    public void handleGainMana() {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Ganar maná no soportado en el estado actual. " + this.getClass().getSimpleName());
    }
    
    /**
     * Actualiza el estado del personaje dependiendo de su situación actual.
     * <p>
     * Esta implementación base verifica si el personaje está muerto para transicionar al estado Dead.
     * Si está vivo y no está en modo de pruebas, transiciona al estado Idle.
     * Los estados concretos pueden sobrescribir este comportamiento según sea necesario.
     * </p>
     */
    @Override
    public void updateState() {
        // In testing mode, don't transition automatically
        if (isTestingMode()) {
            // Only transition to DeadState if character is dead
            if (!character.isAlive()) {
                character.transitionTo(character.getStates().getDeadState());
            }
            return;
        }

        // Normal behavior when not in testing mode
        if (character.isAlive())
            character.transitionTo(character.getStates().getIdleState());
        else {
            character.transitionTo(character.getStates().getDeadState());

        }
    }

    /**
     * Implementación común para manejar la recepción de un ataque.
     * <p>
     * Este método calcula el daño final considerando la protección del casco,
     * actualiza la salud del personaje, gestiona el desgaste del equipamiento,
     * y notifica la muerte si el personaje pierde toda su salud.
     * </p>
     * 
     * @param damage La cantidad de daño base recibido
     */
    @Override
    public void handleReceiveAttack(Double damage) {
        double finalDamage = damage;

        // Si el personaje está retirándose y la retirada fue exitosa, evita el daño
        if (character.getCurrentState() instanceof RetreatingState && character.isRetreatSuccessful()) {
            finalDamage = 0.0;
            StringBuilder message = new StringBuilder(character.getName() + " ha evitado el ataque con éxito");
            System.out.println(message);
            if (character.getFeedLogger() != null) {
                character.getFeedLogger().addFeedLine(message.toString(), Action.PROTECTED);
            }
        } else {
            // Aplicar reducción por casco usando Calculator
            finalDamage = Calculator.getInstance().calculateHelmetReduction(character, finalDamage);
        }

        int actualDamage = (int) finalDamage;
        character.reduceHealth(actualDamage);

        if (character.getHelmet() != null) {
            StringBuilder damageMessage = new StringBuilder(character.getName() + " ha recibido un ataque pero gracias a su casco ha recibido "
             + actualDamage + " de daño");
            System.out.println(damageMessage);
            if (character.getFeedLogger() != null) {
                character.getFeedLogger().addFeedLine(damageMessage.toString(), Action.ATACK);
            }
        }

        if (character.getHelmet() != null) {
            character.getHelmet().decreaseDurability();
            if (character.getHelmet().getDurability() <= 0) {
                StringBuilder helmetBreakMessage = new StringBuilder("El casco de " + character.getName() + " se ha roto");
                System.out.println(helmetBreakMessage);
                if (character.getFeedLogger() != null) {
                    character.getFeedLogger().addFeedLine(helmetBreakMessage.toString(), Action.BREAK);
                }
                character.setHelmet(null);
            }
        }

        if (!character.isAlive()) {
            StringBuilder defeatMessage = new StringBuilder(character.getName() + " ha sido derrotado en combate");
            System.out.println(defeatMessage);
            if (character.getFeedLogger() != null) {
                character.getFeedLogger().addFeedLine(defeatMessage.toString(), Action.BREAK);
            }

            // Notificamos a los aqui porque si no los hilos de Java no notifican y dan problemas
            character.notifyDeathObservers();
        }

        updateState();
    }

    /**
     * Compara si este estado es igual a otro objeto.
     * <p>
     * Dos estados se consideran iguales si son de la misma clase,
     * independientemente del personaje asociado.
     * </p>
     * 
     * @param obj El objeto a comparar
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return obj != null && getClass() == obj.getClass();
    }
}

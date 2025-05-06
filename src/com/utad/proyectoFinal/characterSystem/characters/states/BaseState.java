package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.gameManagement.Calculator;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.ui.combat.Action;
import test.com.utad.proyectoFinal.characterSystem.TestUtils;

public abstract class BaseState implements CharacterState {

    protected BaseCharacter character;

    /**
     * Checks if we're in testing mode
     * @return true if in testing mode, false otherwise
     */
    protected static boolean isTestingMode() {
        return TestUtils.isTestingMode();
    }

    BaseState(BaseCharacter character) {
        this.character = character;
    }

    /**
     * Método auxiliar para comprobar si el personaje está cansado y cambiar al estado correspondiente
     * @return true si el personaje estaba cansado y se cambió de estado, false en caso contrario
     */
    protected boolean checkAndTransitionToTiredIfNeeded() {
        if (TiredState.shouldBeTired(character)) {
            character.transitionTo(character.getStates().getTiredState());
            return true;
        }
        return false;
    }

    @Override
    public void handleAttack(BaseCharacter opponent, AttackStrategy attackStrategy) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Ataque no soportado en el estado actual. " + this.getClass().getSimpleName()   );
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Retirada no soportada en el estado actual. " + this.getClass().getSimpleName());
    }


    @Override
    public void handleMove(GenericTile moveToTile) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Movimiento no soportado en el estado actual. " + this.getClass().getSimpleName());
    }

    @Override
    public void handleHeal() {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Curación no soportada en el estado actual. " + this.getClass().getSimpleName());
    }

    @Override
    public void handleGainMana() {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Ganar maná no soportado en el estado actual. " + this.getClass().getSimpleName());
    }
    
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return obj != null && getClass() == obj.getClass();
    }
}

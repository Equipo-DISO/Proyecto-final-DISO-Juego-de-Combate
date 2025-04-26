package com.utad.proyectoFinal.characterSystem.characters.states;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.tools.Calculator;

public abstract class BaseState implements CharacterState {

    protected BaseCharacter character;

    BaseState(BaseCharacter character) {
        this.character = character;
    }

    @Override
    public void handleAttack(BaseCharacter opponent) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Acción no soportada en el estado actual.");
    }

    @Override
    public void handleRetreat(BaseCharacter opponent) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Acción no soportada en el estado actual.");
    }


    @Override
    public void handleMove(Object toBeReplacedByTileClass) {
        // Implementación por defecto (puede ser vacía o lanzar una excepción)
        throw new UnsupportedOperationException("Acción no soportada en el estado actual.");
    }

    @Override
    public void updateState() {
        if (character.isAlive())
            character.setIdleState();
        else
            character.setDeadState();
    }

    @Override
    public void handleReceiveAttack(Double damage) {
        double finalDamage = damage;

        // Si el personaje está retirándose y la retirada fue exitosa, evita todo el daño
        if (character.getCurrentState() instanceof RetreatingState && character.isRetreatSuccessful()) {
            finalDamage = 0.0;
            System.out.println(character.getName() + " ha evitado el ataque con éxito");
        } else {
            // Aplicar reducción por casco usando Calculator
            finalDamage = Calculator.getInstance().calculateHelmetReduction(character, finalDamage);

            // Aplicar reducción por defensa base del personaje
            finalDamage = finalDamage - (finalDamage * character.getBaseDefense()/100.0);
        }

        int actualDamage = (int) finalDamage;
        character.reduceHealth(actualDamage);

        System.out.println(character.getName() + " ha recibido un ataque de " + damage.intValue() +
                " puntos pero gracias a su casco y defensa ha recibido " + actualDamage + " de daño");


        if (character.getHelmet() != null) {
            character.getHelmet().decreaseDurability();
            if (character.getHelmet().getDurability() <= 0) {
                System.out.println("El casco de " + character.getName() + " se ha roto");
                character.setHelmet(null);
            }
        }

        if (!character.isAlive()) {
            System.out.println(character.getName() + " ha sido derrotado en combate");
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

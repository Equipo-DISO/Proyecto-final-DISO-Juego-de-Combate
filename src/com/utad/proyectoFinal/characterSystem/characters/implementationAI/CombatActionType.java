package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

/**
 * Enum representing possible combat actions for bots
 * These correspond to the possible player actions in combat
 */
public enum CombatActionType {
    LIGHT_ATTACK(0),    // Ataque Ligero
    HEAVY_ATTACK(1),    // Ataque Potente
    HEAL(2),            // Curarse
    GAIN_MANA(3),       // Concentrarse
    RETREAT(4);         // Huir
    
    private final int actionCode;
    
    CombatActionType(int actionCode) {
        this.actionCode = actionCode;
    }
    
    /**
     * Get the action code to be used in CombatManager
     * @return integer code for this action
     */
    public int getActionCode() {
        return actionCode;
    }
} 
package com.utad.proyectoFinal.characterSystem.characters;

import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.characterSystem.tools.BaseWeapon;
import com.utad.proyectoFinal.mapa.GenericTile;

import java.awt.Image;

/**
 * Defines the public interface for characters in the combat system.
 * External systems should interact with characters through these methods
 * rather than directly manipulating states.
 */
public interface CombatCharacter {
    
    // Basic character information
    String getName();
    boolean isAlive();
    Integer getHealthPoints();
    Integer getManaPoints();
    Integer getMaxHealthPoints();
    Integer getMaxManaPoints();
    
    // Combat actions
    void attack(CombatCharacter target, AttackStrategy attackStrategy);
    boolean retreat(CombatCharacter opponent);
    void heal();
    void move(GenericTile moveToTile);
    
    // Equipment management
    void equipWeapon(BaseWeapon weapon);
    void equipHelmet(BaseHelmet helmet);
    BaseWeapon getWeapon();
    BaseHelmet getHelmet();

    // Upgrades
    void addHealthPotion();
    void addManaUpgrade();
    void addHealthUpgrade();


    // State management (might be internal, but useful for tests)
    String getCurrentStateName();

    /**
     * Returns the complete Image with all decorations applied
     * @return a complete Image of the character with all decorations
     */
    Image getCompleteImage();
}
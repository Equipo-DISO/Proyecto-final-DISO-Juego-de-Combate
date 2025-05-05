package com.utad.proyectoFinal.characterSystem.tools.items;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;

/**
 * Interfaz para items que pueden ser consumidos por un personaje
 */
public interface Consumable {
    /**
     * Consume el item, aplicando su efecto sobre el personaje
     * @param character Personaje que consume el item
     * @return true si se consumió con éxito, false en caso contrario
     */
    boolean consume(CombatCharacter character);
} 
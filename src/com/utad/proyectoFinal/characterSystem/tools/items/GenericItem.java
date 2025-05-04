package com.utad.proyectoFinal.characterSystem.tools.items;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.mapa.MapObject;

import java.awt.*;

/**
 * Clase abstracta base para todos los items consumibles.
 * Implementa interfaces comunes y define comportamiento básico.
 */
public abstract class GenericItem implements MapObject, Consumable {
    protected final String name;

    protected GenericItem(String name) {
        this.name = name;
    }

    /**
     * Obtiene el nombre del item.
     * 
     * @return El nombre del item
     */
    public String getName() {
        return name;
    }

    /**
     * Determina si el item puede ser consumido por un personaje.
     * 
     * @param character El personaje que intentará consumir el item
     * @return true si el personaje puede consumir el item, false en caso contrario
     */
    protected boolean canConsume(CombatCharacter character) {
        // Siempre se puede consumir si el personaje está vivo
        return character.isAlive();
    }

    /**
     * Obtiene la imagen que representa al item.
     * Cada subclase debe implementar su propia representación visual.
     * 
     * @return La imagen del item
     */
    @Override
    public abstract Image getImage();
}

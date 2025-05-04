package com.utad.proyectoFinal.characterSystem.tools.factory;

import com.utad.proyectoFinal.mapa.MapObject;

/**
 * Implementación de RandomItemProducer que utiliza RandomItemGenerator
 * para crear items aleatorios.
 */
public class RandomItemFactory implements RandomItemProducer {

    @Override
    public MapObject giveRandomObject() {
        // Usa directamente el método estático de RandomItemGenerator
        return RandomItemGenerator.createRandomItem();
    }
}

package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.mapa.ClosestEnemyStrategy;
import com.utad.proyectoFinal.mapa.ClosestLootStrategy;
import com.utad.proyectoFinal.mapa.PathFindingStrategy;

public enum BotActionType {
    LOOKING_FOR_ENEMY(new ClosestEnemyStrategy()), 
    LOOKING_FOR_ITEM(new ClosestLootStrategy());

    private PathFindingStrategy strategy;
    private BotActionType(PathFindingStrategy strategy) {
        this.strategy = strategy;
    }
    public PathFindingStrategy getStrategy() {
        return strategy;
    }
}

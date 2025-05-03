package com.utad.proyectoFinal.mapa;

import java.util.LinkedList;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.ImplementationAI.Bot;

public abstract class TileFactory 
{    
    protected Integer totalNumberOfTiles;
    protected Integer numberOfSpawns;
    protected LinkedList<Bot> bots;
    protected BaseCharacter player;

    public TileFactory(Integer tiles, Integer spawns, LinkedList<Bot> bots, BaseCharacter player)
    {
        this.totalNumberOfTiles = tiles;
        this.numberOfSpawns = spawns;
        this.bots = bots;
        this.player = player;
    }

    public abstract GenericTile createTile(Integer x, Integer y, Integer tileId);
    public abstract ObstacleTile creatileObstacle(Integer x, Integer y, Integer tileId);

    public abstract TileAbstract generateRandomTile(Integer x, Integer y, Integer tileId);
}

package com.utad.proyectoFinal.mapa;

public abstract class TileFactory 
{    
    protected Integer numberOfTiles;
    protected Integer numberOfSpawns;


    public TileFactory(Integer tiles, Integer spawns)
    {
        this.numberOfTiles = tiles;
        this.numberOfSpawns = spawns;
    }

    public abstract GenericTile createTile(Integer x, Integer y, Integer tileId, Integer nodeX, Integer nodeY);
    public abstract ObstacleTile creatileObstacle(Integer x, Integer y, Integer tileId, Integer nodeX, Integer nodeY);

    public abstract TileAbstract generateRandomTile(Integer x, Integer y, Integer tileId, Integer nodeX, Integer nodeY);
}

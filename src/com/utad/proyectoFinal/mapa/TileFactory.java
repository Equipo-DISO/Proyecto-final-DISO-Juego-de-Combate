package com.utad.proyectoFinal.mapa;

public abstract class TileFactory 
{    
    protected Integer totalNumberOfTiles;
    protected Integer numberOfSpawns;


    public TileFactory(Integer tiles, Integer spawns)
    {
        this.totalNumberOfTiles = tiles;
        this.numberOfSpawns = spawns;
    }

    public abstract GenericTile createTile(Integer x, Integer y, Integer tileId);
    public abstract ObstacleTile creatileObstacle(Integer x, Integer y, Integer tileId);

    public abstract TileAbstract generateRandomTile(Integer x, Integer y, Integer tileId);
}

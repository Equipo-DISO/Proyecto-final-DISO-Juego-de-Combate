package com.utad.proyectoFinal.mapa;

public class NormalTileFactory extends TileFactory
{

    public NormalTileFactory(Integer tiles, Integer spawns) 
    {
        super(tiles, spawns);
    }

    @Override
    public GenericTile createTile(Integer x, Integer y, Integer tileId) 
    {
        return new GenericTile(x, y, tileId);
    }

    @Override
    public ObstacleTile creatileObstacle(Integer x, Integer y, Integer tileId) 
    {
        return new ObstacleTile(x, y, tileId);
    }

    @Override
    public TileAbstract generateRandomTile(Integer x, Integer y, Integer tileId) 
    {
        return Math.random() < MapGenerator.DEFAULT_OBSTACLE_PROBABILITY ? this.creatileObstacle(x, y, tileId) : this.createTile(x, y, tileId);
    }
}

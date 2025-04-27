package com.utad.proyectoFinal.mapa;

public class NormalTileFactory extends TileFactory
{

    public NormalTileFactory(Integer tiles, Integer spawns) 
    {
        super(tiles, spawns);
    }

    @Override
    public GenericTile createTile(Integer x, Integer y, Integer tileId, Integer nodeX, Integer nodeY) 
    {
        return new GenericTile(x, y, tileId, nodeX, nodeY);
    }

    @Override
    public ObstacleTile creatileObstacle(Integer x, Integer y, Integer tileId, Integer nodeX, Integer nodeY) 
    {
        return new ObstacleTile(x, y, tileId, nodeX, nodeY);
    }

    @Override
    public TileAbstract generateRandomTile(Integer x, Integer y, Integer tileId, Integer nodeX, Integer nodeY) 
    {
        return Math.random() < MapGenerator.DEFAULT_OBSTACLE_PROBABILITY ? this.creatileObstacle(x, y, tileId, nodeX, nodeY) : this.createTile(x, y, tileId, nodeX, nodeY);
    }
}

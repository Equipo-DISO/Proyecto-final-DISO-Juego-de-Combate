package com.utad.proyectoFinal.mapa;

import com.utad.proyectoFinal.ui.SimplifiedImage;

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
        // TODO: hay que meter las factorias y los players aqui
        TileAbstract tile;
    
        Double spawnProbability = (double) super.numberOfSpawns / super.totalNumberOfTiles ;
        
        if (Math.random() < spawnProbability && super.numberOfSpawns > 0) 
        {
            tile = this.createTile(x, y, tileId);
            tile.setSpecialImage(new SimplifiedImage("Files/img/PinkGuy.png").generateImage());
            super.numberOfSpawns--;
        } 
        else 
        {
            tile = (Math.random() < MapGenerator.DEFAULT_OBSTACLE_PROBABILITY ? this.creatileObstacle(x, y, tileId) : this.createTile(x, y, tileId));
            
            if (Math.random() < MapGenerator.DEFAULT_LOOT_PROBABILITY && tile instanceof GenericTile) 
            {
                tile.setSpecialImage(new SimplifiedImage("Files/img/Pergamino.png").generateImage());
            }
        }
        
        super.totalNumberOfTiles--;
        return tile;
    }
}

package com.utad.proyectoFinal.mapa;

import java.util.LinkedList;


import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.characterSystem.tools.factory.RandomItemFactory;
import com.utad.proyectoFinal.gameManagement.PushModelObserver;


public class NormalTileFactory extends TileFactory
{

    private RandomItemFactory itemFactory;

    public NormalTileFactory(Integer tiles, Integer spawns, LinkedList<Bot> bots, BaseCharacter player, PushModelObserver obs) 
    {
        super(tiles, spawns, bots, player, obs);
        this.itemFactory = new RandomItemFactory();
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
        TileAbstract tile;
    
        Double spawnProbability = (double) super.numberOfSpawns / super.totalNumberOfTiles ;
        
        if (Math.random() < spawnProbability && super.numberOfSpawns > 0) 
        {
            tile = this.createTile(x, y, tileId);

            
            if (super.bots.isEmpty())
            {
                tile.setOcupiedObject(super.player);
                super.player.setCurrentPosition((GenericTile) tile);
                super.player.addObserver(super.obs);
            }
            else
            {
                BaseCharacter bot = super.bots.pop();
                
                if (bot != null)
                {
                    System.out.println(bot.getName());
                    bot.setCurrentPosition((GenericTile) tile);
                    tile.setOcupiedObject(bot);
                    bot.addObserver(super.obs);
                }
            }
           
            super.numberOfSpawns--;
        } 
        else 
        {
            tile = (Math.random() < MapGenerator.DEFAULT_OBSTACLE_PROBABILITY ? this.creatileObstacle(x, y, tileId) : this.createTile(x, y, tileId));
            
            if (Math.random() < MapGenerator.DEFAULT_LOOT_PROBABILITY && tile instanceof GenericTile) 
            {
                tile.setOcupiedObject(this.itemFactory.giveRandomObject());
            }
        }
        
        super.totalNumberOfTiles--;
        return tile;
    }
}

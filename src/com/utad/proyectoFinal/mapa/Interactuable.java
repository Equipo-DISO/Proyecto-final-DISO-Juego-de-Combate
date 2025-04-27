package com.utad.proyectoFinal.mapa;

public interface Interactuable 
{
    public void execute(TileAbstract currentTile, TileAbstract objectiveTile);
}




/*
 * 
 * class helmet implements Interactuable
 * 
 * 
 * execute(Tile t1, Tile t2)
 * {
 *              t2 -> dar objeto a player en t1
 *              borro el objeto del tile 2
 *              muevo el player de t1 a t2
 * }
 * 
 * 
 * class player implements Interactuable
 * {
 *  t1.player.fight(t2.player);
 *  
 * }
 */
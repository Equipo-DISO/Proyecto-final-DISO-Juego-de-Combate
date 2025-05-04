package com.utad.proyectoFinal.mapa;

import java.util.List;

public class ClosestEnemyStrategy implements PathFindingStrategy
{

    public ClosestEnemyStrategy()
    {
        super();
    }

    @Override
    public Integer getTargetTileId(GenericTile currentPos, List<TileAbstract> tiles) 
    {
        Integer targetTileId = currentPos.getTileId();
        Double closestDistance = Double.MAX_VALUE;

        for (TileAbstract t : tiles)
        {
            if (t instanceof GenericTile && t.isOcupiedByCharacter() && !t.equals(currentPos))
            {
                Double dist = TileGraph.distanceToTile(currentPos, t);
                if (dist < closestDistance)
                {
                    closestDistance = dist;
                    targetTileId = t.getTileId();
                }

            }
        }

        
        return targetTileId;
    }

}

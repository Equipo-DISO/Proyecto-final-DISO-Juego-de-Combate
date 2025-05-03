package com.utad.proyectoFinal.mapa;

import java.util.List;

public interface PathFindingStrategy 
{
    public Integer getTargetTileId(GenericTile currentPos, List<TileAbstract> tiles);
}

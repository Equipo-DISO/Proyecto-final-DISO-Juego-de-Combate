package com.utad.proyectoFinal.mapa;

import java.awt.Graphics2D;

public class ObstacleTile extends TileAbstract
{

    public ObstacleTile(Integer x, Integer y, Integer id) 
    {
        super(x, y, id);
    }

    @Override
    public void drawTile(Graphics2D graphics2d) 
    {
        throw new UnsupportedOperationException("Unimplemented method 'drawTile'");
    }

}

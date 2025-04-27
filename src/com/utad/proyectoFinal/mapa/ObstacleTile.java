package com.utad.proyectoFinal.mapa;

import java.awt.*;


public class ObstacleTile extends TileAbstract
{

    public ObstacleTile(Integer x, Integer y, Integer id) 
    {
        super(x, y, id);
    }

    @Override
    public void drawTile(Graphics2D graphics2d) 
    {
        super.createHexagon();
    }

    @Override
    public boolean contains(Integer mouseX, Integer mouseY) 
    {
        return false;
    }
}

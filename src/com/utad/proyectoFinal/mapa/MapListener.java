package com.utad.proyectoFinal.mapa;

import java.awt.event.*;
import java.awt.*;
import java.util.List;


public class MapListener extends MouseAdapter
{
    private List<TileAbstract> tiles;
    private MapGenerator map;

    public MapListener(MapGenerator m, List<TileAbstract> tiles) 
    {
        this.tiles = tiles;
        this.map = m;
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if (this.map.isDisabled()) { return; }

        Point p = e.getPoint();
        for (TileAbstract t : tiles) 
        {
            if (t.contains(p.x, p.y)) 
            {
                System.out.println("Has clicado la tile en: " + t.getTileId());
                break;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        if (this.map.isDisabled()) { return; }

        Integer mx = e.getX();
        Integer my = e.getY();

        for (TileAbstract t : tiles) 
        {
            boolean now = t.contains(mx, my);
            if (now != t.getIsHovered()) 
            {
                System.out.println("hovering " + t.getTileId());
                t.setHovered(now);
            }
        }

        this.map.updateRendering();
    }
}

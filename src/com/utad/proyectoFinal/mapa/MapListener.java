package com.utad.proyectoFinal.mapa;

import java.awt.event.*;
import java.awt.*;
import java.util.List;
import javax.swing.JPanel;

public class MapListener extends MouseAdapter
{
    private List<TileAbstract> tiles;
    private JPanel screen;

    public MapListener(JPanel s, List<TileAbstract> tiles) 
    {
        this.tiles = tiles;
        this.screen = s;
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
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
        
        screen.repaint();
    }
}

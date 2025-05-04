package com.utad.proyectoFinal.mapa;

import java.awt.event.*;
import java.awt.*;
import java.util.List;

import javax.swing.SwingUtilities;


public class MapListener extends MouseAdapter
{
    private List<TileAbstract> tiles;
    private MapGenerator map;
   
    private Point dragStart;
    private Integer dx = 0;
    private Integer dy = 0;


    public MapListener(MapGenerator m, List<TileAbstract> tiles) 
    {
        this.tiles = tiles;
        this.map = m;

        this.dragStart = null;
        this.dx = 0;
        this.dy = 0;
    }

    public void mousePressed(MouseEvent e) 
    {
        if (this.map.isDisabled()) return;
        this.dragStart = e.getPoint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        if (this.map.isDisabled() || this.dragStart == null) return;
        if (!SwingUtilities.isLeftMouseButton(e)) return;
        
        
        Point current = e.getPoint();
        this.dx = current.x - dragStart.x;
        this.dy = current.y - dragStart.y;
        
       
        this.map.moveViewport(-this.dx, -this.dy);
        
        this.dragStart = current;
        this.map.updateRendering();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        this.dragStart = null; // Limpiamos al soltar el mouse
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if (this.map.isDisabled()) { return; }

        Point p = adjustPoint(e.getPoint());
        for (TileAbstract t : tiles) 
        {
            if (t.contains(p.x, p.y)) 
            {
                this.map.moveToTile(this.map.getPlayer(), (GenericTile) t);
                //System.out.println("Has clicado la tile en: " + t.getTileId());
                break;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        if (this.map.isDisabled()) { return; }

        Point p = adjustPoint(e.getPoint());
        for (TileAbstract t : tiles) 
        {
            boolean now = t.contains(p.x, p.y);
            if (now != t.getIsHovered()) 
            {
                t.setHovered(now);
                //System.out.println("hovering " + t.getTileId());
            }
        }

        this.map.updateRendering();
    }

    private Point adjustPoint(Point original) 
    {        
        return new Point(original.x + this.map.getViewX(), original.y + this.map.getViewY());
    }
}

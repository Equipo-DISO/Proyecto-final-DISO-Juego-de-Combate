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
    private long clickTime;
    private Integer dx;
    private Integer dy;


    public MapListener(MapGenerator m, List<TileAbstract> tiles) 
    {
        this.tiles = tiles;
        this.map = m;

        this.dragStart = null;
        this.dx = 0;
        this.dy = 0;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (MapController.getDisableMap()) return;
        this.dragStart = e.getPoint();
        this.clickTime = e.getWhen();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        if (MapController.getDisableMap() || this.dragStart == null) return;
        if (!SwingUtilities.isRightMouseButton(e)) return;
        
        
        Point current = e.getPoint();
        this.dx = current.x - dragStart.x;
        this.dy = current.y - dragStart.y;
        
       
        this.map.moveViewport(-this.dx, -this.dy);
        
        this.dragStart = current;
        this.map.updateRendering();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragStart == null) return;

        if (SwingUtilities.isLeftMouseButton(e)){

            TileAbstract initialTile = null;
            Point p = adjustPoint(dragStart);
            for (TileAbstract t : tiles)
            { if (t.contains(p.x, p.y)) initialTile = t; }

            if (initialTile != null) 
            {
                if (e.getWhen() - clickTime < 200) 
                {
                    this.map.executeActionOnMove(this.map.getPlayer(), (GenericTile) initialTile);
                } 
                else // Si no, es un drag y comprobar
                {
                    Point p2 = adjustPoint(e.getPoint());
                    for (TileAbstract t : tiles)
                    {
                        if (t.contains(p2.x, p2.y) && t == initialTile){
                            this.map.executeActionOnMove(this.map.getPlayer(), (GenericTile) t);
                            break;

                        }
                    }
                }
            }
        }
        
        this.dragStart = null; // Limpiamos al soltar el mouse
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if (MapController.getDisableMap()) { return; }
        if (!SwingUtilities.isLeftMouseButton(e)) return;

        Point p = adjustPoint(e.getPoint());
        for (TileAbstract t : tiles) 
        {
            if (t.contains(p.x, p.y)) 
            {
                this.map.executeActionOnMove(this.map.getPlayer(), (GenericTile) t);
                // Uncomment para ver los tiles del pathfinding
                // this.map.pathFindingDebug(this.map.getPathToObjective(this.map.getPlayer().getCurrentPosition(), new ClosestLootStrategy()));
                //System.out.println("Has clicado la tile en: " + t.getTileId());
                break;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        if (MapController.getDisableMap()) { return; }

        Point p = adjustPoint(e.getPoint());
        for (TileAbstract t : tiles) 
        {
            boolean now = t.contains(p.x, p.y);
            if (now != t.getIsHovered()) 
            {
                t.setHovered(now);
                // Uncomment for debugging
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

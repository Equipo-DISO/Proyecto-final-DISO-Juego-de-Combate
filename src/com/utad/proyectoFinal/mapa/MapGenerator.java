package com.utad.proyectoFinal.mapa;

import javax.swing.*;
import java.awt.*;

import java.util.List;
import java.util.ArrayList;

public class MapGenerator extends JPanel 
{
    private List<GenericTile> tile;


    public MapGenerator() 
    {
        this.tile = new ArrayList<GenericTile>();
        this.tile.add(new GenericTile(100, 100, 1));
        this.tile.add(new GenericTile(165 + TileAbstract.HEXAGON_RADIOUS + 3, 110, 2));
        this.tile.add(new GenericTile(100 + TileAbstract.HEXAGON_RADIOUS + 3, 180, 3));
    }

    @Override
    public void paintComponent(Graphics g) 
    { 
        super.paintComponent(g);
       
        Graphics2D g2d = (Graphics2D) g;
        super.setBackground(new Color(90, 182, 180)); // agua

        for (TileAbstract t :  this.tile)
        {
            t.drawTile(g2d);
        }
       

    }
}

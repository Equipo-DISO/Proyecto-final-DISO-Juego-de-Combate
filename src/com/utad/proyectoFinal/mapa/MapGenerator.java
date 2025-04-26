package com.utad.proyectoFinal.mapa;

import javax.swing.*;
import java.awt.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class MapGenerator extends JPanel 
{
    private List<GenericTile> tile;


    public MapGenerator() 
    {
        this.tile = new ArrayList<GenericTile>();

        createHexGrid();
    }

    protected void createHexGrid() {
        double hexWidth = TileAbstract.HEXAGON_RADIOUS * 1.9;
        double hexHeight = TileAbstract.HEXAGON_RADIOUS * -1.1;
        
        int gridSize = 2;
        int centerX = 500;
        int centerY = 500;
        
        for (int q = -gridSize; q <= gridSize; q++) {
            int r1 = Math.max(-gridSize, -q - gridSize);
            int r2 = Math.min(gridSize, -q + gridSize);
            
            for (int r = r1; r <= r2; r++) {
               
                double x = centerX + hexWidth * (q + r/2.0);
                double y = centerY + hexHeight * r * 0.75;
                
                int tileX = (int) Math.round(x);
                int tileY = (int) Math.round(y);
                
                this.tile.add(new GenericTile(tileX, tileY, q + r + gridSize));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) 
    { 
        super.paintComponent(g);
       
        Graphics2D g2d = (Graphics2D) g;
        super.setBackground(new Color(90, 182, 180)); // agua

         this.tile.sort(Comparator.comparingInt(t -> t.posY));

        for (TileAbstract t :  this.tile)
        {
            t.drawTile(g2d);
        }
       

    }
}

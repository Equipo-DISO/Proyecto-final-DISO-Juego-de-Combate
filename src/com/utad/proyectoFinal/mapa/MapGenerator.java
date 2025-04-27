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
        setLayout(new BorderLayout());
        
        createHexGrid();
    }



    private void createHexGrid() 
    {
        // numeros magicos, no tocar
        Double hexWidth = TileAbstract.HEXAGON_RADIOUS * 1.86;       
        Double hexHeight = TileAbstract.HEXAGON_RADIOUS * 1.1;       
        
        Integer gridSize = 3;
        Integer centerX = 500;
        Integer centerY = 0;
        
        for (Integer q = -gridSize; q <= gridSize; q++) {
            Integer r1 = Math.max(-gridSize, -q - gridSize);
            Integer r2 = Math.min(gridSize, -q + gridSize);
            
            for (Integer r = r1; r <= r2; r++) 
            {
                // numeros magicos, no tocar
                Double x = centerX + (hexWidth * 0.55 * q);
                Double y = centerY + (hexHeight * (r + q * 0.3));
                

                Double isoX = x - y;
                Double isoY = (x + y) * 0.75;
                
                Integer tileX = (int) Math.round(isoX);
                Integer tileY = (int) Math.round(isoY);
                
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
      

        for (TileAbstract t : this.tile) 
        {
            t.drawTile(g2d);
        }

        //generateDebugLines(g2d);
    }

    private void generateDebugLines(Graphics2D g2d)
    {

        g2d.setColor(new Color(120, 0, 0));
        g2d.setStroke(new BasicStroke(1));

        for (Integer i = 0; i < this.tile.size(); i++)
        {
            for (Integer j = i+1; j < this.tile.size(); j++)
            {
                TileAbstract t1 = this.tile.get(i);
                TileAbstract t2 = this.tile.get(j);

               
                g2d.drawLine(t1.getPosX(), t1.getPosY(), t2.getPosX(), t2.getPosY());
            }
        }

    }
}
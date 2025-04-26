package com.utad.proyectoFinal.mapa;

import javax.swing.*;
import java.awt.*;

public class MapGenerator extends JPanel 
{
    private GenericTile tile;


    public MapGenerator() 
    {
        this.tile = new GenericTile(100, 100, 1);
    }

    @Override
    public void paintComponent(Graphics g) 
    { 
        super.paintComponent(g);
       
        Graphics2D g2d = (Graphics2D) g;
        super.setBackground(new Color(90, 182, 180)); // agua
        this.tile.drawTile(g2d);

    }
}

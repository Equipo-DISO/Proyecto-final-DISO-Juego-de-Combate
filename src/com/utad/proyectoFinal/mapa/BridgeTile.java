package com.utad.proyectoFinal.mapa;

import java.awt.*;


public class BridgeTile extends TileAbstract
{

    public static final Integer DEFAULT_RADIOUS = 25;
    public static final Integer DEFAULT_FACE_DEPTH = 20;

    public BridgeTile(Integer x, Integer y, Integer id) 
    {
        super(x, y + GenericTile.DEFAULT_FACE_DEPTH, id);
        super.radious = BridgeTile.DEFAULT_RADIOUS;
    }

    @Override
    public void drawTile(Graphics2D graphics2d) 
    {
        super.createHexagon();

        Point[] topPoints = new Point[6];
        for (Integer i = 0; i < 6; i++) 
        {
            topPoints[i] = new Point(super.hexagon.xpoints[i], super.hexagon.ypoints[i]);
        }

        Integer[][] faces = { {2, 3}, {0, 1}, {1, 2}, {5, 0} };

       
        Color[] rockColors = {
            new Color(120, 120, 120),  // Gris medio
            new Color(100, 100, 100),  // Gris oscuro
            new Color(140, 140, 140),  // Gris claro
            new Color(90, 90, 90)      // Gris más oscuro para sombras
        };

        // Dibujar las caras laterales con textura de roca
        for (Integer i = 0; i < faces.length; i++) 
        {
            Integer v1 = faces[i][0];
            Integer v2 = faces[i][1];

            Polygon side = new Polygon();
            side.addPoint(topPoints[v1].x, topPoints[v1].y);
            side.addPoint(topPoints[v2].x, topPoints[v2].y);
            side.addPoint(topPoints[v2].x, topPoints[v2].y + BridgeTile.DEFAULT_FACE_DEPTH);
            side.addPoint(topPoints[v1].x, topPoints[v1].y + BridgeTile.DEFAULT_FACE_DEPTH);

           
            graphics2d.setColor(rockColors[i % rockColors.length]);
            graphics2d.fillPolygon(side);

            
            graphics2d.setColor(rockColors[i % rockColors.length].darker());
            graphics2d.setStroke(new BasicStroke(1));
            graphics2d.drawPolygon(side);
        }


        graphics2d.setColor(new Color(150, 150, 150));  
        graphics2d.fillPolygon(super.hexagon);    
    }


    public void setRadious(Integer r) { super.radious = r; }
   
    @Override
    public boolean contains(Integer mouseX, Integer mouseY) 
    {
        return false;
    }
}


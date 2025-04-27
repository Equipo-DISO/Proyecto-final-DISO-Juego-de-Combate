package com.utad.proyectoFinal.mapa;

import java.awt.*;


public class GenericTile extends TileAbstract
{

    public static final Integer DEFAULT_FACE_DEPTH = 35;

    public GenericTile(Integer x, Integer y, Integer id, Integer nodeX, Integer nodeY) 
    {
        super(x, y, id, nodeX, nodeY);
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

        
        Integer[][] faces = { {2, 3}, {0, 1}, {1, 2},  {5, 0} };

 
        for (Integer i = 0; i < faces.length; i++)
        {
            Integer v1 = faces[i][0];
            Integer v2 = faces[i][1];

            
            Polygon side = new Polygon();
                
            side.addPoint(topPoints[v1].x, topPoints[v1].y);
            side.addPoint(topPoints[v2].x, topPoints[v2].y);
            
            side.addPoint(topPoints[v2].x, topPoints[v2].y + GenericTile.DEFAULT_FACE_DEPTH);
            side.addPoint(topPoints[v1].x, topPoints[v1].y + GenericTile.DEFAULT_FACE_DEPTH);
        

            graphics2d.setColor(new Color(117, 84, 66)); 
            graphics2d.fillPolygon(side);
        }

        DecoratingHexagon specialGrass = new DecoratingHexagon(super.posX, super.posY + 4, super.tileId, false, super.nodeX, super.nodeY);
        specialGrass.setColor(new Color(118, 184, 58));
        specialGrass.setStrokeWidth(6);
        specialGrass.setRadious(super.radious - 4);
        specialGrass.drawTile(graphics2d);

       
           
        graphics2d.setColor(new Color(135, 190, 88)); 
        graphics2d.fillPolygon(super.hexagon);

        if (super.getIsHovered())
        {
            graphics2d.setColor(new Color(120, 0, 0)); 
            graphics2d.setStroke(new BasicStroke(3)); 
            graphics2d.drawPolygon(super.hexagon);   
        }
    }

   
    @Override
    public boolean contains(Integer mouseX, Integer mouseY) 
    {
        return super.hexagon.contains(mouseX, mouseY);
    }


}

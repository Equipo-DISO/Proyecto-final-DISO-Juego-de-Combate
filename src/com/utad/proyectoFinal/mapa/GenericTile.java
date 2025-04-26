package com.utad.proyectoFinal.mapa;

import java.awt.*;


public class GenericTile extends TileAbstract
{

    public GenericTile(Integer x, Integer y, Integer id) 
    {
        super(x, y, id);
    }

    @Override
    public void drawTile(Graphics2D graphics2d) 
    {
        super.createHexagon();

        Integer depth = 20; 

        Point[] topPoints = new Point[6];
       
        for (Integer i = 0; i < 6; i++)
        {
            topPoints[i] = new Point(super.hexagon.xpoints[i], super.hexagon.ypoints[i]);
        }

        
        Integer[][] faces = { {0, 1}, {1, 2},  {5, 0} };

        for (Integer[] face : faces)
        {
            Integer v1 = face[0];
            Integer v2 = face[1];

            Polygon side = new Polygon();
            side.addPoint(topPoints[v1].x, topPoints[v1].y);
            side.addPoint(topPoints[v2].x, topPoints[v2].y);
            side.addPoint(topPoints[v2].x, topPoints[v2].y + depth);
            side.addPoint(topPoints[v1].x, topPoints[v1].y + depth);

            graphics2d.setColor(new Color(117, 84, 66)); 
            graphics2d.fillPolygon(side);
        }


        graphics2d.setColor(new Color(149, 211, 97)); 
        graphics2d.fillPolygon(super.hexagon);

        graphics2d.setStroke(new BasicStroke(4));
        graphics2d.setColor(new Color(130, 204, 66)); 
        graphics2d.drawPolygon(super.hexagon);

		
		// Dibujos especiales
		// if (super.isHovered) 
        // {
	    //     // Agregar borde rojo más grueso para indicar hover
	    //     graphics2d.setStroke(new BasicStroke(3)); 
	    //     graphics2d.setColor(new Color(108,70,49));          
	    //     graphics2d.drawPolygon(super.hexagon);    
	    //     graphics2d.setStroke(new BasicStroke(1)); 
	    // }
    }

}

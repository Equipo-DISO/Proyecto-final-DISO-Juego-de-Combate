package com.utad.proyectoFinal.mapa;

import java.awt.*;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;


public class GenericTile extends TileAbstract
{

    public static final Integer DEFAULT_FACE_DEPTH = 35;
    public static final Color DEFAULT_COLOR = new Color(135, 190, 88);

    public Color color;

    public GenericTile(Integer x, Integer y, Integer id) 
    {
        super(x, y, id);
        this.color = GenericTile.DEFAULT_COLOR;
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
        

            if (i == 3)
            {
                graphics2d.setColor(new Color(100, 67, 49)); 
            }   
            else if (i == 1)
            {
                graphics2d.setColor(new Color(112, 79, 61)); 
            }
            else
            {
                graphics2d.setColor(new Color(117, 84, 66)); 
            }
            
            graphics2d.fillPolygon(side);
        }

      
        DecoratingHexagon specialGrass = new DecoratingHexagon(super.posX, super.posY + 4, super.tileId, false);
        specialGrass.setStrokeWidth(6);
        specialGrass.setRadious(super.radious - 4);
        
        if (super.isOcupiedByCharacter() && !((BaseCharacter) super.ocupiedObject).getEsControlado()) 
        { specialGrass.setColor(new Color(192, 43, 43, 150));}
        else { specialGrass.setColor(new Color(118, 184, 58)); }

        specialGrass.drawTile(graphics2d);
       
        if (super.isOcupiedByCharacter() && !((BaseCharacter) super.ocupiedObject).getEsControlado()) 
        {  graphics2d.setColor(new Color(202, 53, 43));}
        else {  graphics2d.setColor(this.color); }

        
        graphics2d.fillPolygon(super.hexagon);

        super.drawImage(graphics2d);

        if (super.getIsHovered())
        {
            graphics2d.setColor(new Color(180, 160, 160, 150));
            graphics2d.fillPolygon(super.hexagon);
            graphics2d.drawPolygon(super.hexagon);  
        }

       
    }

    public void setDebugColor(Color c)
    {
        this.color = c;
    }

   
    @Override
    public boolean contains(Integer mouseX, Integer mouseY) 
    {
        return super.hexagon != null && super.hexagon.contains(mouseX, mouseY);
    }


}

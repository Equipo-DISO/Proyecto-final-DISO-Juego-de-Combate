package com.utad.proyectoFinal.mapa;

import java.awt.*;


public class DecoratingHexagon extends TileAbstract
{

    public static final Color DEFAULT_COLOR = new Color(255, 255, 255, 120);
    public static final Integer DEFAULT_STROKE_WIDTH = 3;

    private Color color;
    private Integer strokeWidth;
    private Boolean fillInPolygon;


    public DecoratingHexagon(Integer x, Integer y, Integer id, Boolean fill) 
    {
        super(x, y, id);
        this.color = DecoratingHexagon.DEFAULT_COLOR;
        this.strokeWidth = DecoratingHexagon.DEFAULT_STROKE_WIDTH;
        this.fillInPolygon = fill;
    }

    

    @Override
    public void drawTile(Graphics2D graphics2d) 
    {
        super.createHexagon();
        graphics2d.setColor(this.color); 

        if (this.fillInPolygon)
        {
            graphics2d.fillPolygon(super.hexagon);
        }

        graphics2d.setStroke(new BasicStroke(this.strokeWidth)); 
        graphics2d.drawPolygon(super.hexagon);    
    }

    public void setStrokeWidth(Integer w)
    {
        this.strokeWidth = w;
    }

    public void setRadious(Integer r)
    {
        super.radious = r;
    }

    public void setColor(Color color) 
    {
        this.color = color;
    }

}

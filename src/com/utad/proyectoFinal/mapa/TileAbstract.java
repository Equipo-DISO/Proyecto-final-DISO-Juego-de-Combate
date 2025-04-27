package com.utad.proyectoFinal.mapa;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class TileAbstract 
{

    public static final Integer HEXAGON_RADIOUS = 70;
	public static final Integer HEXAGON_WIDTH = (int) (Math.sqrt(3) * TileAbstract.HEXAGON_RADIOUS);
	public static final Integer HEXAGON_HEIGHT = 2 * TileAbstract.HEXAGON_RADIOUS;

    
	public static final Integer IMAGE_WIDTH  = (int) (0.8 * TileAbstract.HEXAGON_WIDTH); 
	public static final Integer IMAGE_HEIGHT = (int) (0.8 * TileAbstract.HEXAGON_HEIGHT); 

    protected Integer radious;

    // position in screen
	protected Integer posX;
	protected Integer posY;

    // node position
    protected Integer nodeX;
    protected Integer nodeY;

    protected Polygon hexagon;
    protected Integer tileId;
	

	protected Object  ocupiedObject; 
	protected boolean isHovered;
	protected BufferedImage specialImage;


    public TileAbstract(Integer x, Integer y, Integer id, Integer nodeX, Integer nodeY)
    {
        this.posX = x;
        this.posY = y;

        this.nodeX = nodeX;
        this.nodeY = nodeY;

        this.tileId = id;
        this.hexagon = null;
        this.radious = TileAbstract.HEXAGON_RADIOUS;
        
        this.ocupiedObject = null;
        this.isHovered = false;
        this.specialImage = null;
    }

    protected void createHexagon()
	{
        this.hexagon = new Polygon();

        for (Integer vertix = 0; vertix < 6; vertix++)
        {
            Double vertixAngle = Math.toRadians((vertix * 60) + 40);
            
            Integer vertixPosX = (int) (this.posX + this.radious * Math.cos(vertixAngle));
            Integer vertixPosY = (int) (this.posY + (this.radious / 2) * Math.sin(vertixAngle));
                    
                        
            this.hexagon.addPoint(vertixPosX, vertixPosY);
        }
	}

    protected void drawImage(Graphics2D graphics2d)
	{
		Integer imageX = this.posX - TileAbstract.IMAGE_WIDTH  / 2;
		Integer imageY = this.posY - TileAbstract.IMAGE_HEIGHT / 2;
	   
	   try 
       {
            if (this.specialImage != null)
            {
                graphics2d.drawImage(this.specialImage, imageX, imageY, TileAbstract.IMAGE_WIDTH, TileAbstract.IMAGE_HEIGHT, null);
            }
	   } 
       catch (Exception e) 
       {
		   System.out.println("Error with loading tile image");
		   e.printStackTrace();
	   }
	}

    public abstract boolean contains(Integer mouseX, Integer mouseY);
    public abstract void drawTile(Graphics2D graphics2d);

    
    public Integer getNodeX() { return this.nodeX; }
    public Integer getNodeY() { return this.nodeY; }
    public Integer getPosX() { return this.posX; }
    public Integer getPosY() { return this.posY; }
    public Integer getTileId() { return this.tileId; }
    public boolean getIsHovered() { return this.isHovered; }

    
    public void setOcupiedObject(Object ocupiedObject) { this.ocupiedObject = ocupiedObject; }
    public void setHovered(boolean isHovered) { this.isHovered = isHovered; }
    public void setSpecialImage(BufferedImage specialImage) { this.specialImage = specialImage; }

    @Override
    public String toString() 
    {
        return "TileAbstract [posX=" + posX + ", posY=" + posY + ", hexagon=" + hexagon + ", tileId=" + tileId
                + ", ocupiedObject=" + ocupiedObject + ", isHovered=" + isHovered + ", specialImage=" + specialImage
                + "]";
    }


   

}


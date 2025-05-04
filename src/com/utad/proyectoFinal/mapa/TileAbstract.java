package com.utad.proyectoFinal.mapa;

import java.awt.*;
import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.tools.items.BaseHelmet;



public abstract class TileAbstract 
{

    public static final Integer HEXAGON_RADIOUS = 70;
	public static final Integer HEXAGON_WIDTH = (int) (Math.sqrt(3) * TileAbstract.HEXAGON_RADIOUS);
	public static final Integer HEXAGON_HEIGHT = 2 * TileAbstract.HEXAGON_RADIOUS;

    
	public static final Integer IMAGE_WIDTH  = (int) (0.43 * TileAbstract.HEXAGON_WIDTH); 
	public static final Integer IMAGE_HEIGHT = (int) (0.43 * TileAbstract.HEXAGON_HEIGHT); 

    protected Integer radious;

    // position in screen
	protected Integer posX;
	protected Integer posY;

    protected Polygon hexagon;
    protected Integer tileId;
	

	protected MapObject  ocupiedObject; 
	protected boolean isHovered;
	protected Image specialImage;


    public TileAbstract(Integer x, Integer y, Integer id)
    {
        this.posX = x;
        this.posY = y;

 
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
                Float scaleFactor = (float)TileAbstract.IMAGE_HEIGHT / (float)this.specialImage.getHeight(null);
                Integer scaledWidth = (int)(this.specialImage.getWidth(null) * scaleFactor);
                Integer scaledHeight = TileAbstract.IMAGE_HEIGHT;

                // yo bro u know what i mean dude
                // this guys dont know what br br patapim is
                // this gays arent the real gggsssssss broooo
                if (this.ocupiedObject instanceof BaseHelmet)
                {
                    graphics2d.drawImage(this.specialImage, imageX - 10, imageY, (int)(scaledWidth * 0.8), (int)(scaledHeight * 0.8), null);
                }
                else if (this.ocupiedObject instanceof BaseCharacter)
                {
                    graphics2d.drawImage(this.specialImage, imageX - 10, imageY - 25, (int)(scaledWidth * 1.2), (int)(scaledHeight * 1.2), null);
                }
                else
                {
                    graphics2d.drawImage(this.specialImage, imageX, imageY, scaledWidth, scaledHeight, null);
                }
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

    

    public Integer getPosX() { return this.posX; }
    public Integer getPosY() { return this.posY; }
    public Integer getTileId() { return this.tileId; }
    public boolean getIsHovered() { return this.isHovered; }
    public MapObject getOcupiedObject() { return this.ocupiedObject; }
    public boolean isOcupied() { return this.ocupiedObject != null; }

    public boolean isOcupiedByCharacter() { return isOcupied() && this.ocupiedObject instanceof BaseCharacter; }
    public boolean isOcupiedByLoot() { return isOcupied() && this.ocupiedObject instanceof MapObject && !isOcupiedByCharacter(); }
    
    public void setHovered(boolean isHovered) { this.isHovered = isHovered; }
    public void setOcupiedObject(MapObject ocupiedObject) 
    { 
        this.ocupiedObject = ocupiedObject; 

        if (ocupiedObject instanceof MapObject)
        {
            MapObject o = (MapObject) ocupiedObject;
            setSpecialImage(o.getImage());
        }
        else if (ocupiedObject == null)
        {
            setSpecialImage(null);
        }
    }

    protected void setSpecialImage(Image specialImage) { this.specialImage = specialImage; }

    @Override
    public String toString() 
    {
        return "TileAbstract [posX=" + posX + ", posY=" + posY + ", hexagon=" + hexagon + ", tileId=" + tileId
                + ", ocupiedObject=" + ocupiedObject + ", isHovered=" + isHovered + ", specialImage=" + specialImage
                + "]";
    }


   

}


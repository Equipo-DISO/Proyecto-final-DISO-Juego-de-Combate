package com.utad.proyectoFinal.mapa;

public class RenderParameters 
{
    private Integer offsetX;
    private Integer offsetY;
    private Double scaleX;
    private Double scaleY;
    
    public RenderParameters() 
    {
        this(0, 0, 1.0, 1.0);
    }

    public RenderParameters(Integer offsetX, Integer offsetY, Double scaleX, Double scaleY) 
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
 
    public Integer getOffsetX() { return this.offsetX; }
    public Integer getOffsetY() { return this.offsetY; }
    public Double getScaleX() { return this.scaleX; }
    public Double getScaleY() { return this.scaleY; }
}
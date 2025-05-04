package com.utad.proyectoFinal.mapa;

public class FpsDebugger 
{
    private Long lastFPSUpdate;
    private Integer fps;
    private Integer frameCount;
    
    public FpsDebugger()
    {
        this.lastFPSUpdate = 0L;
        this.fps = 0;
        this.frameCount = 0;
    }

    public void update() 
    {
        frameCount++;
        
        Long currentTime = System.currentTimeMillis();

        if(currentTime - lastFPSUpdate > 1000) 
        { 
            fps = frameCount;
            frameCount = 0;
            lastFPSUpdate = currentTime; 
        }
    }
    
    public int getFPS() 
    {
        return fps;
    }
}

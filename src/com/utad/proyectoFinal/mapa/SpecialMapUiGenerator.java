package com.utad.proyectoFinal.mapa;

import java.awt.*;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.gameManagement.PushModelObserver;
import com.utad.proyectoFinal.ui.SimplifiedImage;

public class SpecialMapUiGenerator implements PushModelObserver
{

    private Integer screenSizeX;
    private Integer screenSizeY;

    private Integer viewPortX;
    private Integer viewPortY;

    private Integer currentStandingPlayers;
    private final Integer generatedPlayers;

    public SpecialMapUiGenerator(Integer screenSizeX, Integer screenSizeY, Integer viewPortX, Integer viewPortY, Integer spawns)
    {
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;
        this.viewPortX = viewPortX;
        this.viewPortY = viewPortY;

        this.currentStandingPlayers = spawns;
        this.generatedPlayers = spawns;
    }

    public void drawPlayerHUD(Graphics2D g2d, boolean disableMap) 
    {
        createMouseMovementTip(g2d);
        createPlayerCounter(g2d);

        if (disableMap) { drawPendingScreen(g2d); }
    }

    public void setDrawInformation(Integer screenSizeX, Integer screenSizeY, Integer viewPortX, Integer viewPortY)
    {
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;
        this.viewPortX = viewPortX;
        this.viewPortY = viewPortY;
    }

    private void drawPendingScreen(Graphics2D g2d)
    {
        Composite oldComp = g2d.getComposite();
        Color oldColor   = g2d.getColor();

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(this.viewPortX, this.viewPortY, this.screenSizeX, this.screenSizeY);

             
        String text = "ON GOING FIGHT";
        FontMetrics fm = g2d.getFontMetrics();
        int tx = (this.viewPortX + this.screenSizeX - fm.stringWidth(text)) / 2;
        int ty = (this.viewPortY + this.screenSizeY - fm.getHeight()) / 2 + fm.getAscent();
        createText(g2d, tx - 150, ty, text, 50f);
    
        System.out.println(this.viewPortX + " " + this.viewPortY);

        g2d.setComposite(oldComp);
        g2d.setColor(oldColor);  
    }

   
    private void createMouseMovementTip(Graphics2D g2d)
    {
        Integer boxWidth = 250;
        Integer boxHeight = 60;

        Integer boxX = (this.viewPortX + ((this.screenSizeX - boxWidth / 2) / 2));
        Integer boxY = (this.viewPortY + this.screenSizeY - boxHeight - 20);


        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY); 
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        
        
       createText(g2d, boxX + 20, boxY + 35, "Hold left click to move", 20f);
       //createText(g2d, boxX + 20, boxY + 35, "FPS " + this.fps.getFPS(), 20f);
    }

    private void createPlayerCounter(Graphics2D g2d)
    {
        Integer boxWidth = 120;
        Integer boxHeight = 60;

        Integer boxX = this.viewPortX + this.screenSizeX - boxWidth + 5;
        Integer boxY = this.viewPortY + (-10);


        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY); 
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

       g2d.drawImage(new SimplifiedImage("Files/img/PeopleIcon.png").generateBufferedImage(), boxX + 15, boxY + 20, 30, 30, null);
       createText(g2d, boxX + 60, boxY + 40, this.currentStandingPlayers + "/" + this.generatedPlayers, 20f);
    }

    private void createText(Graphics2D g2d, Integer posX, Integer posY, String msg, Float fontSize)
    {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.setColor(Color.WHITE);
        
        Font  oldFont = g2d.getFont();
        Font font = oldFont.deriveFont(Font.BOLD, fontSize);
        g2d.setFont(font);
        g2d.drawString(msg, posX, posY);
        g2d.setFont(oldFont);
    }

    @Override
    public void characterHasDied(BaseCharacter character) 
    {
        this.currentStandingPlayers--;
    }

}

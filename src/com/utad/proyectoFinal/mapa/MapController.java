package com.utad.proyectoFinal.mapa;

import java.awt.*;


import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.gameManagement.PushModelObserver;
import com.utad.proyectoFinal.ui.SimplifiedImage;

public class MapController implements PushModelObserver
{

    private Integer screenSizeX;
    private Integer screenSizeY;

    private Integer viewPortX;
    private Integer viewPortY;

    private Integer currentStandingPlayers;
    private final Integer generatedPlayers;

    private static boolean DISABLE_MAP = false;

    public MapController(Integer screenSizeX, Integer screenSizeY, Integer viewPortX, Integer viewPortY, Integer spawns)
    {
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;
        this.viewPortX = viewPortX;
        this.viewPortY = viewPortY;

        this.currentStandingPlayers = spawns;
        this.generatedPlayers = spawns;
    }

    public void drawPlayerHUD(Graphics2D g2d) 
    {
        createMouseMovementTip(g2d);
        createPlayerCounter(g2d);

        if (MapController.DISABLE_MAP) { drawPendingScreen(g2d); }
    }

    public static void setDisableMap(boolean b) { MapController.DISABLE_MAP = b; }
    public static boolean getDisableMap() { return MapController.DISABLE_MAP; }

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
        float fontSize = 50f;
        g2d.setFont(g2d.getFont().deriveFont(fontSize));
        
      
        Integer centerX = this.viewPortX + (this.screenSizeX / 2);
        Integer centerY = this.viewPortY + (this.screenSizeY / 2);
        
        
        Integer textWidth = fm.stringWidth(text);
        Integer textHeight = fm.getHeight();
        Integer tx = centerX - (textWidth / 2);
        Integer ty = centerY - (textHeight / 2) + fm.getAscent();
        
        createText(g2d, tx - 150, ty, text, fontSize);

        g2d.setComposite(oldComp);
        g2d.setColor(oldColor);  
    }

   
    private void createMouseMovementTip(Graphics2D g2d)
    {
        Integer boxWidth = 290;
        Integer boxHeight = 75;

        Integer boxX = this.viewPortX + (this.screenSizeX - boxWidth) / 2;
        Integer boxY = this.viewPortY + this.screenSizeY - boxHeight - 20;

        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY); 
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

       
        createText(g2d, boxX + 24, boxY + 30, "Hold MBR to change view", 20f);
        createText(g2d, boxX + 18, boxY + 55, "MBL on tile to move player", 20f);
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

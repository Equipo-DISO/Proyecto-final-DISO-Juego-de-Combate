package com.utad.proyectoFinal.mapa;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class MapGenerator extends JPanel 
{
    public static final Double DEFAULT_OBSTACLE_PROBABILITY = 0.3d;
    public static final Double DEFAULT_LOOT_PROBABILITY = 0.25d;

    public static final Integer DEFAULT_GRID_SIZE = 3;

    private List<TileAbstract> tiles;
    private MapListener listener;
    private static MapGenerator instance;
    private Integer screenX = 0;
    private Integer screenY = 0;
    private boolean disableMap;

    private TileFactory factory;

    private Integer gridSize;

    private MapGenerator(Integer x, Integer y, Integer size) 
    {
        this.gridSize = size;

        this.factory = new NormalTileFactory(calculateTotalTiles(), calculateTotalTiles() / 4);

        this.tiles = new ArrayList<TileAbstract>();
        this.screenX = x;
        this.screenY = y;
        this.disableMap = false;

        createHexGrid();

        this.listener = new MapListener(this, this.tiles);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }

    public static MapGenerator getInstance(Integer screenX, Integer screenY, Integer size)
    {
        if (MapGenerator.instance == null)
        {
            MapGenerator.instance = new MapGenerator(screenX, screenY, size);
        }

        return MapGenerator.instance;
    }

    private void createHexGrid() 
    {
        // numeros magicos, no tocar
        Double hexWidth = TileAbstract.HEXAGON_RADIOUS * 1.86;       
        Double hexHeight = TileAbstract.HEXAGON_RADIOUS * 1.1;       
        
        Integer gridSize = MapGenerator.DEFAULT_GRID_SIZE;
        Integer centerX = this.screenX / 2;
        Integer centerY = this.screenY / 2;

 
       
        for (Integer q = -gridSize; q <= gridSize; q++) 
        {
            Integer r1 = Math.max(-gridSize, -q - gridSize);
            Integer r2 = Math.min(gridSize, -q + gridSize);
            
            for (Integer r = r1; r <= r2; r++) 
            {
                // numeros magicos, no tocar
                Double x = centerX + (hexWidth * 0.55 * q);
                Double y = centerY + (hexHeight * (r + q * 0.3));
                

                Double isoX = x - y;
                Double isoY = (x + y) * 0.75;
                
                Integer tileX = (int) Math.round(isoX);
                Integer tileY = (int) Math.round(isoY);
                
                TileAbstract tile = this.factory.generateRandomTile(tileX, tileY, this.tiles.size() + 1);
                this.tiles.add(tile);
            }
        }
    }
    

    @Override
    public void paintComponent(Graphics g) 
    { 
        super.paintComponent(g);


        Graphics2D g2d = (Graphics2D) g;
        super.setBackground(new Color(90, 182, 180)); // agua

        this.tiles.sort(Comparator.comparingInt(t -> t.posY));
      

        for (TileAbstract t : this.tiles) 
        {
            t.drawTile(g2d);
        }

        if (this.disableMap)
        {
            setPendingScreen(g2d);
            super.setEnabled(false);
        }
        else
        {
            super.setEnabled(true);
        }
       
        //generateDebugLines(g2d);
    }

    public void generateDebugLines(Graphics2D g2d)
    {

        g2d.setColor(new Color(120, 0, 0));
        g2d.setStroke(new BasicStroke(1));

        for (Integer i = 0; i < this.tiles.size(); i++)
        {
            for (Integer j = i+1; j < this.tiles.size(); j++)
            {
                TileAbstract t1 = this.tiles.get(i);
                TileAbstract t2 = this.tiles.get(j);

               
                g2d.drawLine(t1.getPosX(), t1.getPosY(), t2.getPosX(), t2.getPosY());
            }
        }

    }

    public void setPendingScreen(Graphics2D g2d)
    {
        Composite oldComp = g2d.getComposite();
        Color oldColor   = g2d.getColor();
        Font  oldFont    = g2d.getFont();

        // 1) Velo gris semitransparente
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, super.getWidth(), super.getHeight());

        // 2) Texto centrado
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.setColor(Color.WHITE);

        Font font = oldFont.deriveFont(Font.BOLD, 48f);
        g2d.setFont(font);

       
        String text = "ON GOING FIGHT";
        FontMetrics fm = g2d.getFontMetrics();
        int tx = (super.getWidth() - fm.stringWidth(text)) / 2;
        int ty = (super.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(text, tx, ty);

    
        g2d.setComposite(oldComp);
        g2d.setColor(oldColor);
        g2d.setFont(oldFont);
    
    }

    public void updateRendering() { this.repaint(); }
    public boolean isDisabled()   { return this.disableMap; }
    public Integer calculateTotalTiles() { return 1 + 3 * this.gridSize * (this.gridSize + 1); }
}
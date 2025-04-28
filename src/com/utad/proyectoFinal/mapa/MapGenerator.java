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

    private TileFactory factory;
    private List<TileAbstract> tiles;

    private MapListener listener;
    private static MapGenerator instance;

    private Integer screenX;
    private Integer screenY;
    private boolean disableMap;
    private Integer gridSize;

    private Graph graph;

    private MapGenerator(Integer x, Integer y, Integer size, Integer spawns) 
    {
        this.gridSize = size;

        this.factory = new NormalTileFactory(calculateTotalTiles(), spawns);

        this.graph = new Graph(calculateTotalTiles());

        this.screenX = x;
        this.screenY = y;
        this.tiles = createHexGrid();
        this.disableMap = false;


        this.listener = new MapListener(this, this.tiles);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }


    public static MapGenerator getInstance(Integer screenX, Integer screenY, Integer size, Integer spawns)
    {
        if (MapGenerator.instance == null)
        {
            MapGenerator.instance = new MapGenerator(screenX, screenY, size, spawns);
        }

        return MapGenerator.instance;
    }

    public List<TileAbstract> createHexGrid() 
    {
        List<TileAbstract> generatedMap = new ArrayList<>();

        // numeros magicos, no tocar
        Double hexWidth = TileAbstract.HEXAGON_RADIOUS * 1.86;       
        Double hexHeight = TileAbstract.HEXAGON_RADIOUS * 1.1;       
        
        Integer centerX = this.screenX / 2;
        Integer centerY = this.screenY / 2;

 
        for (Integer q = -this.gridSize; q <= this.gridSize; q++) 
        {
            Integer r1 = Math.max(-this.gridSize, -q - this.gridSize);
            Integer r2 = Math.min(this.gridSize, -q + this.gridSize);
            
            for (Integer r = r1; r <= r2; r++) 
            {
                // numeros magicos, no tocar
                Double x = centerX + (hexWidth * 0.55 * q);
                Double y = centerY + (hexHeight * (r + q * 0.3));
                

                Double isoX = x - y;
                Double isoY = (x + y) * 0.75;
                
                Integer tileX = (int) Math.round(isoX);
                Integer tileY = (int) Math.round(isoY);

                Integer newTileId = generatedMap.size();


                TileAbstract tile = this.factory.generateRandomTile(tileX, tileY, newTileId);
                generatedMap.add(tile);

                if (tile instanceof GenericTile)
                {
                    this.graph.connectNeighbors(generatedMap, (GenericTile) tile);
                }
            }
        }

        return generatedMap;
    }
    

    @Override
    public void paintComponent(Graphics g) 
    { 
        super.paintComponent(g);


        Graphics2D g2d = (Graphics2D) g;
        super.setBackground(new Color(90, 182, 180)); // agua

        this.tiles.sort(Comparator.comparingInt(t -> t.posY));
        this.tiles.forEach(t -> t.drawTile(g2d));
        generateDebugLines(g2d);
      

        if (this.disableMap)
        {
            drawPendingScreen(g2d);
            super.setEnabled(false);
        }
        else
        {
            super.setEnabled(true);
        }

        drawPlayerHUD(g2d);
    }



    private void drawPendingScreen(Graphics2D g2d)
    {
        Composite oldComp = g2d.getComposite();
        Color oldColor   = g2d.getColor();
        Font  oldFont    = g2d.getFont();

       
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, super.getWidth(), super.getHeight());

       
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

    private void drawPlayerHUD(Graphics2D g2d) 
    {
        Integer boxWidth = 120;
        Integer boxHeight = 60;

        Integer boxX = super.getWidth() - boxWidth + 5;
        Integer boxY = -10;



        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY); 
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

       //g2d.drawImage(new SimplifiedImage("Files/img/player.png").generateImage(30, 30), boxX, boxY, boxWidth, boxHeight, null);

       g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
       g2d.setColor(Color.WHITE);
        
       Font  oldFont = g2d.getFont();
       Font font = oldFont.deriveFont(Font.BOLD, 20f);
        g2d.setFont(font);
        g2d.drawString(calculateTotalTiles() + "/15", boxX + 55, boxY + 40);
        g2d.setFont(oldFont);
    }


    /**
     * 
     * 
     * @param initial Tile from where you are moving from
     * @param objective Destination tile
     * @return Returns boolean in the event of being a legal move (aka you can move there)
     */

    public boolean isLegalMove(TileAbstract initial, TileAbstract objective)
    {
        return (this.graph.getAdjacencyMatrix()[initial.getTileId()][objective.getTileId()] == 1 ? true : false);
    }

    /*
     * 
     *      MOVIDAS GRAFO
     * 
     */
    private void generateDebugLines(Graphics2D g2d)
    {

        g2d.setColor(new Color(120, 0, 0));
        g2d.setStroke(new BasicStroke(1));

        for (Integer i = 0; i < this.tiles.size(); i++)
        {
            for (Integer j = 0; j < this.tiles.size(); j++)
            {
                
                TileAbstract t1 = this.tiles.get(i);
                TileAbstract t2 = this.tiles.get(j);

                if (this.graph.getAdjacencyMatrix()[t1.getTileId()][t2.getTileId()] == 1) 
                {
                    g2d.drawLine(t1.getPosX(), t1.getPosY(), t2.getPosX(), t2.getPosY());
                }
            }
        }
    }


    public void setFactory(TileFactory f) { this.factory = f; }
    public void disableMap(boolean b) { this.disableMap = b; }
    public void updateRendering() { this.repaint(); }
    public boolean isDisabled()   { return this.disableMap; }
    public Integer calculateTotalTiles() { return 1 + 3 * this.gridSize * (this.gridSize + 1); }
}




// DEPRECATED


    // public void drawFogOfWar(Graphics2D g2d)
    // {
    //     Composite oldComp = g2d.getComposite();
    //     Color oldColor   = g2d.getColor();
    //     Font  oldFont    = g2d.getFont();

    //     Composite original = g2d.getComposite();

    //     g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f));
    //     g2d.setColor(new Color(30, 30, 30)); // Fog color
    //     g2d.fillRect(0, 0, getWidth(), getHeight());


    //     g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.20f));
    //     g2d.setColor(new Color(30, 30, 30, 10));
    //     g2d.fillOval(this.screenX / 2, 0, TileAbstract.HEXAGON_RADIOUS  * 3, TileAbstract.HEXAGON_RADIOUS * 3);

    //     g2d.setComposite(oldComp);
    //     g2d.setColor(oldColor);
    //     g2d.setFont(oldFont);
    //     g2d.setComposite(original);
    // }
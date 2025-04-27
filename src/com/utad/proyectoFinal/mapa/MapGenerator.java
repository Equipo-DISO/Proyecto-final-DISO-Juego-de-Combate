package com.utad.proyectoFinal.mapa;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class MapGenerator extends JPanel 
{
    public static final Double DEFAULT_OBSTACLE_PROBABILITY = 0.3d;
    public static final Double DEFAULT_LOOT_PROBABILITY = 0.25d;

    public static final Integer DEFAULT_GRID_SIZE = 3;

    private TileFactory factory;
    private List<TileAbstract> tiles;

    private MapListener listener;
    private static MapGenerator instance;

    private Integer screenX;
    private Integer screenY;
    private boolean disableMap;
    private Integer gridSize;

    private Integer[][] adjacencyMatrix;
     private Map<String, Integer> axialToIndex;

    private MapGenerator(Integer x, Integer y, Integer size) 
    {
        this.gridSize = size;

        this.factory = new NormalTileFactory(calculateTotalTiles(), calculateTotalTiles() / 4);

        this.adjacencyMatrix = new Integer[calculateTotalTiles()][calculateTotalTiles()];
        this.axialToIndex = new HashMap<>();
        initializeAdjacencyMatrix();

        this.screenX = x;
        this.screenY = y;
        this.tiles = createHexGrid();
        this.disableMap = false;


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

    public List<TileAbstract> createHexGrid() 
    {
        System.out.println("created");
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

                Integer newTileId = generatedMap.size() + 1;

                
                String axialKey = q + "," + r;
                axialToIndex.put(axialKey, newTileId);
                
                TileAbstract tile = this.factory.generateRandomTile(tileX, tileY, newTileId, q, r);
                generatedMap.add(tile);

                if (tile instanceof GenericTile)
                {
                    connectNeighbors(generatedMap, q, r, newTileId - 1);
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
       
        generateDebugLines(g2d);
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


    /*
     * 
     *      MOVIDAS GRAFO
     * 
     */

    public void generateDebugLines(Graphics2D g2d)
    {

        g2d.setColor(new Color(120, 0, 0));
        g2d.setStroke(new BasicStroke(1));

        for (Integer i = 0; i < this.tiles.size(); i++)
        {
            for (Integer j = i+1; j < this.tiles.size(); j++)
            {
                if (this.adjacencyMatrix[i][j] == 1) 
                {
                    TileAbstract t1 = this.tiles.get(i);
                    TileAbstract t2 = this.tiles.get(j);

                    g2d.drawLine(t1.getPosX(), t1.getPosY(), t2.getPosX(), t2.getPosY());
                }
            }
        }
    }

    private void connectNeighbors(List<TileAbstract> tiles, Integer nodeX, Integer nodeY, Integer currentId) 
    {
        
        Integer[][] directions = {
            {1, 0}, {1, -1}, {0, -1},
            {-1, 0}, {-1, 1}, {0, 1}
        };
        
        // Buscar vecinos en cada dirección
        for (Integer[] dir : directions) 
        {
            Integer neighborX = nodeX + dir[0];
            Integer neighborY = nodeY + dir[1];

            String neighborKey = neighborX + "," + neighborY;
        
            
            if (axialToIndex.containsKey(neighborKey)) 
            {
                Integer neighborIndex = axialToIndex.get(neighborKey);
                if (neighborIndex >= 0 && neighborIndex < adjacencyMatrix.length) 
                {
                    TileAbstract neighbor = tiles.get(neighborIndex);
                
                    if (neighbor instanceof GenericTile) 
                    {
                        adjacencyMatrix[currentId][neighborIndex] = 1;
                        adjacencyMatrix[neighborIndex][currentId] = 1;
                    }
                }
            }
        }
    }

     private void initializeAdjacencyMatrix() {
        for (int i = 0; i < adjacencyMatrix.length; i++) 
        {
            Arrays.fill(adjacencyMatrix[i], 0);
        }
    }

    public void setFactory(TileFactory f) { this.factory = f; }
    public void disableMap(boolean b) { this.disableMap = b; }
    public void updateRendering() { this.repaint(); }
    public boolean isDisabled()   { return this.disableMap; }
    public Integer calculateTotalTiles() { return 1 + 3 * this.gridSize * (this.gridSize + 1); }
}
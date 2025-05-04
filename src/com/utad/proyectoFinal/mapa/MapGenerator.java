package com.utad.proyectoFinal.mapa;


import javax.swing.*;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.ImplementationAI.Bot;
import com.utad.proyectoFinal.ui.SimplifiedImage;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;


public class MapGenerator extends JPanel 
{
    public static final Double DEFAULT_OBSTACLE_PROBABILITY = 0.4d;
    public static final Double DEFAULT_LOOT_PROBABILITY = 0.25d;


    private TileFactory factory;
    private List<TileAbstract> tiles;


    private MapListener listener;
    private static MapGenerator instance;

    private Integer screenX;
    private Integer screenY;
    private boolean disableMap;
    private Integer gridSize;

    private Integer viewportX;
    private Integer viewportY;

    private TileGraph graph;

    
    private BaseCharacter player;

    private MapGenerator(Integer x, Integer y, Integer size, Integer spawns, LinkedList<Bot> bots, BaseCharacter player) 
    {
        super();
        this.gridSize = size;

        this.player = player;

        this.factory = new NormalTileFactory(calculateTotalTiles(), spawns, bots, player);
        this.graph = new TileGraph(calculateTotalTiles());

        this.screenX = x;
        this.screenY = y;
        this.tiles = createHexGrid();
        this.disableMap = false;

        this.viewportX = 0;
        this.viewportY = 0;

        this.listener = new MapListener(this, this.tiles);
        this.addMouseListener(this.listener);
        this.addMouseMotionListener(this.listener);
    }

    public void displayMap()
    {
        JFrame frame = new JFrame("Hexágono 3D Testing");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(this);
        frame.setVisible(true);
        frame.setIconImage(new SimplifiedImage("Files/img/Logo.png").generateImage(100, 130));
    }

    public static MapGenerator getInstance(Integer screenX, Integer screenY, Integer size, Integer spawns, LinkedList<Bot> bots, BaseCharacter player)
    {
        if (MapGenerator.instance == null)
        {
            MapGenerator.instance = new MapGenerator(screenX, screenY, size, spawns, bots, player);
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

        this.graph.connectSubGraphs(generatedMap);

        return generatedMap;
    }
    

    @Override
    public void paintComponent(Graphics g) 
    { 
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        

        super.setBackground(new Color(90, 182, 180)); // agua

        g2d.translate(-this.viewportX, -this.viewportY);

        this.tiles.sort(Comparator.comparingInt(t -> t.posY));
        this.tiles.forEach(t -> t.drawTile(g2d));

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
        renderBridges(g2d);
    }


    private void drawPendingScreen(Graphics2D g2d)
    {
        Composite oldComp = g2d.getComposite();
        Color oldColor   = g2d.getColor();

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, super.getWidth(), super.getHeight());

             
        String text = "ON GOING FIGHT";
        FontMetrics fm = g2d.getFontMetrics();
        int tx = (this.viewportX + super.getWidth() - fm.stringWidth(text)) / 2;
        int ty = (this.viewportY + super.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        createText(g2d, tx - 150, ty, text, 50f);
    
        g2d.setComposite(oldComp);
        g2d.setColor(oldColor);  
    }

    private void drawPlayerHUD(Graphics2D g2d) 
    {
        createMouseMovementTip(g2d);
        createPlayerCounter(g2d);
    }

    private void createMouseMovementTip(Graphics2D g2d)
    {
        Integer boxWidth = 250;
        Integer boxHeight = 60;

        Integer boxX = (this.viewportX + ((super.getWidth() - boxWidth / 2) / 2));
        Integer boxY = (this.viewportY + super.getHeight() - boxHeight - 20);


        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY); 
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

       createText(g2d, boxX + 20, boxY + 35, "Hold left click to move", 20f);
    }

    private void createPlayerCounter(Graphics2D g2d)
    {
        Integer boxWidth = 120;
        Integer boxHeight = 60;

        Integer boxX = this.viewportX + super.getWidth() - boxWidth + 5;
        Integer boxY = this.viewportY + (-10);


        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.DARK_GRAY); 
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

       g2d.drawImage(new SimplifiedImage("Files/img/PeopleIcon.png").generateBufferedImage(), boxX + 15, boxY + 20, 30, 30, null);
       createText(g2d, boxX + 50, boxY + 40, "nig/15", 20f);
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

    private void renderBridges(Graphics2D g2d) 
    {
        Stroke originalStroke = g2d.getStroke();
        Color originalColor = g2d.getColor();
        
        
        float[] dashPattern = {6f, 6f};
        g2d.setColor(new Color(40, 30, 30, 120));
        g2d.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, 
            dashPattern, 0));

        for (Integer i = 0; i < this.tiles.size(); i++) 
        {
            for (Integer j = i + 1; j < this.tiles.size(); j++) 
            {
                TileAbstract t1 = this.tiles.get(i);
                TileAbstract t2 = this.tiles.get(j);
                
                if (this.graph.getAdjacencyMatrix()[t1.getTileId()][t2.getTileId()] == 2) 
                {
                    drawLine(g2d, t1, t2);
                }
            }
        }
        
        
        g2d.setStroke(originalStroke);
        g2d.setColor(originalColor);
    }

    private void drawLine(Graphics2D g2d, TileAbstract t1, TileAbstract t2)
    {
        Integer x1 = t1.getPosX();
        Integer y1 = t1.getPosY();
        Integer x2 = t2.getPosX();
        Integer y2 = t2.getPosY();

        Double dx = (double) x2 - x1;
        Double dy = (double) y2 - y1;
        Double length = Math.sqrt(dx * dx + dy * dy);

        if (length == 0) return; 
        
        Double ux = dx / length;
        Double uy = dy / length;

        
        Double offsetX = ux * TileAbstract.HEXAGON_RADIOUS * 0.9;  
        Double offsetY = uy * TileAbstract.HEXAGON_RADIOUS * 0.55; 
        
        Integer newX1 = (int) (x1 + offsetX);
        Integer newY1 = (int) (y1 + offsetY);
        Integer newX2 = (int) (x2 - offsetX);
        Integer newY2 = (int) (y2 - offsetY);

        g2d.drawLine(newX1, newY1, newX2, newY2);
    }


    /*
     * 
     *      MOVIDAS GRAFO
     * 
     */

    /**
    * @param character Character that desires to move
    * @param objective Destination tile
    */
    public void moveToTile(BaseCharacter character, GenericTile objective)
    {
        if (!this.graph.isLegalMove(character.getCurrentPosition(), objective)) { return; }
        
        //TODO logica de cambio de movidas ocupadas en los tiles
        if (objective.isOcupiedByCharacter())
        {

        }
        else if (objective.isOcupiedByLoot())
        {
            
            MapObject loot = objective.getOcupiedObject();
            objective.setOcupiedObject(null);
            // TODO: trabaja tonto character.addLoot??

            character.getCurrentPosition().setOcupiedObject(null);
            character.move(objective);
        }
        else
        {
            character.getCurrentPosition().setOcupiedObject(null);
            objective.setOcupiedObject(character);
            character.move(objective);
        }
    }

    /**
    * @param currentPos Current position
    * @param strategy Get your closest point of interest
    * @return returns list of tiles as a path
    */
    public List<GenericTile> getPathToObjective(GenericTile currentPos, PathFindingStrategy strategy)
    {
        return this.graph.pathFindingBFS(currentPos, strategy.getTargetTileId(currentPos, this.tiles), this.tiles);
    }

    public void moveViewport(Integer dx, Integer dy) 
    {
        int minX = -1200;
        int minY = -400;
        int maxX =  300;
        int maxY =  300;
        
        this.viewportX = Math.max(minX, Math.min(maxX, viewportX + dx));
        this.viewportY = Math.max(minY, Math.min(maxY, viewportY + dy));
    }

    public Integer getViewX() { return this.viewportX; }
    public Integer getViewY() { return this.viewportY; }
    public TileGraph getGraph() { return this.graph; }
    public void setFactory(TileFactory f) { this.factory = f; }
    public void disableMap(boolean b) { this.disableMap = b; }
    public void updateRendering() { this.repaint(); }
    public boolean isDisabled()   { return this.disableMap; }
    public Integer calculateTotalTiles() { return 1 + 3 * this.gridSize * (this.gridSize + 1); }
    public BaseCharacter getPlayer() { return this.player; }

   
}

// DEBUG

// private GenericTile getRandomTile()
//     {
//         TileAbstract t = this.tiles.get((int) (Math.random() * this.tiles.size()));


//         if (t instanceof GenericTile)
//         {
//             return (GenericTile) t;
//         }
//         else
//         {
//            return getRandomTile();
//         }
//     }

//     private void pathFindingTesting()
//     {
//         GenericTile startTile = getRandomTile();
//         GenericTile endTile = getRandomTile();
        

    
//         List<GenericTile> path = this.graph.pathFindingBFS(startTile, endTile.getTileId(), this.tiles);
        
//         for (GenericTile tile : path) 
//         {
//             tile.setDebugColor(Color.YELLOW);  
//         }
        
//         startTile.setDebugColor(Color.GREEN); 
//         endTile.setDebugColor(Color.RED);      
        
//         updateRendering();
//     }

//     private void generateDebugLines(Graphics2D g2d)
//     {

//         g2d.setColor(new Color(120, 0, 0));
//         g2d.setStroke(new BasicStroke(1));

//         for (Integer i = 0; i < this.tiles.size(); i++)
//         {
//             for (Integer j = i + 1; j < this.tiles.size(); j++)
//             {
                
//                 TileAbstract t1 = this.tiles.get(i);
//                 TileAbstract t2 = this.tiles.get(j);

//                 if (this.graph.getAdjacencyMatrix()[t1.getTileId()][t2.getTileId()] > 0) 
//                 {
//                     g2d.drawLine(t1.getPosX(), t1.getPosY(), t2.getPosX(), t2.getPosY());
//                 }
//             }
//         }
//     }


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
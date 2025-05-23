package com.utad.proyectoFinal.mapa;


import javax.swing.*;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.characterSystem.tools.items.Consumable;
import com.utad.proyectoFinal.gameManagement.GameContext;
import com.utad.proyectoFinal.ui.SimplifiedImage;
import com.utad.proyectoFinal.ui.combat.CombatInterface;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;


public class MapGenerator extends JPanel 
{
    public static final Double DEFAULT_OBSTACLE_PROBABILITY = 0.45d;
    public static final Double DEFAULT_LOOT_PROBABILITY = 0.30d;


    private TileFactory factory;
    private List<TileAbstract> tiles;


    private MapListener listener;
    private static MapGenerator instance;

    private Integer screenX;
    private Integer screenY;
    private Integer gridSize;

    private Integer viewportX;
    private Integer viewportY;

    private TileGraph graph;
    
    private BaseCharacter player;
    private MapController mapController;


    private MapGenerator(Integer x, Integer y, Integer size, Integer spawns, LinkedList<Bot> bots, BaseCharacter player) 
    {
        super();
        this.gridSize = size;


        this.player = player;

        this.screenX = x;
        this.screenY = y;
        this.viewportX = 0;
        this.viewportY = 0;

        this.mapController = new MapController(this.screenX, this.screenY, this.viewportX, this.viewportY, spawns);
        this.factory = new NormalTileFactory(calculateTotalTiles(), spawns, bots, player, this.mapController);
        this.graph = new TileGraph(calculateTotalTiles());

        
        this.tiles = createHexGrid();


        this.listener = new MapListener(this, this.tiles);
        this.addMouseListener(this.listener);
        this.addMouseMotionListener(this.listener);
    }

    public void displayMap()
    {
        JFrame frame = new JFrame("Juego de Combate");

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

    public static synchronized MapGenerator getInstance() throws Exception
    {

        if (MapGenerator.instance == null)
        {
            throw new Exception("Map not generated yet");
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

                if (tile instanceof GenericTile genericTile)
                {
                    this.graph.connectNeighbors(generatedMap, genericTile);
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
        this.mapController.setDrawInformation(super.getWidth(), super.getHeight(), this.viewportX, this.viewportY);

        this.tiles.sort(Comparator.comparingInt(t -> t.posY));
        this.tiles.forEach(t -> t.drawTile(g2d));


        this.mapController.drawPlayerHUD(g2d);
        super.setEnabled(MapController.getDisableMap());

        renderBridges(g2d);
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
        

        g2d.drawLine((int) (x1 + offsetX), (int) (y1 + offsetY), (int) (x2 - offsetX), (int) (y2 - offsetY));
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
    public void executeActionOnMove(BaseCharacter character, GenericTile objective)
    {
        if (!character.isAlive()) { return; }
        if (character.getCurrentPosition() == null) { return; }
        if (character.getCurrentPosition().equals(objective)) { return; }
        if (!this.graph.isLegalMove(character.getCurrentPosition(), objective)) { return; }
        
        if (objective.isOcupiedByCharacter())
        {
            BaseCharacter enemyCharacter = (BaseCharacter) objective.getOcupiedObject();

            // Skip interaction if either character is dead
            if (!character.isAlive() || !enemyCharacter.isAlive())
            {
                // For safety, ensure dead characters are removed from tiles
                if (!character.isAlive()) { 
                    character.getCurrentPosition().setOcupiedObject(null); 
                    character.setCurrentPosition(null); 
                }
                if (!enemyCharacter.isAlive()) { 
                    enemyCharacter.getCurrentPosition().setOcupiedObject(null); 
                    enemyCharacter.setCurrentPosition(null); 
                }
                return;
            }
            else if ((!character.getEsControlado() || !enemyCharacter.getEsControlado()) && !MapController.getDisableMap())
            {
                MapController.setDisableMap(true);
                updateRendering(); // Update rendering before opening combat interface

                CombatInterface combatInterface = new CombatInterface(character, enemyCharacter);
                combatInterface.showInterface();
            }
        }
        else if (objective.isOcupiedByLoot())
        {
            Consumable loot = (Consumable) objective.getOcupiedObject();
            objective.setOcupiedObject(null);
            loot.consume(character);
            character.move(objective);
        }
        else
        {
            character.move(objective);
        }

        // Only execute bot turn if no combat occurred
        if (!character.getEsControlado()) { GameContext.getInstance().botTurn(character); }
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
    public void updateRendering() { this.repaint(); }
    public Integer calculateTotalTiles() { return 1 + 3 * this.gridSize * (this.gridSize + 1); }
    public BaseCharacter getPlayer() { return this.player; }

    public void pathFindingDebug(List<GenericTile> path)
    {   
        removePathFindingDebug();     

        for (GenericTile tile : path) 
        {
            tile.setDebugColor(Color.YELLOW);  
        }
        
        path.getFirst().setDebugColor(Color.GREEN);
        path.getLast().setDebugColor(Color.RED);
        
        updateRendering();
    }
   
    public void removePathFindingDebug()
    {
        for (TileAbstract tile : this.tiles) 
        {
            if (tile instanceof GenericTile t)
            {
                t.setDebugColor(GenericTile.DEFAULT_COLOR); 
            }
             
        }
    }
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

package com.utad.proyectoFinal.mapa;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileGraph 
{

    private Integer[][] adjacencyMatrix;
    private Integer totalNodes;

    public TileGraph(Integer maxNodes)
    {
        this.totalNodes = maxNodes;
        this.adjacencyMatrix = new Integer[maxNodes][maxNodes];
        initializeAdjacencyMatrix();
    }


    private void initializeAdjacencyMatrix() 
    {
        for (int i = 0; i < this.totalNodes; i++) 
        {
            Arrays.fill(adjacencyMatrix[i], 0);
        }
    }

    private boolean isInRange(GenericTile initial, GenericTile target)
    { 
        return distanceToTile(initial, target) < 127.0d; // magic number, distancia maxima a la que estara un tile contiguo
    }

    private Double distanceToTile(TileAbstract initial, TileAbstract target)
    {
        Point centerInitial = new Point(initial.getPosX(), initial.getPosY());
        Point centerTarget  = new Point(target.getPosX() , target.getPosY());

       
        return centerInitial.distance(centerTarget); // magic number, distancia maxima a la que estara un tile contiguo
    }

    private GenericTile findClosestConnectedTile(List<TileAbstract> allTiles, GenericTile targetTile) 
    {
        GenericTile closestTile = null;
        double minDistance = Double.MAX_VALUE;
        
        for (TileAbstract tile : allTiles) 
        {
            if (tile instanceof GenericTile && !tile.equals(targetTile)) 
            {
                GenericTile genericTile = (GenericTile) tile;
                
                if (isTileConnectedOnce(allTiles, genericTile)) 
                {
                    double distance = distanceToTile(targetTile, genericTile);
                    
                    if (distance < minDistance) 
                    {
                        minDistance = distance;
                        closestTile = genericTile;
                    }
                }
            }
        }
        
        return closestTile;
    }


    public void findBridges(List<TileAbstract> allTiles) 
    {
        List<TileAbstract> aloneTiles = findAloneTiles(allTiles);
        
       
        if (aloneTiles.isEmpty()) { return; }
        

        for (TileAbstract aloneTile : aloneTiles) 
        {
            if (aloneTile instanceof GenericTile)
            {   
                GenericTile closestConnectedTile = findClosestConnectedTile(allTiles, (GenericTile) aloneTile);
            
                if (closestConnectedTile != null) 
                {
                    // Crear conexión bidireccional
                    this.adjacencyMatrix[aloneTile.getTileId()][closestConnectedTile.getTileId()] = 2;
                    this.adjacencyMatrix[closestConnectedTile.getTileId()][aloneTile.getTileId()] = 2;
                    
                }
            }
        }
    }

   
    public boolean isLegalMove(TileAbstract initial, TileAbstract objective)
    {
        return (this.adjacencyMatrix[initial.getTileId()][objective.getTileId()] > 0 ? true : false);
    }

    public void connectNeighbors(List<TileAbstract> tiles, GenericTile currentTile) 
    {
        for (TileAbstract tile : tiles)
        {
            if (tile instanceof GenericTile)
            {
                if (this.isInRange(currentTile, (GenericTile) tile))
                {
                    this.adjacencyMatrix[currentTile.getTileId()][tile.getTileId()] = 1;
                    this.adjacencyMatrix[tile.getTileId()][currentTile.getTileId()] = 1;
                }
            }           
        }
    }

    public List<TileAbstract> findAloneTiles(List<TileAbstract> tiles)
    {
        List<TileAbstract> returnTiles = new ArrayList<TileAbstract>();

        for (TileAbstract currentTile : tiles)
        {
            if (currentTile instanceof GenericTile)
            {
                if (!isTileConnectedOnce(tiles, currentTile) && !returnTiles.contains(currentTile))
                {
                    returnTiles.add(currentTile);
                    System.out.println(currentTile.getTileId());
                }
            }
        }

        return returnTiles;
    }

    public boolean isTileConnectedOnce(List<TileAbstract> tiles, TileAbstract currentTile)
    {
        boolean res = false;

        for (TileAbstract tileToSearch : tiles)
        {
            if (tileToSearch instanceof GenericTile && !currentTile.equals(tileToSearch) &&
                this.adjacencyMatrix[currentTile.getTileId()][tileToSearch.getTileId()] != null && 
                this.adjacencyMatrix[currentTile.getTileId()][tileToSearch.getTileId()] == 1
            )
            {
               res = true;
            }
        }

        return res;
    }

    public Integer[][] getAdjacencyMatrix() { return this.adjacencyMatrix; }
}

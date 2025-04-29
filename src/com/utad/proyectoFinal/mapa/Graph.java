package com.utad.proyectoFinal.mapa;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph 
{

    private Integer[][] adjacencyMatrix;
    private Integer totalNodes;

    public Graph(Integer maxNodes)
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

    private Double distanceToTile(GenericTile initial, GenericTile target)
    {
        Point centerInitial = new Point(initial.getPosX(), initial.getPosY());
        Point centerTarget  = new Point(target.getPosX() , target.getPosY());

       
        return centerInitial.distance(centerTarget); // magic number, distancia maxima a la que estara un tile contiguo
    }

    private GenericTile getClosestTile(List<TileAbstract> tiles, GenericTile currentTile)
    {
        Double closestDistance = Double.MAX_VALUE;
        GenericTile closestTile = null;

        for (TileAbstract tile : tiles)
        {
            if (tile instanceof GenericTile)
            {
                Double dist = distanceToTile(currentTile, (GenericTile) tile);

                if (dist < closestDistance)
                {
                    closestDistance = dist;
                    closestTile = (GenericTile) tile;

                }
            }
        }

        return closestTile;
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
        return (this.adjacencyMatrix[initial.getTileId()][objective.getTileId()] == 1 ? true : false);
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

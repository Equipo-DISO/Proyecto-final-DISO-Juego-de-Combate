package com.utad.proyectoFinal.mapa;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

   
    public boolean isLegalMove(GenericTile initial, GenericTile objective)
    {
        return this.adjacencyMatrix[initial.getTileId()][objective.getTileId()] > 0;
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

    public void connectSubGraphs(List<TileAbstract> allTiles) 
    {
        Map<Integer, List<GenericTile>> connectedComponents = findConnectedComponents(allTiles);
        
    
        if (connectedComponents.size() <= 1) { return; }

        for (List<GenericTile> component : connectedComponents.values()) 
        {
            connectComponentToNearest(allTiles, component, connectedComponents);
        }
        
        connectSubGraphs(allTiles);
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

       
        return centerInitial.distance(centerTarget);
    }


    // ---------------------------------
    //
    //
    //         Conexion de grafos
    //
    //
    // ---------------------------------


    private Map<Integer, List<GenericTile>> findConnectedComponents(List<TileAbstract> allTiles) 
    {
        Map<Integer, List<GenericTile>> components = new HashMap<>();
        List<GenericTile> genericTiles = new ArrayList<>();

        // Filtramos solo los GenericTile
        for (TileAbstract tile : allTiles) 
        {
            if (tile instanceof GenericTile) 
            {
                genericTiles.add((GenericTile) tile);
            }
        }

        boolean[] visited = new boolean[totalNodes];
        Integer componentId = 0;

        for (GenericTile tile : genericTiles) 
        {
            if (!visited[tile.getTileId()]) 
            {
                List<GenericTile> component = new ArrayList<>();
                dfs(tile, visited, component, genericTiles);
                components.put(componentId++, component);
            }
        }

        return components;
    }

    // https://en.wikipedia.org/wiki/Depth-first_search
    private void dfs(GenericTile current, boolean[] visited, List<GenericTile> component, List<GenericTile> allTiles) 
    {
        visited[current.getTileId()] = true;
        component.add(current);

        for (GenericTile neighbor : allTiles) 
        {
            if (!visited[neighbor.getTileId()] && adjacencyMatrix[current.getTileId()][neighbor.getTileId()] > 0) 
            {
                dfs(neighbor, visited, component, allTiles);
            }
        }
    }

    private void connectComponentToNearest(List<TileAbstract> allTiles, List<GenericTile> component, Map<Integer, List<GenericTile>> allComponents) 
    {
        GenericTile closestFrom = null;
        GenericTile closestTo = null;
        Double minDistance = Double.MAX_VALUE;

        // Buscamos en todos los otros componentes
        for (List<GenericTile> otherComponent : allComponents.values()) 
        {
            if (otherComponent.equals(component)) { continue; }

            // Buscamos el par más cercano entre este componente y el otro
            for (GenericTile tileFrom : component) 
            {
                for (GenericTile tileTo : otherComponent) 
                {
                    Double distance = distanceToTile(tileFrom, tileTo);
                    if (distance < minDistance) 
                    {
                        minDistance = distance;
                        closestFrom = tileFrom;
                        closestTo = tileTo;
                    }
                }
            }
        }

        
        if (closestFrom != null && closestTo != null) 
        {
            adjacencyMatrix[closestFrom.getTileId()][closestTo.getTileId()] = 2;
            adjacencyMatrix[closestTo.getTileId()][closestFrom.getTileId()] = 2;
        }
    }


    public List<GenericTile> pathFinding(GenericTile start, Integer targetTileId, List<GenericTile> allTiles) 
    {
        List<GenericTile> path = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        int[] found = new int[1];
        found[0] = 0;
        
        pathFindingRecursive(start, targetTileId, allTiles, path, visited, found);
        

        return path; 
    }

    private void pathFindingRecursive(GenericTile current, Integer targetTileId, List<GenericTile> allTiles, List<GenericTile> path, Set<Integer> visited, int[] found) 
    {
        path.add(current);
        visited.add(current.getTileId());
       
        if (found[0] == 1) { return; }

        if (current.getTileId().equals(targetTileId)) 
        {   
            found[0] = 1; 
            return; 
        }

        
        for (int neighborId = 0; neighborId < this.totalNodes && (found[0] == 0); neighborId++) 
        {
            if (neighborId >= allTiles.size()) continue;
            
            GenericTile neighbor = allTiles.get(neighborId);
            
            if (this.adjacencyMatrix[current.getTileId()][neighbor.getTileId()] > 0 && !visited.contains(neighbor.getTileId())) 
            {
                pathFindingRecursive(neighbor, targetTileId, allTiles, path, visited, found);
            }
        }
        
       
        if (found[0] == 0) 
        {
            path.remove(path.size() - 1);
        }
    }


   
    public Integer[][] getAdjacencyMatrix() { return this.adjacencyMatrix; }
}

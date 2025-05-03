package com.utad.proyectoFinal.mapa;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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


    public List<GenericTile> pathFindingBFS(GenericTile start, Integer targetTileId, List<TileAbstract> allTiles) 
    {
        LinkedList<List<GenericTile>> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        
        
        queue.offer(new ArrayList<>(Arrays.asList(start)));
        visited.add(start.getTileId());
        
        while (!queue.isEmpty()) 
        {
            List<GenericTile> currentPath = queue.poll();
            GenericTile lastTile = currentPath.get(currentPath.size() - 1);
            
  
            if (lastTile.getTileId().equals(targetTileId)) 
            {
                return currentPath;
            }
            
        
            for (int neighborId = 0; neighborId < this.totalNodes; neighborId++) 
            {

                TileAbstract neighbor = allTiles.get(neighborId);

                if (this.adjacencyMatrix[lastTile.getTileId()][neighbor.getTileId()] > 0 && !visited.contains(neighbor.getTileId())) 
                {
                    visited.add(neighbor.getTileId());
                    

                    List<GenericTile> newPath = new ArrayList<>(currentPath);
                    newPath.add((GenericTile) neighbor);
                    queue.offer(newPath);
                }
            }
        }
        
        return Collections.emptyList(); 
    }


   
    public Integer[][] getAdjacencyMatrix() { return this.adjacencyMatrix; }
}

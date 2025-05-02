package com.utad.proyectoFinal.mapa;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<GenericTile> pathFinding(GenericTile start, List<GenericTile> targets, List<TileAbstract> allTiles){

        Map<Integer, GenericTile> idToTile = new HashMap<>(); //hash que traduce Id's into ObjetoTile
        Boolean[] visited = new boolean[totalNodes];
        List<GenericTile> path = new ArrayList<>();

        for(TileAbstract tile: allTiles){
            //por cada tile del mapa, verifico si es un GenericTile
            if(tile instanceof GenericTile){
                //insertamos el tile en el hash
                idToTile.put(tile.getTileId(), tile);
            }
        }

        for(GenericTile target : targets){
            Arrays.fill(visited, false);
            path.clear();
            //prueba si hay un camino entre start y target y si lo encuentra lo aloja en path
            if(dfsPath(start, target, visited, path, idToTile)){
                return new ArrayList<>(path);
            }
        }
    }

    private Boolean dfsPath(GenericTile current, GenericTile target, Boolean[] visited, List<GenericTile> path, Map<Integer, GenericTile> idToTile) {

        visited[current.getTileId()] = true;
        path.add(current);

        if(current.equals(target)){
            //encontramos el objetivo
            return true;
        }

        for(Integer neighBorId = 0; neighBorId < totalNodes; neighBorId++){
            if(adjacencyMatrix[current.getTileId()][neighBorId] != null && adjacencyMatrix[current.getTileId()][neighborId] > 0 && !visited[neighBorId]){
                //filtramos si hay conexion > 0 entre actual y vecino y si no hemos visitado todavía el vecino
                GenericTile neighbor = idToTile.get(neighBorId);

                if(neighbor != null && dfsPath(neighbor, target, visited, path, idToTile)){
                    return true; //si el camino por el vecino funciona, devuelve true
                }
            }
        }

        path.remove(path.size() - 1); //backtrack
        return false;

    }
    public Integer[][] getAdjacencyMatrix() { return this.adjacencyMatrix; }
}

package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapGenerator;
import com.utad.proyectoFinal.mapa.TileGraph;

import java.util.*;

public class TypeBBotAI extends BotAI {

    @Override
    public void analyzeSituation(Bot bot) {
        //same para Type B bot
        // TODO: filtrarObjetivos
//        this.targets = MapGenerator.getInstance().filtrarObjetivos(bot, false);
    }


    @Override
    public void decideNextMove(GenericTile tile, Bot bot) {
        //same para Type B bot
        if (targets == null || targets.isEmpty()){
            return;
        }

        TileGraph graph = bot.getMap().getGraph();
        List<GenericTile> path = graph.pathFinding(tile, targets, bot.getMap().getTiles());


        if (path != null && path.size() > 1) {
            bot.setCurrentPosition(path.get(1)); // avanzar un paso
        }
    }

    @Override
    public void performAction(Bot bot) {
        //same para Type B bot
    }

    /*public List<GenericTile> filtrarObjetivos(Bot bot, boolean priorizarItems) {
        List<GenericTile> items = new ArrayList<>();
        List<GenericTile> enemigos = new ArrayList<>();

        for (TileAbstract tile : bot.getMap().getTiles()) {
            if (!(tile instanceof GenericTile)) continue;
            GenericTile gTile = (GenericTile) tile;

            if (gTile.hasItem()) {
                items.add(gTile);
            } else if (gTile.contieneEnemigo(bot)) {
                enemigos.add(gTile);
            }
        }

        List<GenericTile> result = new ArrayList<>();
        if (priorizarItems) {
            result.addAll(items);
            result.addAll(enemigos);
        } else {
            result.addAll(enemigos);
            result.addAll(items);
        }

        return result;
        }
     */

}

package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

import com.utad.proyectoFinal.mapa.*;

import java.util.ArrayList;
import java.util.List;

public class TypeABotAI extends BotAI {

    @Override
    public void analyzeSituation(Bot bot) {
        //lógica para analizar si movernos a objeto o atacar (path)
        this.targets = MapGenerator.getInstance(0,0, 0, 0).getPathToObjective(bot.getCurrentPosition(), new ClosestLootStrategy());
    }

    @Override
    public void decideNextMove(GenericTile tile, Bot bot) {
        //si el oponente está cerca huimos

    }

    @Override
    public void performAction(Bot bot) {
        //realizar la acción

    }

    public List<GenericTile> filtrarObjetivos(Bot bot, boolean priorizarItems) {
        List<GenericTile> items = new ArrayList<>();
        List<GenericTile> enemigos = new ArrayList<>(); //separar ambas listas permite aplicar lógica de prioridad después

        for (TileAbstract tile : bot.getMap().getTiles()) {
            if (!(tile instanceof GenericTile)) continue;
            GenericTile gTile = (GenericTile) tile;

            // TODO: filtrarObjetivos
//            if (gTile.hasItem()) {
//                items.add(gTile);
//            } else if (gTile.contieneEnemigo(bot)) {
//                enemigos.add(gTile);
//            }
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

}


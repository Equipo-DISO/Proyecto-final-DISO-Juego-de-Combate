package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

import com.utad.proyectoFinal.characterSystem.characters.states.AttackingState;
import com.utad.proyectoFinal.characterSystem.characters.states.MovingOnMapState;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapGenerator;
import com.utad.proyectoFinal.mapa.TileGraph;
import com.utad.proyectoFinal.mapa.ClosestEnemyStrategy;
import com.utad.proyectoFinal.mapa.ClosestLootStrategy;

import java.util.*;

public class TypeBBotAI extends BotAI {

    @Override
    public void analyzeSituation(Bot bot) {
        //same para Type B bot
        // TODO: pathfinding
        this.targets = MapGenerator.getInstance(0,0, 0, 0).getPathToObjective(bot.getCurrentPosition(), new ClosestEnemyStrategy());
    }


    @Override
    public void decideNextMove(GenericTile tile, Bot bot) {
        //same para Type B bot
        if(targets != null && targets.size() < 3){
            //si tenemos enemigo cerca me muevo
            bot.setBotActionType(BotActionType.MOVE);
        }else{

        }
    }

    @Override
    public void performAction(Bot bot) {
        //same para Type B bot
        switch(bot.getBotActionType()){
            case MOVE:
                //aquí move to
            case ATTACK:
                //bot.attack
            case NONE:
                System.out.println("Bot is being laz zZz");
            default:
                System.out.println("No action injected yet");
            break;
        }
        //reset de actions tras ejecución
        bot.setBotActionType(BotActionType.MOVE);
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

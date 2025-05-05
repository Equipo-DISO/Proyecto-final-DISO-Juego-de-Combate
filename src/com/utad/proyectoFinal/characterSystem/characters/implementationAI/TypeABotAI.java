package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.TiredState;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.mapa.*;

public class TypeABotAI extends BotAI {

    //private Integer counter = 0;
    @Override
    public void analyzeSituation(Bot bot) {
        //pillar el camino hasta el objetivo, con la estrategia propuesta
        this.targets = MapGenerator.getInstance(0,0, 0, 0, null, null).getPathToObjective(bot.getCurrentPosition(), new ClosestLootStrategy());
        MapGenerator.getInstance(1280, 720, 3, 2, null, null).pathFindingDebug(targets);


        this.currentStepTile = (this.targets.size() <= 1 ? targets.get(0) : targets.get(1)); 

    }

    @Override
    public void decideNextMove(GenericTile tile, Bot bot) {
        //si nos movemos seteamos actiontype move y si atacamos seteamos actiontype attack
        if(targets != null && !targets.isEmpty()) {
            if(currentStepTile.isOcupiedByCharacter()){
                bot.setBotActionType(BotActionType.ATTACK);
            }else{
                bot.setBotActionType(BotActionType.MOVE);
            }
        }

        if(bot.getCurrentState() instanceof TiredState){
            bot.setBotActionType(BotActionType.MANAREGEN);
        }
    }

    @Override
    public void performAction(Bot bot) {
        //realizar la acción
        // switch(bot.getBotActionType()){
        //     case MOVE:
        //         try {
        //             MapGenerator.getInstance().executeActionOnMove(bot, this.currentStepTile);
        //         } catch (Exception e) {
        //             System.out.println("no hay mapa aun");
        //         }
        //         break;
        //     case ATTACK:
        //         bot.attack((CombatCharacter) currentStepTile.getOcupiedObject(), new LightAttackStrategy());
        //         break;
        //     case NONE:
        //         System.out.println("None assigned yet");
        //         break;
        //     default:
        //         System.out.println("Bot is zZz ");
        // }

        try {
            MapGenerator.getInstance().executeActionOnMove(bot, this.currentStepTile);
        } catch (Exception e) {
            System.out.println("no hay mapa aun");
        }

        //this.counter++;

        // Si hemos llegado al final del camino, reiniciamos para calcular un nuevo objetivo desde 0
        //if (targets != null && counter >= targets.size()) {
            //this.counter = 0;
            //this.currentStepTile = null;  // Esto fuerza un nuevo análisis en el siguiente turno
        }
    }

/*  DEPRECATED

    public List<GenericTile> filtrarObjetivos(Bot bot, boolean priorizarItems) {
        List<GenericTile> items = new ArrayList<>();
        List<GenericTile> enemigos = new ArrayList<>(); //separar ambas listas permite aplicar lógica de prioridad después

        for (TileAbstract tile : bot.getMap().getTiles()) {
            if (!(tile instanceof GenericTile)) continue;
            GenericTile gTile = (GenericTile) tile;

            // TODO: filtrarObjetivos
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



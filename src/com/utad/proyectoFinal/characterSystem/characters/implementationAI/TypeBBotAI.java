package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.mapa.ClosestEnemyStrategy;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapGenerator;

public class TypeBBotAI extends BotAI {

    @Override
    public void analyzeSituation(Bot bot) {
        //same para Type B bot
        this.targets = MapGenerator.getInstance(0,0, 0, 0, null, null).getPathToObjective(bot.getCurrentPosition(), bot.getBotActionType());
        this.currentStepTile = (this.targets.size() <= 1 ? targets.get(0) : targets.get(1)); 
    }


    @Override
    public void decideNextMove(GenericTile tile, Bot bot) {
        if(targets != null && !targets.isEmpty()) {
            bot.setBotActionType(currentStepTile.isOcupiedByCharacter() ? BotActionType.LOOKING_FOR_ENEMY : BotActionType.LOOKING_FOR_ITEM);
        }
    }

    @Override
    public void performAction(Bot bot) {
        
        if(!bot.isLowEnergy()){
            try {
                MapGenerator.getInstance().executeActionOnMove(bot, this.currentStepTile);
            } catch (Exception e) {
                System.out.println("no hay mapa aun");
            }
        } else {
            bot.gainMana();
        
        }
    }
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



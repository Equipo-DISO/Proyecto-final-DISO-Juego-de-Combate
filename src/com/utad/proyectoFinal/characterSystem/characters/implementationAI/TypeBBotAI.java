package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapGenerator;
import com.utad.proyectoFinal.mapa.ClosestEnemyStrategy;

public class TypeBBotAI extends BotAI {

    private Integer counter = 0;
    @Override
    public void analyzeSituation(Bot bot) {
        //same para Type B bot
        this.targets = MapGenerator.getInstance(0,0, 0, 0, null, null).getPathToObjective(bot.getCurrentPosition(), new ClosestEnemyStrategy());
        this.currentStepTile = targets.get(counter);
    }


    @Override
    public void decideNextMove(GenericTile tile, Bot bot) {
        //same para Type B bot
        if(targets != null && !targets.isEmpty()) {
            if(currentStepTile.isOcupiedByCharacter()){
                bot.setBotActionType(BotActionType.ATTACK);
            }else{
                bot.setBotActionType(BotActionType.MOVE);
            }
        }
    }

    @Override
    public void performAction(Bot bot) {
        //same para Type B bot
        switch(bot.getBotActionType()){
            case MOVE:
                //aquí move to
                bot.move(currentStepTile);
                break;
            case ATTACK:
                //bot.attack
                bot.attack((CombatCharacter) currentStepTile.getOcupiedObject(), new HeavyAttackStrategy());
                break;
            case NONE:
                System.out.println("None assigned to bot" + bot.getId());
            default:
                System.out.println("No action injected yet");
            break;
        }

        this.counter++;

        if (targets != null && counter >= targets.size()) {
            counter = 0;
            currentStepTile = null;
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

}

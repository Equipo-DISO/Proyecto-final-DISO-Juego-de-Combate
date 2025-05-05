package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.TiredState;

import com.utad.proyectoFinal.mapa.*;

public class TypeABotAI extends BotAI {

    // Threshold values for decision making
    private static final double LOW_HEALTH_THRESHOLD = 0.35;


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

        //actualizar su posicion
        try {
            MapGenerator.getInstance().executeActionOnMove(bot, this.currentStepTile);
        } catch (Exception e) {
            System.out.println("no hay mapa aun");
        }
    }
    
    /**
     * Determines combat action for a TypeA bot (loot-seeking)
     * This bot type prioritizes:
     * 1. Gaining mana if low (they need it for light attacks)
     * 2. Healing if health is low
     * 3. Light attacks (more efficient)
     * 4. Retreating if very low health
     * @param bot The bot character
     *
     * @return The combat action type to perform
     */ 
    @Override
    public CombatActionType decideCombatAction(Bot bot) {
        // Check if mana is too low for even light attacks
        if (bot.getManaPoints() < DefaultAttributes.LOW_MANA_THRESHOLD) {
            return CombatActionType.GAIN_MANA;
        }
        
        // Check health and determine if healing is needed
        double healthRatio = (double) bot.getHealthPoints() / bot.getMaxHealthPoints();
        if (healthRatio < LOW_HEALTH_THRESHOLD && bot.getHpPotions() > 0) {
            return CombatActionType.HEAL;
        }
        
        // If health is very low, consider retreating
        if (healthRatio < 0.2) {
            return CombatActionType.RETREAT;
        }
        
        // Default to light attack (most mana efficient)
        return CombatActionType.LIGHT_ATTACK;
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



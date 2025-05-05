package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.TiredState;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.mapa.ClosestEnemyStrategy;
import com.utad.proyectoFinal.mapa.ClosestLootStrategy;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapGenerator;

public class TypeCBotAI extends BotAI {

    private static final double LOW_HEALTH_THRESHOLD = 0.3;
    //private int counter = 0;

    @Override
    public void analyzeSituation(Bot bot) {
        double healthRatio = bot.getHealthPoints() / (double) bot.getMaxHealthPoints();

        if (healthRatio < LOW_HEALTH_THRESHOLD || bot.getHelmet() == null || bot.getWeapon() == null) {
            // Buscar loot si está débil o sin equipamiento
            this.targets = MapGenerator.getInstance(0, 0, 0, 0, null, null)
                    .getPathToObjective(bot.getCurrentPosition(), new ClosestLootStrategy());
        } else {
            // Solo si está bien equipado y con vida, busca enemigos
            this.targets = MapGenerator.getInstance(0, 0, 0, 0, null, null)
                    .getPathToObjective(bot.getCurrentPosition(), new ClosestEnemyStrategy());
        }

        if (targets != null && !targets.isEmpty()) {
            this.currentStepTile = (this.targets.size() <= 1 ? targets.get(0) : targets.get(1)); 
        }

        if(bot.getCurrentState() instanceof TiredState){
            bot.setBotActionType(BotActionType.MANAREGEN);
        }
    }

    @Override
    public void decideNextMove(GenericTile tile, Bot bot) {
        if (targets != null && !targets.isEmpty()) {
            if (currentStepTile.isOcupiedByCharacter()) {
                CombatCharacter enemy = (CombatCharacter) currentStepTile.getOcupiedObject();
                // Huir si está débil o mal equipado
                if (bot.getHealthPoints() < bot.getMaxHealthPoints() * LOW_HEALTH_THRESHOLD || bot.getWeapon() == null || bot.getHelmet() == null) {

                    //Boolean success = bot.retreat(enemy); // acción de huida
                    //bot.setBotActionType(success ? BotActionType.RETREAT : BotActionType.NONE); //

                } else {
                    bot.setBotActionType(BotActionType.ATTACK);
                }
            } else {
                bot.setBotActionType(BotActionType.MOVE);
            }
        } else {
            bot.setBotActionType(BotActionType.NONE);
        }
    }

    @Override
    public void performAction(Bot bot) {
        // switch (bot.getBotActionType()) {
        //     case MOVE:
        //         bot.move(currentStepTile);
        //         break;
        //     case ATTACK:
        //         bot.attack((CombatCharacter) currentStepTile.getOcupiedObject(), new HeavyAttackStrategy());
        //         break;
        //     case NONE:
        //         System.out.println("Type C Bot " + bot.getId() + " skipped or retreated.");
        //         break;
        //     default:
        //         System.out.println("Unexpected action type for bot " + bot.getId());
        // }

        try {
            MapGenerator.getInstance().executeActionOnMove(bot, this.currentStepTile);
        } catch (Exception e) {
            System.out.println("no hay mapa aun");
        }

        }
    }




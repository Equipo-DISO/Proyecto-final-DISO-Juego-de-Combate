package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.TiredState;
import com.utad.proyectoFinal.mapa.ClosestEnemyStrategy;
import com.utad.proyectoFinal.mapa.ClosestLootStrategy;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapGenerator;

public class TypeCBotAI extends BotAI {

    private static final double LOW_HEALTH_THRESHOLD = 0.3;
    // Thresholds for combat decisions
    private static final double CRITICAL_HEALTH_THRESHOLD = 0.4;
    private static final double RETREAT_THRESHOLD = 0.25;
    private static final int MIN_MANA_NEEDED = DefaultAttributes.LOW_MANA_THRESHOLD;

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
    
    /**
     * Determines combat action for a TypeC bot (survival-focused)
     * This bot type prioritizes:
     * 1. Retreating when health is critical
     * 2. Healing whenever possible if health is low
     * 3. Gaining mana if low
     * 4. Using light attacks to conserve mana
     * 5. Only using heavy attacks if in good condition
     * @param bot The bot character
     *
     * @return The combat action type to perform
     */ 
    @Override
    public CombatActionType decideCombatAction(Bot bot) {
        // Check health and determine actions based on survival priorities
        double healthRatio = (double) bot.getHealthPoints() / bot.getMaxHealthPoints();
        
        // If health is critical, try to retreat
        if (healthRatio < RETREAT_THRESHOLD) {
            return CombatActionType.RETREAT;
        }
        
        // If health is low and has potion, heal
        if (healthRatio < CRITICAL_HEALTH_THRESHOLD && bot.getHpPotions() > 0) {
            return CombatActionType.HEAL;
        }
        
        // If mana is too low, regenerate
        if (bot.getManaPoints() < MIN_MANA_NEEDED) {
            return CombatActionType.GAIN_MANA;
        }
        
        // If health is good and has enough mana, use heavy attack
        if (healthRatio > 0.7 && bot.getManaPoints() >= 2) {
            return CombatActionType.HEAVY_ATTACK;
        }
        
        // Default to light attack (conserves mana)
        return CombatActionType.LIGHT_ATTACK;
    }
}




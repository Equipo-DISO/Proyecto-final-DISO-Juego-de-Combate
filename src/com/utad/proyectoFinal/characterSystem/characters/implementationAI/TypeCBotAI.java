package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.TiredState;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.mapa.*;

public class TypeCBotAI extends BotAI {

    private static final double LOW_HEALTH_THRESHOLD = 0.3;
    // Thresholds for combat decisions
    private static final double CRITICAL_HEALTH_THRESHOLD = 0.4;
    private static final double RETREAT_THRESHOLD = 0.25;
    private static final int MIN_MANA_NEEDED = DefaultAttributes.LOW_MANA_THRESHOLD;

    @Override
    public void analyzeSituation(Bot bot) {
        try {
            PathFindingStrategy strategy = bot.getHelmet() == null || bot.getWeapon() == null
                    ? BotActionType.LOOKING_FOR_ITEM.getStrategy()
                    : bot.getBotActionType().getStrategy();

            this.targets = MapGenerator.getInstance().getPathToObjective(bot.getCurrentPosition(), strategy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (targets != null && !targets.isEmpty()) {
            this.currentStepTile = targets.size() == 1 ? targets.get(0) : targets.get(1);
        }
    }

    @Override
    public void decideNextMove(GenericTile tile, Bot bot) {
        if (targets != null && !targets.isEmpty()) {
            if (bot.getHelmet() == null || bot.getWeapon() == null || bot.getHealthPoints() < bot.getMaxHealthPoints() * LOW_HEALTH_THRESHOLD) {
                bot.setBotActionType(BotActionType.LOOKING_FOR_ITEM);
            } else {
                bot.setBotActionType(BotActionType.LOOKING_FOR_ENEMY);
            }
        }
    }

    @Override
    public void performAction(Bot bot) {

        // After list ends ask for a new one
        if (currentStepTile != null && currentStepTile.getOcupiedObject() instanceof Bot enemyBot && enemyBot.equals(bot)) {
            try {
                this.targets = MapGenerator.getInstance().getPathToObjective(bot.getCurrentPosition(), BotActionType.LOOKING_FOR_ENEMY.getStrategy());
                this.currentStepTile = targets.size() <= 1 ? targets.get(0) : targets.get(1); 
            } catch (Exception e) {
                System.err.println("TypeC: Atacando a sí mismo");
                e.printStackTrace();
            }   
        }   
        try {
            if (bot.getCurrentState() instanceof TiredState) {
                bot.gainMana();
            } else if (currentStepTile != null && currentStepTile.getOcupiedObject() instanceof Bot enemyBot && !enemyBot.equals(bot)) {
                bot.attack((CombatCharacter) currentStepTile.getOcupiedObject(), new LightAttackStrategy());
            } else {    
                MapGenerator.getInstance().executeActionOnMove(bot, currentStepTile);
            }
        } catch (Exception e) {
            System.err.println("Error ejecutando acción de TypeC: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Determines combat action for a TypeC bot (survival-focused)
     * This bot type prioritizes:
     * 1. Retreating when health is critical
     * 2. Healing whenever possible if health is low
     * 3. Gaining mana if low
     * 4. Using light attacks to conserve mana
     * 5. Only using heavy attacks if in good condition.
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




package com.utad.proyectoFinal.gameManagement;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.CombatActionType;
import com.utad.proyectoFinal.ui.combat.Action;
import com.utad.proyectoFinal.ui.combat.CombatFeedLine;
import com.utad.proyectoFinal.ui.combat.CombatInterface;

public class CombatManager {
    private static CombatManager instance = null;
    
    public static CombatManager getInstance() {
        if (instance == null) {
            instance = new CombatManager();
        }
        return instance;
    }
    
    private CombatManager() {
        // Private constructor for singleton
    }
    
    /**
     * Handles player action in combat
     * @param player The player character
     * @param enemy The enemy character
     * @param actionType Type of action (0=light attack, 1=heavy attack, 2=heal, 3=concentrate, 4=run)
     * @param combatInterface Reference to combat interface for updating UI
     * @return true if the battle is over, false otherwise
     */
    public boolean handlePlayerAction(CombatCharacter player, CombatCharacter enemy, int actionType, CombatInterface combatInterface) {
        CombatFeedLine feedLine = new CombatFeedLine("");
        boolean isRunning = false;
        
        // Set feed loggers
        player.setFeedLogger(combatInterface);
        enemy.setFeedLogger(combatInterface);
        
        // Handle player action
        switch (actionType) {
            case 0:
                player.attack(enemy, new LightAttackStrategy());
                break;
            case 1:
                player.attack(enemy, new HeavyAttackStrategy());
                break;
            case 2:
                feedLine.setNewLine("Curarse (" + player.getHpPotions() + ")", Action.HEAL);
                combatInterface.addFeedLine(feedLine);
                player.heal();
                break;
            case 3:
                player.gainMana();
                break;
            case 4:
                isRunning = player.retreat(enemy);
                if (isRunning) {
                    combatInterface.addFeedLine("Retirado", Action.RUN);
                }   
                break;
            default:
                System.out.println("Error: Invalid action type");
        }
        
        // Handle battle end
        boolean battleEnded = checkBattleEnd(player, enemy, isRunning);
        
        // Clear feed loggers
        player.setFeedLogger(null);
        enemy.setFeedLogger(null);
        
        return battleEnded;
    }
    
    /**
     * Handles enemy (bot) turn in combat
     * @param player The player character
     * @param enemy The enemy character
     * @param combatInterface Reference to combat interface for updating UI
     * @return true if the battle is over, false otherwise
     */
    public boolean handleBotTurn(CombatCharacter player, CombatCharacter enemy, CombatInterface combatInterface) {
        // Set feed loggers
        player.setFeedLogger(combatInterface);
        enemy.setFeedLogger(combatInterface);
        
        // Determine the action based on the bot type
        int actionType;
        
        if (enemy instanceof Bot) {
            Bot botEnemy = (Bot) enemy;
            CombatActionType botAction = determineBotAction(botEnemy, player);
            actionType = botAction.getActionCode();
        } else {
            // Fallback to default bot AI if not a Bot instance
            actionType = getRandomAction(enemy);
        }
        
        // Execute the selected action
        boolean isRunning = executeAction(enemy, player, actionType, combatInterface);
        
        // Handle battle end
        boolean battleEnded = checkBattleEnd(player, enemy, isRunning);
        
        // Clear feed loggers
        player.setFeedLogger(null);
        enemy.setFeedLogger(null);
        
        return battleEnded;
    }
    
    /**
     * Determines the appropriate combat action for a bot based on its AI type
     * @param bot The bot character
     * @param enemy The enemy character (usually the player)
     * @return The selected combat action type
     */
    private CombatActionType determineBotAction(Bot bot, CombatCharacter enemy) {
        return bot.getBotAI().decideCombatAction(bot);
    }
    
    /**
     * Gets a random action code for non-Bot enemies
     * @param enemy The enemy character
     * @return A random action code
     */
    private int getRandomAction(CombatCharacter enemy) {
        int action = (int)(Math.random() * 4); // Random action (0-3)
        
        // If action is HEAL but no potions, change to LIGHT_ATTACK
        if (action == 2 && enemy.getHpPotions() <= 0) {
            action = 0;
        }
        
        return action;
    }
    
    /**
     * Executes the specified combat action
     * @param attacker The character performing the action
     * @param defender The target character
     * @param actionType The type of action to perform
     * @param combatInterface Reference to combat interface for UI updates
     * @return true if retreat was successful, false otherwise
     */
    private boolean executeAction(CombatCharacter attacker, CombatCharacter defender, int actionType, CombatInterface combatInterface) {
        boolean isRunning = false;
        
        switch (actionType) {
            case 0: // Light attack
                AttackStrategy lightStrategy = new LightAttackStrategy();
                attacker.attack(defender, lightStrategy);
                break;
                
            case 1: // Heavy attack
                // If not enough mana for heavy, fallback to light attack
                if (attacker.getManaPoints() >= 2) {
                    AttackStrategy heavyStrategy = new HeavyAttackStrategy();
                    attacker.attack(defender, heavyStrategy);
                } else {
                    AttackStrategy lightFallback = new LightAttackStrategy();
                    attacker.attack(defender, lightFallback);
                }
                break;
                
            case 2: // Heal
                if (attacker.getHpPotions() > 0) {
                    combatInterface.addFeedLine(attacker.getName() + " se cura", Action.HEAL);
                    attacker.heal();
                } else {
                    // Fallback to light attack if no potions
                    AttackStrategy fallback = new LightAttackStrategy();
                    attacker.attack(defender, fallback);
                }
                break;
                
            case 3: // Gain mana
                attacker.gainMana();
                combatInterface.addFeedLine(attacker.getName() + " se concentra", Action.CONCENTRATE);
                break;
                
            case 4: // Retreat
                isRunning = attacker.retreat(defender);
                if (isRunning) {
                    combatInterface.addFeedLine(attacker.getName() + " intenta huir", Action.RUN);
                }
                break;
                
            default:
                // Default to light attack
                AttackStrategy defaultStrategy = new LightAttackStrategy();
                attacker.attack(defender, defaultStrategy);
        }
        
        return isRunning;
    }
    
    /**
     * Checks if the battle is over and handles appropriate game context actions
     * @param player The player character
     * @param enemy The enemy character
     * @param isRunning Whether the player is running away
     * @return true if battle is over, false otherwise
     */
    private boolean checkBattleEnd(CombatCharacter player, CombatCharacter enemy, boolean isRunning) {
        if (!player.isAlive() || !enemy.isAlive() || isRunning) {
            if (!player.isAlive()) {
                GameContext.getInstance().playerKilled(enemy);
            } else if (!enemy.isAlive()) {
                GameContext.getInstance().characterKilled(enemy);
            }
            
            return true;
        }
        
        return false;
    }
} 
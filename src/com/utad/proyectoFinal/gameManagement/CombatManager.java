package com.utad.proyectoFinal.gameManagement;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.Bot;
import com.utad.proyectoFinal.characterSystem.characters.implementationAI.CombatActionType;
import com.utad.proyectoFinal.ui.combat.Action;
import com.utad.proyectoFinal.ui.combat.CombatInterface;
import java.util.Random;

public class CombatManager {
    private static CombatManager instance = null;
    private final Random random;
    
    public static CombatManager getInstance() {
        if (instance == null) {
            instance = new CombatManager();
        }
        return instance;
    }
    
    private CombatManager() {
        // Private constructor for singleton
        this.random = new Random();
    }
    
    /**
     * Handles player action in combat
     * @param player The player character
     * @param enemy The enemy character
     * @param actionType Type of action (0=light attack, 1=heavy attack, 2=heal, 3=concentrate, 4=run)
     * @param combatInterface Reference to combat interface for updating UI
     * @return true if the battle is over, false otherwise
     */
    public boolean handlePlayerAction(CombatCharacter player, CombatCharacter enemy, CombatActionType actionType, CombatInterface combatInterface) {
        boolean isRunning = false;
        
        combatInterface.addFeedLine("---Comenzando turno del jugador---", Action.NEW_TURN);

        // Set feed loggers
        player.setFeedLogger(combatInterface);
        enemy.setFeedLogger(combatInterface);
        
        // Handle player action
        switch (actionType) {
            case LIGHT_ATTACK:
                player.attack(enemy, new LightAttackStrategy());
                break;
            case HEAVY_ATTACK:
                player.attack(enemy, new HeavyAttackStrategy());
                break;
            case HEAL:
                player.heal();
                break;
            case GAIN_MANA:
                player.gainMana();
                break;
            case RETREAT:
                isRunning = player.retreat(enemy);
                break;
            default:
                // This case should ideally not be reached
                System.out.println("Error: Invalid action type: " + actionType);
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
        
        combatInterface.addFeedLine("---Comenzando turno del enemigo---", Action.NEW_TURN); 

        // Set feed loggers
        player.setFeedLogger(combatInterface);
        enemy.setFeedLogger(combatInterface);
        
        // Determine the action based on the bot type
        CombatActionType botActionEnum;
        
        if (enemy instanceof Bot botEnemy) {
            botActionEnum = determineBotAction(botEnemy, player);
        } else {
            // Fallback to default bot AI if not a Bot instance
            botActionEnum = getRandomAction(enemy);
        }
        
        // Execute the selected action
        boolean isRunning = executeAction(enemy, player, botActionEnum, combatInterface);
        
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
     * Gets a random action code for non-Bot enemies, used for fallback if the bot is not a Bot instance
     * @param enemy The enemy character
     * @return A random action code
     */
    private CombatActionType getRandomAction(CombatCharacter enemy) {
        int randomActionCode = this.random.nextInt(4);
        CombatActionType selectedAction;

        switch (randomActionCode) {
            case 0:
                selectedAction = CombatActionType.LIGHT_ATTACK;
                break;
            case 1:
                selectedAction = CombatActionType.HEAVY_ATTACK;
                // If not enough mana for heavy, or no heavy attack capability, could add fallback here or in executeAction
                break;
            case 2:
                selectedAction = CombatActionType.HEAL;
                break;
            case 3:
                selectedAction = CombatActionType.GAIN_MANA;
                break;
            default: // Should not happen with Math.random()*4
                selectedAction = CombatActionType.LIGHT_ATTACK; 
                break;
        }
        
        // If action is HEAL but no potions, change to LIGHT_ATTACK
        if (selectedAction == CombatActionType.HEAL && enemy.getHpPotions() <= 0) {
            selectedAction = CombatActionType.LIGHT_ATTACK;
        }

        return selectedAction;
    }
    
    /**
     * Executes the specified combat action
     * @param attacker The character performing the action
     * @param defender The target character
     * @param actionType The type of action to perform
     * @param combatInterface Reference to combat interface for UI updates
     * @return true if retreat was successful, false otherwise
     */
    private boolean executeAction(CombatCharacter attacker, CombatCharacter defender, CombatActionType actionType, CombatInterface combatInterface) {
        // If attacker is not alive or is deactivated, don't allow any actions
        if (!attacker.isAlive()) {
            return false;
        }
        
        boolean isRunning = false;
        
        switch (actionType) {
            case LIGHT_ATTACK:
                AttackStrategy lightStrategy = new LightAttackStrategy();
                attacker.attack(defender, lightStrategy);
                break;
                
            case HEAVY_ATTACK:
                AttackStrategy heavyStrategy = new HeavyAttackStrategy();
                attacker.attack(defender, heavyStrategy);
                break;
                
            case HEAL:
                attacker.heal();    
                break;
                
            case GAIN_MANA:
                attacker.gainMana();
                break;
                
            case RETREAT:
                isRunning = attacker.retreat(defender);
                break;
                
            default:
                // Default to concentrate if an unknown action type is somehow passed 
                // This should ideally not be reached
                System.out.println("Error: Unknown action type in executeAction: " + actionType + ". Defaulting to light attack.");
                attacker.gainMana();
                break;
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
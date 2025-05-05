package com.utad.proyectoFinal.gameManagement;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.AttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.mapa.MapController;
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
        
        // Simple AI logic for bot
        AttackStrategy attackStrategy;
        int action = (int)(Math.random() * 4); // Random action (0-3)
        
        switch (action) {
            case 0: // Light attack
                attackStrategy = new LightAttackStrategy();
                enemy.attack(player, attackStrategy);
                break;
            case 1: // Heavy attack if enough mana, otherwise light attack
                if (enemy.getManaPoints() >= 2) {
                    attackStrategy = new HeavyAttackStrategy();
                    enemy.attack(player, attackStrategy);
                } else {
                    attackStrategy = new LightAttackStrategy();
                    enemy.attack(player, attackStrategy);
                }
                break;
            case 2: // Heal if needed and has potions
                if (enemy.getHealthPoints() < enemy.getMaxHealthPoints() / 2 && enemy.getHpPotions() > 0) {
                    combatInterface.addFeedLine("Curarse (" + enemy.getHpPotions() + ")", Action.HEAL);
                    enemy.heal();
                } else {
                    attackStrategy = new LightAttackStrategy();
                    enemy.attack(player, attackStrategy);
                }
                break;
            case 3: // Gain mana if low
                if (enemy.getManaPoints() < 2) {
                    enemy.gainMana();
                } else {
                    attackStrategy = new LightAttackStrategy();
                    enemy.attack(player, attackStrategy);
                }
                break;
        }
        
        // Handle battle end
        boolean battleEnded = checkBattleEnd(player, enemy, false);
        
        // Clear feed loggers
        player.setFeedLogger(null);
        enemy.setFeedLogger(null);
        
        return battleEnded;
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
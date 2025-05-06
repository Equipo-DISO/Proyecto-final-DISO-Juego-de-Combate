package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.mapa.MapGenerator;

public class Bot extends BaseCharacter {

    private static final String BOTNAME = "B.O.T";
    private BotActionType botActionType;
    private BotAI botAi;
    private Boolean deactivated = false; // Flag to indicate if the bot is deactivated
    
    private MapGenerator map;

    public Bot() {
        this(BOTNAME);
    }

    public Bot(String name) {
        this(name, DefaultAttributes.ATTACK);
    }

    public Bot(String name, Double baseAttack) {
        super(name, baseAttack);
        super.esControlado = true; 
    }

    @Override
    public boolean isAlive() {
        return super.attributes.isAlive() && !deactivated;
    }
    
    /**
     * Check if bot is deactivated
     * @return true if deactivated, false otherwise
     */
    public Boolean isDeactivated() {
        return deactivated;
    }
    
    /**
     * Set bot's deactivated status
     * @param deactivated true to deactivate, false to activate
     */
    public void setDeactivated(Boolean deactivated) {
        this.deactivated = deactivated;
    }
    
    /**
     * Self-destruct mechanism when a bot dies
     * Clears from position, transitions to DeadState, and deactivates
     */
    public void selfDestruct() {
        // Transition to dead state
        transitionTo(states.getDeadState());
        
        // Clear from current position
        if (currentPosition != null) {
            currentPosition.setOcupiedObject(null);
        }
        
        // Deactivate bot
        deactivated = true;
    }
    
    @Override
    public void reduceHealth(Integer damage) {
        super.attributes.reduceHealth(damage);
        
        // Transition to DeadState if health reaches 0
        if (!super.attributes.isAlive() && !deactivated) {
            selfDestruct();
        }
    }

    public void BotMove() {
        if (!deactivated) { 
            if (botAi != null) {
                botAi.executeTurn(this);
            } else {  
                System.out.println("AI no definida para este bot.");
            }
        }
    }


    public void setBotAI(BotAI botAi) {
    this.botAi = botAi;
}

    public BotAI getBotAI() {
    return botAi;
}

    public MapGenerator getMap(){
        return this.map;
    }

    public void setMap(MapGenerator map){
        this.map = map;
    }

    public BotActionType getBotActionType() {
        return botActionType;
    }

    public void setBotActionType(BotActionType botActionType) {
        this.botActionType = botActionType;
    }
}

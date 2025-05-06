package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.TiredState;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.LightAttackStrategy;
import com.utad.proyectoFinal.mapa.*;

public class TypeABotAI extends BotAI {

    // Threshold values for decision making
    private static final double LOW_HEALTH_THRESHOLD = 0.35;


    //private Integer counter = 0;
    @Override
    public void analyzeSituation(Bot bot) {
        //pillar el camino hasta el objetivo, con la estrategia propuesta
        //asignamos acción por defecto, buscar loot
        if (bot.getBotActionType() == null) {
            bot.setBotActionType(BotActionType.LOOKING_FOR_ITEM);
        }
        PathFindingStrategy strategy = bot.getBotActionType().getStrategy();
        try {
            this.targets = MapGenerator.getInstance().getPathToObjective(bot.getCurrentPosition(), strategy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(targets != null && !targets.isEmpty() && bot.getCurrentPosition() != null) {
            this.currentStepTile = (this.targets.size() <= 1 ? targets.get(0) : targets.get(1));
        }

    }

    @Override
    public void decideNextMove(GenericTile tile, Bot bot) {
        //si se le cruza un enemigo, lo ataca o lo persigue
        if (targets != null && !targets.isEmpty()) {
            bot.setBotActionType(currentStepTile.isOcupiedByCharacter() ? BotActionType.LOOKING_FOR_ENEMY : BotActionType.LOOKING_FOR_ITEM);
        }
    }

    @Override
    public void performAction(Bot bot) {
        if (currentStepTile != null && currentStepTile.getOcupiedObject() instanceof Bot enemyBot && enemyBot.equals(bot)) {
            try {
                this.targets = MapGenerator.getInstance().getPathToObjective(bot.getCurrentPosition(), BotActionType.LOOKING_FOR_ENEMY.getStrategy());
                this.currentStepTile = targets.size() <= 1 ? targets.get(0) : targets.get(1); 
            } catch (Exception e) {
                System.err.println("TypeA: Atacando a sí mismo");
                e.printStackTrace();
            }
        }
        //actualizar su posicion
        try {
            if (bot.getCurrentState() instanceof TiredState) {
                bot.gainMana();
            } else if (currentStepTile != null && currentStepTile.getOcupiedObject() instanceof Bot enemyBot && !enemyBot.equals(bot)) {
                // Asegurarse de que no se ataque a sí mismo
                bot.attack((CombatCharacter) currentStepTile.getOcupiedObject(), new LightAttackStrategy());
            } else {
                MapGenerator.getInstance().executeActionOnMove(bot, this.currentStepTile);
            }
        } catch (Exception e) {
            System.err.println("Error ejecutando acción de TypeA: " + e.getMessage());  
            e.printStackTrace();    
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



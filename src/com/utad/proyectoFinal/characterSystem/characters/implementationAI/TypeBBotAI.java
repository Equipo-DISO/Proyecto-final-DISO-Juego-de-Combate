package com.utad.proyectoFinal.characterSystem.characters.implementationAI;

import com.utad.proyectoFinal.characterSystem.characters.DefaultAttributes;
import com.utad.proyectoFinal.characterSystem.characters.states.TiredState;
import com.utad.proyectoFinal.characterSystem.characters.states.strategies.HeavyAttackStrategy;
import com.utad.proyectoFinal.characterSystem.characters.CombatCharacter;
import com.utad.proyectoFinal.mapa.GenericTile;
import com.utad.proyectoFinal.mapa.MapGenerator;
import com.utad.proyectoFinal.mapa.ClosestEnemyStrategy;
import com.utad.proyectoFinal.mapa.PathFindingStrategy;

public class TypeBBotAI extends BotAI {

    // Threshold values for decision making
    private static final double LOW_HEALTH_THRESHOLD = 0.25; // They are more willing to risk
    private static final int HEAVY_ATTACK_MANA_NEEDED = 10;
    private static final int LIGHT_ATTACK_MANA_NEEDED = DefaultAttributes.LOW_MANA_THRESHOLD;

    @Override
    public void analyzeSituation(Bot bot) {
        //same para Type B bot
        // Si no tiene una acción aún, asignar una por defecto
        if (bot.getBotActionType() == null) {
            bot.setBotActionType(BotActionType.LOOKING_FOR_ENEMY); // o LOOKING_FOR_ENEMY si es TypeB
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
        // Siempre va tras enemigos
        //creo que sobra esta línea si ya le seteamos la stratgy por defecto
        bot.setBotActionType(BotActionType.LOOKING_FOR_ENEMY);
    }



    @Override
    public void performAction(Bot bot) {

        try {
            if (currentStepTile.getOcupiedObject() instanceof Bot) {
                bot.attack((CombatCharacter) currentStepTile.getOcupiedObject(), new HeavyAttackStrategy());
            } else if (bot.getCurrentState() instanceof TiredState)     {
                bot.gainMana();
            } else {
                MapGenerator.getInstance().executeActionOnMove(bot, this.currentStepTile);
            }
        } catch (Exception e) {
            System.out.println("no hay mapa aun");
        }
    }
    
    /**
     * Determines combat action for a TypeB bot (combat-focused)
     * This bot type prioritizes:
     * 1. Heavy attacks when possible (they prefer power)
     * 2. Gaining mana to execute heavy attacks
     * 3. Light attacks as fallback
     * 4. Healing only when critically low
     * 5. Almost never retreats
     * @param bot The bot character
     *
     * @return The combat action type to perform
     */ 
    @Override
    public CombatActionType decideCombatAction(Bot bot) {
        // If critically low on health, consider healing
        double healthRatio = (double) bot.getHealthPoints() / bot.getMaxHealthPoints();
        if (healthRatio < LOW_HEALTH_THRESHOLD && bot.getHpPotions() > 0) {
            return CombatActionType.HEAL;
        }
        
        // If has enough mana for heavy attack, use it (preferred attack)
        if (bot.getManaPoints() >= HEAVY_ATTACK_MANA_NEEDED) {
            return CombatActionType.HEAVY_ATTACK;
        }
        
        // If has enough mana for light attack, use it
        if (bot.getManaPoints() >= LIGHT_ATTACK_MANA_NEEDED) {
            return CombatActionType.LIGHT_ATTACK;
        }
        
        // Otherwise, regenerate mana
        return CombatActionType.GAIN_MANA;
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



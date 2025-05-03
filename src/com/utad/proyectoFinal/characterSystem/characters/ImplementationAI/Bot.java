package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.mapa.MapGenerator;

import java.awt.image.BufferedImage;

public class Bot extends BaseCharacter {

    private static final String BOTNAME = "B.O.T";
    private static final Double ATTACK = 10.00;
    private BotActionType botActionType;
    private BotAI botAi;

    private MapGenerator map;

    public Bot() {
        this(BOTNAME, ATTACK);
        botActionType = BotActionType.NONE;
    }

    public Bot(String name, Double baseAttack) {
        super(name, baseAttack);
    }

    public void BotMove() {
        if (botAi != null) {
            botAi.executeTurn(this);
        } else {
            System.out.println("AI no definida para este bot.");
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

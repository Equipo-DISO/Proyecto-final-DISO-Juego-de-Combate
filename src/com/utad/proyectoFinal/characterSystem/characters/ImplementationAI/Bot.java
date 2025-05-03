package com.utad.proyectoFinal.characterSystem.characters.ImplementationAI;

import com.utad.proyectoFinal.characterSystem.characters.BaseCharacter;
import com.utad.proyectoFinal.characterSystem.tools.BaseHelmet;
import com.utad.proyectoFinal.mapa.MapGenerator;

public class Bot extends BaseCharacter {

    private static final String BOTNAME = "B.O.T";
    private static final Double ATTACK = 10.00;
    private static final Double DEFENSE = 10.0;

    private BotAI botAi;

    private MapGenerator map;
    public Bot() {
        this(BOTNAME, ATTACK, DEFENSE);

    }

    public Bot(String name, Double baseAttack, Double baseDefense) {
        super(name, baseAttack, baseDefense);
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

    public BaseHelmet getCurrentHelmet() {
        return getHelmet();
    }


    public MapGenerator getMap(){
        return this.map;
    }

    public void setMap(MapGenerator map){
        this.map = map;
    }
}
